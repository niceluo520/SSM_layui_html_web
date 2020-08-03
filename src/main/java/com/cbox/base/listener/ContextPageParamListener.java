package com.cbox.base.listener;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cbox.base.util.StrUtil;
import com.cbox.business.common.config.service.SysConfigService;

/**
 * @ClassName: ContextPageParamListener
 * @Function: 加载前端页面使用的公共参数。当Servlet容器启动Web应用时调用该方法，在调用完该方法之后，容器再对Filter初始化。
 * 
 * @author cbox
 * @date 2019年10月3日 下午4:36:38
 * @version 1.0
 */
public class ContextPageParamListener implements ServletContextListener {

    @Value("${is.filesave.cdn:false}")
    protected String isCDN;

    @Value("${file.cdn-path:}")
    protected String fileCDNPath;

    @Autowired
    private SysConfigService sysConfigService;

    public void contextInitialized(ServletContextEvent sce) {
        WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext()).getAutowireCapableBeanFactory().autowireBean(this);
        ServletContext context = sce.getServletContext();

        /* 设置前端页面使用的参数。前端页面通过${paramName}引用 */

        // 文件展示路径
        String fileShowPath = "";
        if ("false".equals(isCDN)) {
            fileShowPath = context.getContextPath() + "/attach/show?fileName=";
        } else {
            fileShowPath = fileCDNPath;
        }
        context.setAttribute("fileShowPath", fileShowPath);

        String suffixShowPath = context.getContextPath() + "/attach/showSuffix?fileName=";
        context.setAttribute("suffixShowPath", suffixShowPath);

        String fileDownloadPath = context.getContextPath() + "/attach/download?fileName=";
        context.setAttribute("fileDownloadPath", fileDownloadPath);

        List<Map<String, Object>> listSysConfig = sysConfigService.listSysConfig();
        if (listSysConfig != null) {
            for (Map<String, Object> sysConfig : listSysConfig) {
                String key = StrUtil.getNotNullStrValue(sysConfig.get("sc_name"));
                String val = StrUtil.getNotNullStrValue(sysConfig.get("sc_value"));
                if (StrUtil.isNotNull(key) && StrUtil.isNotNull(val)) {
                    if (key.startsWith("img_")) {
                        key = key.substring(4);
                        if (!val.startsWith("http")) {
                            val = context.getContextPath() + "/myResources/" + val;
                        }
                    }
                    context.setAttribute(key, val);
                    System.out.println("项目参数：" + key + "," + val);
                }
            }
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}