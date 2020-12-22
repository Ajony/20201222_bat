package com.h3c.vdi.viewscreen.api.model.response.monitoring;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author lgq
 * @since 2020/6/22 15:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "地区课程排行")
public class ResponseCourseRankings {

    private String college;

    private Long attendClassSum;

    private Long attendClassTime;

}
