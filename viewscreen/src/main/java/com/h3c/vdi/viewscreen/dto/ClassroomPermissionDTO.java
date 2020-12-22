package com.h3c.vdi.viewscreen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by x19765 on 2020/10/14.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomPermissionDTO {
    private Long id;
    private Long objectId;
    private Short type;
}
