package com.h3c.vdi.viewscreen.api.model.response.ipconfig;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Date 2020/11/10 16:33
 * @Created by lgw2845
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "ip信息")
public class ResponseIpInfo {

    private String ip;

    private List<String> ipList;

}
