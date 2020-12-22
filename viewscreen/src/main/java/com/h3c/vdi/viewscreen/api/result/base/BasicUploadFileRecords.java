package com.h3c.vdi.viewscreen.api.result.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
public class BasicUploadFileRecords extends SuperModel {

    private static final long serialVersionUID = -1273930356991765072L;
    /**
     * 来源
     */
    @ApiModelProperty(value = "来源")
    protected String source;

    /**
     * 源文件名
     */
    @ApiModelProperty(value = "源文件名")
    protected String srcFileName;

    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名")
    protected String fileName;

    /**
     * 文件扩展名
     */
    @ApiModelProperty(value = "文件扩展名")
    protected String fileExtension;

    /**
     * 源文件路径
     */
    @ApiModelProperty(value = "源文件路径")
    protected String filePath;

    /**
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小")
    protected Long fileSize;

}