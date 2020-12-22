package com.h3c.vdi.viewscreen.api.model.response.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.h3c.vdi.viewscreen.constant.Constant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author lgq
 * @since 2020/6/17 17:46
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "")
public class ResponseReport {

    @ApiModelProperty(value = "status")
    private String status;

    @ApiModelProperty(value = "uuid")
    private String uuid;

    @JsonFormat(pattern = Constant.DateTimeFormat.DATE_TIME_PATTERN)
    @ApiModelProperty(value = "时间戳")
    private LocalDateTime modifiedDate;

}
