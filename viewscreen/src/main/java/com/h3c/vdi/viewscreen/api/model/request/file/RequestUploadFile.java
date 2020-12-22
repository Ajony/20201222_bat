package com.h3c.vdi.viewscreen.api.model.request.file;

import com.h3c.vdi.viewscreen.api.result.base.BasicUploadFileRecords;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;


//@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Setter
@Getter
@Accessors(chain = true)
@ApiModel(description = "上传文件")
public class RequestUploadFile extends BasicUploadFileRecords {

    private static final long serialVersionUID = 354105240827283502L;

    @ApiModelProperty("文件")
    private MultipartFile file;

}