package com.h3c.vdi.viewscreen.service.file;

import com.h3c.vdi.viewscreen.api.model.request.file.RequestUploadFile;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.entity.UploadFileRecords;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Date 2020/10/19 14:49
 * @Created by lgw2845
 */
public interface UploadFileRecordsService {


    /**
     * 保存文件上传
     */
    UploadFileRecords saveUploadFile(RequestUploadFile requestParam);

    /**
     * 图片上传
     */
    ApiResponse<String> imageUpload(MultipartFile file);
}
