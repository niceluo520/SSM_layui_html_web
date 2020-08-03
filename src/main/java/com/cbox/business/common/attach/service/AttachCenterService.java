package com.cbox.business.common.attach.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cbox.base.common.service.BaseCRUDService;
import com.cbox.base.protocol.ResponseBodyVO;
import com.cbox.base.protocol.ServerRspUtil;
import com.cbox.base.util.GlobalRecIdUtil;
import com.cbox.base.util.StrUtil;
import com.cbox.business.common.attach.factory.AttachNameFactory;

/**
 * 
 * @ClassName: AttachCenterService
 * @Function:附件/文件相关的上传下载服务
 * 
 * @author cbox
 * @date 2019年9月29日 上午10:48:22
 * @version 1.0
 */
@Service
@Transactional
public class AttachCenterService extends BaseCRUDService {

    @Value("${web.upload-path:}")
    String webUploadPath;

    /**
     * 文件上传。考虑到页面缓存更新的问题，所以如果是修改的情况，则先删除原有文件，然后再新增文件的方式来处理。
     * 
     * @param request
     * @param upfile
     * @return
     */
    public ResponseBodyVO<Object> uploadAdd(HttpServletRequest request, @RequestParam("file") MultipartFile upfile) {

        if (upfile == null) {
            ServerRspUtil.error("图片上传失败请重试！");
        }

        // 判断当前上传的文件是否在库中已经存在
        boolean bFileExist = false;
        String imgFileName = request.getParameter("imgFileName");
        if (StrUtil.isNotNull(imgFileName) && !"undefined".equals(imgFileName)) {
            Map<String, Object> selectParams = new HashMap<String, Object>();
            selectParams.put("file_name", imgFileName);
            Map<String, Object> imgBeanMap = this.queryOne("d_sys_file_attachment", selectParams);

            // 删除原来的上传文件
            if (imgBeanMap != null) {
                bFileExist = true;

                String filePath = (String) imgBeanMap.get("file_path");
                String targetFilePath = webUploadPath + filePath + imgFileName;
                FileUtils.deleteQuietly(new File(targetFilePath));
            }
        }

        String rec_id = GlobalRecIdUtil.nextRecId();// 文件名唯一id

        /* Step1：上传文件 */
        String originalName = upfile.getOriginalFilename();
        String fileSuffix = this.getSuffix(originalName);
        String targetFileName = rec_id + "." + this.getSuffix(originalName);// 保存的文件名
        String savePath = AttachNameFactory.getAttachPath();
        try {
            String targetFilePath = webUploadPath + savePath + targetFileName;
            File targetFile = new File(targetFilePath);

            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }
            upfile.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* Step2：保存入库 */
        int count = 1;
        if (!bFileExist) {
            Map<String, Object> updateParams = new HashMap<String, Object>();
            updateParams.put("org_file_name", originalName);
            updateParams.put("file_name", targetFileName);
            updateParams.put("file_path", savePath);
            // updateParams.put("file_type", "");
            // updateParams.put("file_size", "");
            updateParams.put("file_suffix", fileSuffix);
            // updateParams.put("img_height", "");
            // updateParams.put("img_width", "");
            // updateParams.put("av_times", "");
            // updateParams.put("av_image", "");
            updateParams.put("rec_id", rec_id);
            count = this.save(null, "d_sys_file_attachment", updateParams);
        } else {
            // 更新文件名称和文件路径
            Map<String, Object> updateParams = new HashMap<String, Object>();
            updateParams.put("file_name", targetFileName);
            updateParams.put("file_path", savePath);
            Map<String, Object> conditionMap = new HashMap<String, Object>();
            conditionMap.put("file_name", imgFileName);
            count = this.update(conditionMap, "d_sys_file_attachment", updateParams);
        }

        /* Step3：返回信息 */
        Map<String, Object> map2 = new HashMap<>();
        map2.put("src", targetFileName);// 存储的文件名
        map2.put("orgFileName", originalName);// 原始文件名
        map2.put("fileSuffix", fileSuffix);// 扩展名

        if (count > 0) {
            return ServerRspUtil.success("图片上传成功", map2);
        } else {
            return ServerRspUtil.error("图片上传失败请重试！");
        }

    }

    public ResponseBodyVO<Object> uploadDelete(HttpServletRequest request) {

        // 判断当前上传的文件是否在库中已经存在
        @SuppressWarnings("unused")
        boolean bFileExist = false;
        String fileName = request.getParameter("fileName");
        if (StrUtil.isNotNull(fileName) && !"undefined".equals(fileName)) {
            Map<String, Object> selectParams = new HashMap<String, Object>();
            selectParams.put("file_name", fileName);
            Map<String, Object> imgBeanMap = this.queryOne("d_sys_file_attachment", selectParams);

            // 删除原来的上传文件
            if (imgBeanMap != null) {
                bFileExist = true;

                String filePath = (String) imgBeanMap.get("file_path");
                String targetFilePath = webUploadPath + filePath + fileName;
                FileUtils.deleteQuietly(new File(targetFilePath));
            }
        }

        Map<String, Object> selectParams = new HashMap<String, Object>();
        selectParams.put("file_name", fileName);
        int count = this.delete("d_sys_file_attachment", selectParams);

        if (count > 0) {
            return ServerRspUtil.success("图片删除成功");
        } else {
            return ServerRspUtil.error("图片删除失败！");
        }
    }

    public String getFilePath(String fileName) {

        // 必传参数校验
        if (StrUtil.isNull(fileName)) {
            return "";
        }

        // 获取数据 Table:d_sys_file_attachment
        List<String> listColumn0 = new ArrayList<String>();
        listColumn0.add("file_path");
        listColumn0.add("file_name");
        Map<String, Object> mapQueryParam0 = new HashMap<String, Object>();
        mapQueryParam0.put("file_name", fileName);
        Map<String, Object> mapQueryResult0 = this.queryOne("d_sys_file_attachment", mapQueryParam0, listColumn0);

        String filePath = "";
        if (null != mapQueryResult0 && !mapQueryResult0.isEmpty()) {
            filePath = String.valueOf(mapQueryResult0.get("file_path"));
        }

        return filePath;
    }

    public Map<String, Object> getFileAttach(String fileName) {

        // 必传参数校验
        if (StrUtil.isNull(fileName)) {
            return null;
        }

        // 获取数据 Table:d_sys_file_attachment
        List<String> listColumn0 = new ArrayList<String>();
        listColumn0.add("file_path");
        listColumn0.add("file_name");
        listColumn0.add("org_file_name");
        listColumn0.add("file_suffix");
        // listColumn0.add("file_type");
        // listColumn0.add("file_size");
        // listColumn0.add("img_height");
        // listColumn0.add("img_width");
        // listColumn0.add("av_times");
        // listColumn0.add("av_image");
        Map<String, Object> mapQueryParam0 = new HashMap<String, Object>();
        mapQueryParam0.put("file_name", fileName);
        Map<String, Object> mapQueryResult0 = this.queryOne("d_sys_file_attachment", mapQueryParam0, listColumn0);

        return mapQueryResult0;
    }

    /* 获取文件的后缀名 */
    private String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
