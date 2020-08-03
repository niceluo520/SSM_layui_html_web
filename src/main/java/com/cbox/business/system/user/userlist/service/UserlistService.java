package com.cbox.business.system.user.userlist.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbox.base.common.service.BaseCRUDService;
import com.cbox.base.protocol.ResponseBodyVO;
import com.cbox.base.protocol.ServerRspUtil;
import com.cbox.business.system.user.userlist.dao.UserlistMapper;

/**
 * @ClassName: UserlistService
 * @Function: 用户管理-列表
 * 
 * @author cbox
 * @version 1.0
 */
@Service
@Transactional
public class UserlistService extends BaseCRUDService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserlistMapper userlistMapper;

    /************************* 列表[用户查询列表list0]相关方法  Begin ************************/

    /**
     * doLoadListList0:加载列表数据-用户查询列表
     *
     * @param param
     * @return
     */
    public List<Map<String, Object>> doLoadListList0(Map<String, Object> param) {
        return userlistMapper.doLoadListList0(param);
    }


    /**
     * doDelCheckList0:删除校验-用户查询列表
     *
     * @param param
     * @return
     */
    public ResponseBodyVO<Object> doDelCheckList0(Map<String, Object> param) {

        int count = 0;

        return ServerRspUtil.formRspBodyVO(count, "修改操作表单失败");
    }

    /************************* End 表单[用户查询列表list0] ******************************/

}
