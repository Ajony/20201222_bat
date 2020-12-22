package com.h3c.vdi.viewscreen.api.model.response.autonavi;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author lgq
 * @since 2020/7/7 9:58
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "地理编码")
public class ResponseLocation {


    /**
     * status : 1
     * info : OK
     * infocode : 10000
     * count : 1
     * geocodes : [{"formatted_address":"北京市","country":"中国","province":"北京市","citycode":"010","city":"北京市","district":[],"township":[],"neighborhood":{"name":[],"type":[]},"building":{"name":[],"type":[]},"adcode":"110000","street":[],"number":[],"location":"116.407526,39.904030","level":"省"}]
     */

    private String status;
    private String info;
    private String infocode;
    private String count;
    private List<GeocodesBean> geocodes;

    @NoArgsConstructor
    @Data
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class GeocodesBean extends ResponseLocation {
        /**
         * formatted_address : 北京市
         * country : 中国
         * province : 北京市
         * citycode : 010
         * city : 北京市
         * district : []
         * township : []
         * neighborhood : {"name":[],"type":[]}
         * building : {"name":[],"type":[]}
         * adcode : 110000
         * street : []
         * number : []
         * location : 116.407526,39.904030
         * level : 省
         */

        private String formatted_address;
        private String country;
        private String province;
        private String citycode;
        private String city;
//        private NeighborhoodBean neighborhood;
//        private BuildingBean building;
        private String adcode;
        private String location;
        private String level;
        private String district;
        private String township;
        private String street;
        private String number;

//        @EqualsAndHashCode(callSuper = false)
//        @NoArgsConstructor
//        @Data
//        @AllArgsConstructor
//        @Accessors(chain = true)
//        public static class NeighborhoodBean extends ResponseLocation {
//            private List<?> name;
//            private List<?> type;
//        }
//
//        @EqualsAndHashCode(callSuper = false)
//        @NoArgsConstructor
//        @Data
//        @AllArgsConstructor
//        @Accessors(chain = true)
//        public static class BuildingBean extends ResponseLocation {
//            private List<?> name;
//            private List<?> type;
//        }
    }
}
