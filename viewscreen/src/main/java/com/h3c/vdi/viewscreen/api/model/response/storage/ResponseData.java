package com.h3c.vdi.viewscreen.api.model.response.storage;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Date 2020/12/9 15:30
 * @Created by lgw2845
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "存储集群")
public class ResponseData {

    private List<StorageCluster.ClusterData.TrendRate> trendRate;
}
