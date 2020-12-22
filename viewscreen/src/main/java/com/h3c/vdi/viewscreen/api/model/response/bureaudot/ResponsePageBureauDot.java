package com.h3c.vdi.viewscreen.api.model.response.bureaudot;

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
 * @Date 2020/11/3 17:58
 * @Created by lgw2845
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "局点分页信息")
public class ResponsePageBureauDot {

    private Long id;

    private Long hostSum;

    private Long classSum;

    private Long terminalSum;

    private String provinceCode;

    private String provinceName;

    private String cityCode;

    private String cityName;

    private String countyCode;

    private String countyName;

    private String college;

    private String uuid;

    private LocalDate onlineDate;

    @JsonFormat(pattern = Constant.DateTimeFormat.DATE_TIME_PATTERN)
    private LocalDateTime addDate;

    @JsonFormat(pattern = Constant.DateTimeFormat.DATE_TIME_PATTERN)
    private LocalDateTime modifiedDate;
}
