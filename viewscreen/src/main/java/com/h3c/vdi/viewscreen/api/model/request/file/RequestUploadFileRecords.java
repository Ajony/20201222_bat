package com.h3c.vdi.viewscreen.api.model.request.file;

import com.h3c.vdi.viewscreen.api.result.base.BasicUploadFileRecords;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

//@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Setter
@Getter
@Accessors(chain = true)
@ApiModel(description = "上传文件记录")
public class RequestUploadFileRecords extends BasicUploadFileRecords {

}