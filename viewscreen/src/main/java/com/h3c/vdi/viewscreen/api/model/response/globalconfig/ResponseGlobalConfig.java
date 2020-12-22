package com.h3c.vdi.viewscreen.api.model.response.globalconfig;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.h3c.vdi.viewscreen.constant.Constant;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Date 2020/7/31 14:54
 * @Created by lgw2845
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "")
public class ResponseGlobalConfig {

    private Long id;

    private String type;

    private String name;

    private String value;

    private String text;

    private String logicDelete;

    @JsonFormat(pattern = Constant.DateTimeFormat.DATE_TIME_PATTERN)
    private LocalDateTime addDate;

}
