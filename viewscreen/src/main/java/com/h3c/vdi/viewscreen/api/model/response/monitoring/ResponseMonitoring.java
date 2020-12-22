package com.h3c.vdi.viewscreen.api.model.response.monitoring;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author lgq
 * @since 2020/6/22 10:10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "监控信息")
public class ResponseMonitoring {

    private String totalTimeNationwide;

    private Long classSum;

    private Long desktopSum;

    private Long schoolSum;

    private Long courseSum;

    private Long userSum;

    private Long hostSum;

    private Long terminalSum;

    private Integer type;

    //省市集合
    private List<ResponseChinaAreaDetails> areaList;

    //局点信息集合
    private List<ResponseShareArea> shareAreaList;

    //最新动态集合
    private List<ResponseLastDynamic> lastDynamicList;

    //上课次数集合
    private List<ResponseCourseRankings> attendClassSumList;

    //上课时长集合
    private List<ResponseCourseRankings> attendClassTimeList;

    //省市on_line_info 集合
    private List<ResponseOnLineInfo> onLineInfoList;

}
