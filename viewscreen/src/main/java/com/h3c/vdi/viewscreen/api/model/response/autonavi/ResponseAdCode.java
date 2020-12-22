package com.h3c.vdi.viewscreen.api.model.response.autonavi;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Date 2020/11/18 16:52
 * @Created by lgw2845
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "行政区域查询")
public class ResponseAdCode {


    /**
     * status : 1
     * info : OK
     * infocode : 10000
     * count : 2
     * suggestion : {"keywords":[],"cities":[]}
     * districts : [{"citycode":"010","adcode":"110000","name":"北京市","center":"116.405285,39.904989","level":"province","districts":[{"citycode":"010","adcode":"110100","name":"北京城区","center":"116.405285,39.904989","level":"city","districts":[{"citycode":"010","adcode":"110116","name":"怀柔区","center":"116.637122,40.324272","level":"district","districts":[]}]}]}]
     */

    private String status;
    private String info;
    private String infocode;
    private String count;
    private List<DistrictsBeanXX> districts;


    @NoArgsConstructor
    @Data
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class DistrictsBeanXX{
        /**
         * citycode : 010
         * adcode : 110000
         * name : 北京市
         * center : 116.405285,39.904989
         * level : province
         * districts : [{"citycode":"010","adcode":"110100","name":"北京城区","center":"116.405285,39.904989","level":"city","districts":[{"citycode":"010","adcode":"110116","name":"怀柔区","center":"116.637122,40.324272","level":"district","districts":[]}]}]
         */

        private String citycode;
        private String adcode;
        private String name;
        private String center;
        private String level;
        private List<DistrictsBeanX> districts;

        @NoArgsConstructor
        @Data
        @AllArgsConstructor
        @Accessors(chain = true)
        public static class DistrictsBeanX{
            /**
             * citycode : 010
             * adcode : 110100
             * name : 北京城区
             * center : 116.405285,39.904989
             * level : city
             * districts : [{"citycode":"010","adcode":"110116","name":"怀柔区","center":"116.637122,40.324272","level":"district","districts":[]}]
             */

            private String citycode;
            private String adcode;
            private String name;
            private String center;
            private String level;
            private List<DistrictsBean> districts;

            @NoArgsConstructor
            @Data
            @AllArgsConstructor
            @Accessors(chain = true)
            public static class DistrictsBean{
                /**
                 * citycode : 010
                 * adcode : 110116
                 * name : 怀柔区
                 * center : 116.637122,40.324272
                 * level : district
                 * districts : []
                 */

                private String citycode;
                private String adcode;
                private String name;
                private String center;
                private String level;
                private List<?> districts;
            }
        }
    }
}
