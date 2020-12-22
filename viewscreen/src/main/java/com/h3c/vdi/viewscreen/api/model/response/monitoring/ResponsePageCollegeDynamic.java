package com.h3c.vdi.viewscreen.api.model.response.monitoring;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.h3c.vdi.viewscreen.constant.Constant;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author lgq
 * @since 2020/6/22 14:07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "局点动态分页信息")
public class ResponsePageCollegeDynamic {

    private Long id;

    private Long hostNumber;

    private Long classroomNumber;

    private Long terminalNumber;

    private LocalDate onlineDate;

    private String uuid;

    private String provinceName;

    private String cityName;

    private String college;

    private String logicDelete;

    @JsonFormat(pattern = Constant.DateTimeFormat.DATE_TIME_PATTERN)
    private LocalDateTime addDate;

    @JsonFormat(pattern = Constant.DateTimeFormat.DATE_TIME_PATTERN)
    private LocalDateTime modifiedDate;

    private String version;

}
