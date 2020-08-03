package com.cbox.business.common.attach.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cbox.base.protocol.ResponseBodyVO;
import com.cbox.base.protocol.ServerRspUtil;
import com.cbox.business.common.attach.service.AttachCenterService;

@Controller
@RequestMapping("/attach")
public class AttachCenterController {

    @Value("${web.upload-path:}")
    String webUploadPath;

    @Autowired
    AttachCenterService attachCenterService;

    @ResponseBody
    @RequestMapping(value = "upload", method = { RequestMethod.POST })
    public Map<String, Object> upload(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile upfile) {

        ResponseBodyVO<Object> resp = attachCenterService.uploadAdd(request, upfile);
        return ServerRspUtil.genResponseMap(resp);
    }

    @ResponseBody
    @RequestMapping(value = "delete", method = { RequestMethod.POST })
    public Map<String, Object> delete(HttpServletRequest request, HttpServletResponse response) {

        ResponseBodyVO<Object> resp = attachCenterService.uploadDelete(request);
        return ServerRspUtil.genResponseMap(resp);
    }

    /**
     * show:以读取数据流的方式，返回图片信息供前端展示
     *
     * @date: 2019年9月29日 下午4:17:55
     * @author cbox
     * @param request fileName-存储的文件路径+名称
     * @param response 通过OutputStream写会给客户端
     */
    @RequestMapping("/show")
    public void show(HttpServletRequest request, HttpServletResponse response) {
        try {
            String fileName = (String) request.getParameter("fileName");
            String filePath = attachCenterService.getFilePath(fileName);

            File filePic = new File(webUploadPath + filePath + fileName);
            FileInputStream inputStream = new FileInputStream(filePic);
            int fileLength = inputStream.available(); // 得到文件大小
            byte data[] = new byte[fileLength];
            inputStream.read(data); // 读数据
            inputStream.close();

            response.setContentType("application/octet-stream"); // 设置返回的文件类型
            OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
            toClient.write(data); // 输出数据
            toClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response) {
        try {
            String fileName = (String) request.getParameter("fileName");

            Map<String, Object> mapAttach = attachCenterService.getFileAttach(fileName);
            if (null != mapAttach && !mapAttach.isEmpty()) {
                String filePath = String.valueOf(mapAttach.get("file_path"));
                String fileOriginalName = String.valueOf(mapAttach.get("org_file_name"));

                String fileurl = webUploadPath + filePath + fileName;

                File file = new File(fileurl);
                // 将文件读入文件流
                InputStream inStream = new FileInputStream(fileurl);
                // 获得浏览器代理信息
                final String userAgent = request.getHeader("USER-AGENT");
                // 判断浏览器代理并分别设置响应给浏览器的编码格式
                String finalFileName = null;
                if (StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "Trident")) {// IE浏览器
                    finalFileName = URLEncoder.encode(fileOriginalName, "UTF8");
                } else if (StringUtils.contains(userAgent, "Mozilla")) {// google,火狐浏览器
                    finalFileName = new String(fileOriginalName.getBytes(), "ISO8859-1");
                } else {
                    finalFileName = URLEncoder.encode(fileOriginalName, "UTF8");// 其他浏览器
                }
                // 设置HTTP响应头
                response.reset();// 重置 响应头
                response.setContentType("application/x-download");// 告知浏览器下载文件，而不是直接打开，浏览器默认为打开
                response.addHeader("Content-Disposition", "attachment;filename=\"" + finalFileName + "\"");// 下载文件的名称
                response.addHeader("Content-Length", "" + file.length());
                // 循环取出流中的数据
                byte[] b = new byte[1024];
                int len;
                while ((len = inStream.read(b)) > 0) {
                    response.getOutputStream().write(b, 0, len);
                }
                inStream.close();
                response.getOutputStream().close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "info", method = { RequestMethod.POST })
    public Map<String, Object> info(HttpServletRequest request, HttpServletResponse response) {

        String fileName = (String) request.getParameter("fileName");
        Map<String, Object> mapAttach = attachCenterService.getFileAttach(fileName);

        Map<String, Object> map2 = new HashMap<String, Object>();

        ResponseBodyVO<Object> resp = null;
        if (null != mapAttach && !mapAttach.isEmpty()) {
            map2.put("src", fileName);// 存储的文件名
            map2.put("orgFileName", mapAttach.get("org_file_name"));// 原始文件名
            map2.put("fileSuffix", mapAttach.get("file_suffix"));// 扩展名
            resp = ServerRspUtil.success("获取附件信息成功", map2);
        } else {
            resp = ServerRspUtil.error("为获取到附件信息");
        }

        return ServerRspUtil.genResponseMap(resp);
    }

}
