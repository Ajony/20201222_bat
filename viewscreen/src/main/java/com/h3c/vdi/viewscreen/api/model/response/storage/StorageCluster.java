package com.h3c.vdi.viewscreen.api.model.response.storage;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Date 2020/12/9 10:30
 * @Created by lgw2845
 */

@NoArgsConstructor
@Data
public class StorageCluster {


    /**
     * uuid : 00000000000000000000000000000000
     * iopsPercent : {"trendRate":[{"name":"read","rates":[{"time":"2020-12-05 08:56:50","rate":"0.0"}]},{"name":"write","rates":[{"time":"2020-12-05 08:56:50","rate":"18.0"}]}]}
     */

    private String uuid;
    private ClusterData clusterData;

    @NoArgsConstructor
    @Data
    public static class ClusterData {
        private List<TrendRate> trendRate;

        @NoArgsConstructor
        @Data
        public static class TrendRate {
            /**
             * name : read
             * rates : [{"time":"2020-12-05 08:56:50","rate":"0.0"}]
             */

            private String name;
            private List<Rates> rates;

            @NoArgsConstructor
            @Data
            public static class Rates {
                /**
                 * time : 2020-12-05 08:56:50
                 * rate : 0.0
                 */

                private String time;
                private String rate;
            }
        }
    }
}
