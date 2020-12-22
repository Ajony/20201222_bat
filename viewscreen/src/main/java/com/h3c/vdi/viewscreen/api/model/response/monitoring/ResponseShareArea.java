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
 * @since 2020/7/8 16:31
 */
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "局点信息")
@Data
public class ResponseShareArea {

    private Long id;

    private String provinceCode;

    private String provinceName;

    private String cityCode;

    private String cityName;

    private String countyCode;

    private String countyName;

    private String college;

    private String location;

    private String uuid;

    private Long classSum;

    private Long desktopSum;

    private Long courseSum;

    private Long userSum;

    private Long terminalSum;

    private Long hostSum;

    private Long attendClassSum;

    private Long attendClassTime;

    private String logicDelete;

    @JsonFormat(pattern = Constant.DateTimeFormat.DATE_TIME_PATTERN)
    private LocalDateTime addDate;

    @JsonFormat(pattern = Constant.DateTimeFormat.DATE_TIME_PATTERN)
    private LocalDateTime modifiedDate;



}
