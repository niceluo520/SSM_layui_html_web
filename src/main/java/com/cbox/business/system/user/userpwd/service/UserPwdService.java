package com.cbox.business.system.user.userpwd.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbox.base.common.service.BaseCRUDService;
import com.cbox.base.protocol.ResponseBodyVO;
import com.cbox.base.protocol.ServerRspUtil;
import com.cbox.business.system.user.userlogin.bean.SysUserVO;
import com.cbox.business.system.user.userlogin.service.PasswordService;

/**
 * Created by Administrator on 2017/2/7 0007.
 */
@Service
@Transactional
public class UserPwdService extends BaseCRUDService {

    @Autowired
    private PasswordService passwordService;

    public ResponseBodyVO<String> doEditPwd(SysUserVO userInfo, String oldPwd, String newPwd) {
        ResponseBodyVO<String> resp = new ResponseBodyVO<String>();

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("user_id", userInfo.getUser_id());
        Map<String, Object> userMap = this.queryOne("s_sysuser", param);
        if (userMap != null && !userMap.isEmpty()) {
            if (!passwordService.matches(userInfo.getUser_id(), userMap.get("password").toString(), oldPwd, userMap.get("salt").toString())) {
                resp = ServerRspUtil.error("旧密码不正确");
                return resp;
            }
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("password", passwordService.encryptPassword(userInfo.getUser_id(), newPwd, userMap.get("salt").toString()));
            paramMap.put("rec_updateperson", userInfo.getUser_id()); // 操作人(取当前用户的Id)
            int count = this.save(userInfo.getRec_id(), "s_sysuser", paramMap);
            if (count > 0) {
                resp = ServerRspUtil.success("修改密码成功！", "");
            } else {
                resp = ServerRspUtil.error("修改密码失败！");
            }
        } else {
            resp = ServerRspUtil.error("用户不存在");
        }

        return resp;
    }

}
