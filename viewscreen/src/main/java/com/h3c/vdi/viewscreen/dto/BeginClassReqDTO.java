package com.h3c.vdi.viewscreen.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by x19765 on 2020/10/14.
 */
@Data
@ToString
@ApiModel(description = "")
public class BeginClassReqDTO {

    @ApiModelProperty(value = "教室id")
    private List<Long> classroomIds;

    @ApiModelProperty(value = "教室id")
    private Long currentClassroomId;

    private Integer openSeats;

    private Long courseImageId;

    private Long teacherId;

    private String teacherIp;

    private Integer teacherPort;

    private Boolean freedom;

}
