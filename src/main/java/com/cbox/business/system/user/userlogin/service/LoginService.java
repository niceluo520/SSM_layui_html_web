package com.cbox.business.system.user.userlogin.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.cbox.base.cache.EhcacheUtil;
import com.cbox.base.common.service.BaseCRUDService;
import com.cbox.base.processor.aspect.PlatOperateLog;
import com.cbox.base.processor.aspect.type.LogType;
import com.cbox.base.protocol.ResponseBodyVO;
import com.cbox.base.protocol.ServerRspUtil;
import com.cbox.base.util.ObjUtil;
import com.cbox.business.system.user.userlogin.bean.SysUserVO;
import com.cbox.business.system.user.userlogin.util.JwtUtil;
import com.cbox.business.system.user.userlogin.util.LoginUtil;
import com.cbox.constant.BusiConstant;

@Service
@Transactional
public class LoginService extends BaseCRUDService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private LoginUtil loginUtil;

    /**
     * 登录处理
     * 
     * @param user_id
     * @param pwd
     * @param flag
     * @return
     */
    @PlatOperateLog(value = "用户登录Service", logType = LogType.LOGIN)
    public ResponseBodyVO<SysUserVO> doLogin(String user_id, String pwd) {
        /** 根据账号查出用户信息 */
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("user_id", user_id);// 登录账号
        SysUserVO sysUserVO = sysUserService.querySysUser(param);
        if (null == sysUserVO) {
            return ServerRspUtil.error(BusiConstant.ERROR_BUSINESS, "对不起，用户不存在");
        }

        /** 校验密码 **/
        if (!passwordService.matches(sysUserVO, pwd)) {
            return ServerRspUtil.error(BusiConstant.ERROR_BUSINESS, "账号或密码错误");
        }
        /** 是否禁用 **/
        if (!"1".equals(sysUserVO.getLocked())) {
            return ServerRspUtil.error(BusiConstant.ERROR_BUSINESS, "登录失败，该账号已禁用");
        }

        /** 成功 **/
        sysUserService.loadAuthinfo(sysUserVO);// 加载用户的权限信息
        // 是否有权限访问
        if (ObjUtil.isNull(sysUserVO.getListUserResources())) {
            return ServerRspUtil.error(BusiConstant.ERROR_BUSINESS, "对不起，您没有访问系统的权限,请联系管理员授权后，再访问");
        }
        sysUserVO.setPassword("");// 密码置空，不缓存
        sysUserVO.setSalt("");// 清空密钥

        String token = jwtUtil.createJWT(sysUserVO.getUser_id(), sysUserVO.getUser_name(), "");
        sysUserVO.setLoginToken(token);
        EhcacheUtil.getInstance().set(token, JSON.toJSONString(sysUserVO), 3600);// 过期时间：1小时 （单位秒）

        loginUtil.refreshProjectCache(false);

        return ServerRspUtil.success("登录成功", sysUserVO);
    }

    /**
     * doEditPwd:修改密码
     *
     * @date: 2020年3月11日 下午10:28:56
     * @author cbox
     * @param sysUserVO
     * @param userId
     * @param passWd
     * @return
     */
    public ResponseBodyVO<SysUserVO> doEditPwd(SysUserVO sysUserVO, String userId, String passWd) {

        String passwd = passwordService.encryptPassword(userId, passWd, sysUserVO.getSalt());
        if (passwd.equals(sysUserVO.getPassword())) {
            return ServerRspUtil.error("新密码不能与原密码一致！请重新设置");
        }

        Map<String, Object> conditionsParam = new HashMap<String, Object>();
        conditionsParam.put("user_id", userId);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("password", passwd);
        param.put("login_status", "1"); // 设置为非首次登陆
        param.put("rec_updateperson", userId); // 操作人(取当前用户的Id)
        int count = this.update(conditionsParam, "s_sysuser", param);

        return ServerRspUtil.formRspBodyVO(count, "密码修改成功");
    }

}
