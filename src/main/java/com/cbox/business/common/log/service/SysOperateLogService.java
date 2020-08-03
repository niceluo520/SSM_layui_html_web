package com.cbox.business.common.log.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbox.base.common.service.BaseCRUDService;

@Service
@Transactional
public class SysOperateLogService extends BaseCRUDService {

    public String addOperateLog(Map<String, Object> params) {
        int i = this.save(null, "d_sys_operatelog", params);
        if (i < 1) {
            return "日志保存数据库失败";
        }
        return "";
    }

}
