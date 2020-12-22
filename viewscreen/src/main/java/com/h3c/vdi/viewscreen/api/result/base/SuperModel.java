package com.h3c.vdi.viewscreen.api.result.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.h3c.vdi.viewscreen.constant.Constant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 基础通用Model
 * </p>
 *
 * @author lgq
 * @since 2020-06-19
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "基础通用Model")
public class SuperModel implements Serializable {

    private static final long serialVersionUID = -1347771517199832335L;
    /**
     * 当前页（分页查询参数）
     */
    @ApiModelProperty(value = "当前页（分页查询参数）", example = "1")
    protected Long current;

    /**
     * 每页记录数（分页查询参数）
     */
    @ApiModelProperty(value = "每页记录数（分页查询参数）", example = "1")
    protected Long size;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    protected Long id;

    /**
     * 逻辑删除
     */
    @ApiModelProperty(value = "逻辑删除")
    protected String logicDelete;

    /**
     * 删除日期
     */
    @ApiModelProperty(value = "删除日期")
    @JsonFormat(pattern = Constant.DateTimeFormat.DATE_TIME_PATTERN)
    protected LocalDateTime deleteDate;

    /**
     * 删除人
     */
    @ApiModelProperty(value = "删除人")
    protected String deleteBy;

    /**
     * 添加日期
     */
    @ApiModelProperty(value = "添加日期")
    @JsonFormat(pattern = Constant.DateTimeFormat.DATE_TIME_PATTERN)
    protected LocalDateTime addDate;

    /**
     * 添加人
     */
    @ApiModelProperty(value = "添加人")
    protected String addBy;

    /**
     * 修改日期
     */
    @ApiModelProperty(value = "修改日期")
    @JsonFormat(pattern = Constant.DateTimeFormat.DATE_TIME_PATTERN)
    protected LocalDateTime modifiedDate;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    protected String modifiedBy;

    /**
     * 乐观锁版本
     */
    @ApiModelProperty(value = "乐观锁版本")
    protected String version;

}