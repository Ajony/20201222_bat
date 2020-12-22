package com.h3c.vdi.viewscreen.api.model.response.monitoring;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Classname ResponseArea
 * @Date 2020/7/22 9:45
 * @Created by lgw2845
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "地区信息")
public class ResponseChinaArea {

    private Long id;

    private Long addressParentId;

    private String addressName;

    private Integer addressType;

    private String addressCode;

    private String extName;
}
