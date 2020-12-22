package com.h3c.vdi.viewscreen.web.file;

import com.h3c.vdi.viewscreen.api.model.request.file.RequestUploadFile;
import com.h3c.vdi.viewscreen.api.model.response.file.ResponseUploadFileRecords;
import com.h3c.vdi.viewscreen.api.result.ApiErrorEnum;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.entity.UploadFileRecords;
import com.h3c.vdi.viewscreen.service.file.UploadFileRecordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Log4j2
@Validated
@Api(tags = "文件上传")
@RestController
public class UploadFileRecordsController {

    @Resource
    private UploadFileRecordsService uploadFileRecordsService;


    /**
     * 文件上传
     *
     * @param requestParam requestParam
     * @return
     */
    @ApiOperation("文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "__file", name = "file", value = "文件上传")
    })
    @PostMapping(value = "/file/upload", headers = "content-type=multipart/form-data")
    public ApiResponse<ResponseUploadFileRecords> fileUpload(@ModelAttribute RequestUploadFile requestParam) {
        ApiResponse<ResponseUploadFileRecords> returnValue = ApiResponse.buildError(ApiErrorEnum.E50000, "");

        if (requestParam == null) {
            return ApiResponse.buildError(ApiErrorEnum.E50101, "全部");
        }
        // 判断该文件是否有内容
        if (requestParam.getFile() == null || requestParam.getFile().isEmpty()) {
            return ApiResponse.buildError(ApiErrorEnum.E50101, "file");
        }
        if (StringUtils.isEmpty(requestParam.getSource())) {
            return ApiResponse.buildError(ApiErrorEnum.E50101, "source");
        }

        // 判断单个文件大于1M
        long fileSize = requestParam.getFile().getSize();
        requestParam.setFileSize(fileSize);
        log.info("file.getSize():" + fileSize);
        if (fileSize > 104857600) { // 104857600 = 100 * 1024 * 1024 100MB
            return ApiResponse.buildError(ApiErrorEnum.E50102, "100MB");
        }

        UploadFileRecords entity = uploadFileRecordsService.saveUploadFile(requestParam);
        if (null != entity) {
            ResponseUploadFileRecords responseModel = new ResponseUploadFileRecords();
            BeanUtils.copyProperties(entity, responseModel);
            returnValue = ApiResponse.buildSuccess(responseModel);
        }
        return returnValue;
    }


    /**
     * logo图片上传
     *
     * @param file
     * @return
     */
    @ApiOperation("图片上传")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "__file", name = "file", value = "文件上传")
    })
    @PostMapping(value = "/file/imageUpload", headers = "content-type=multipart/form-data")
    public ApiResponse<String> imageUpload(@RequestParam(value = "file", required = false) MultipartFile file) {
        return uploadFileRecordsService.imageUpload(file);
    }


}