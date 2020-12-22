package com.h3c.vdi.viewscreen.api.model.response.file;

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
public class ResponseUploadFileRecords extends BasicUploadFileRecords {

    private static final long serialVersionUID = -5622714316734009862L;
}