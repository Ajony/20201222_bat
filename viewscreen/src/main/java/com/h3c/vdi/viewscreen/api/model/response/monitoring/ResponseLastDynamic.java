package com.h3c.vdi.viewscreen.api.model.response.monitoring;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.h3c.vdi.viewscreen.constant.Constant;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author lgq
 * @since 2020/6/22 15:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "地区最新动态")
public class ResponseLastDynamic {

    private String uuid;

    private String college;

    private String classroomName;

    private Integer classroomStatus;

    private String Name;

    @JsonFormat(pattern = Constant.DateTimeFormat.DATE_TIME)
    private LocalDateTime classBeginTime;

    @JsonFormat(pattern = Constant.DateTimeFormat.DATE_TIME)
    private LocalDateTime classOverTime;

    @JsonFormat(pattern = Constant.DateTimeFormat.DATE_TIME)
    private LocalDateTime time;
}
