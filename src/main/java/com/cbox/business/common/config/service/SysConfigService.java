package com.cbox.business.common.config.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbox.base.common.service.BaseCRUDService;

@Service
@Transactional
public class SysConfigService extends BaseCRUDService {

    public List<Map<String, Object>> listSysConfig() {
        Map<String, Object> param = new HashMap<String, Object>();
        List<Map<String, Object>> list = this.query("s_sysconfig", param);
        return list;
    }

}
