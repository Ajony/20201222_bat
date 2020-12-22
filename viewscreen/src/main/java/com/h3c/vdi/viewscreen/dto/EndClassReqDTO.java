package com.h3c.vdi.viewscreen.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by x19765 on 2020/10/15.
 */
@Data
public class EndClassReqDTO {
    private List<Long> classroomIds;
    private Long courseImageId;
}
