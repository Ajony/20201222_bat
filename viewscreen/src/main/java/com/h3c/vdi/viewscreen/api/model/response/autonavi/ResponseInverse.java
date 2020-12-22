package com.h3c.vdi.viewscreen.api.model.response.autonavi;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Date 2020/11/23 17:46
 * @Created by lgw2845
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "逆地理编码")
public class ResponseInverse {


    /**
     * status : 1
     * regeocode : {"addressComponent":{"city":"郑州市","province":"河南省","adcode":"410105","district":"金水区","towncode":"410105008000","streetNumber":{"number":"95号","location":"113.663774,34.785692","direction":"北","distance":"99.3483","street":"文化路"},"country":"中国","township":"文化路街道","businessAreas":[[]],"building":{"name":[],"type":[]},"neighborhood":{"name":[],"type":[]},"citycode":"0371"},"formatted_address":"河南省郑州市金水区文化路街道河南农业大学图书馆河南农业大学文化路校区"}
     * info : OK
     * infocode : 10000
     */

    private String status;
    private RegeocodeBean regeocode;
    private String info;
    private String infocode;

    @NoArgsConstructor
    @Data
    public static class RegeocodeBean {
        /**
         * addressComponent : {"city":"郑州市","province":"河南省","adcode":"410105","district":"金水区","towncode":"410105008000","streetNumber":{"number":"95号","location":"113.663774,34.785692","direction":"北","distance":"99.3483","street":"文化路"},"country":"中国","township":"文化路街道","businessAreas":[[]],"building":{"name":[],"type":[]},"neighborhood":{"name":[],"type":[]},"citycode":"0371"}
         * formatted_address : 河南省郑州市金水区文化路街道河南农业大学图书馆河南农业大学文化路校区
         */

        private AddressComponentBean addressComponent;
        private String formatted_address;

        @NoArgsConstructor
        @Data
        public static class AddressComponentBean {
            /**
             * city : 郑州市
             * province : 河南省
             * adcode : 410105
             * district : 金水区
             * towncode : 410105008000
             * streetNumber : {"number":"95号","location":"113.663774,34.785692","direction":"北","distance":"99.3483","street":"文化路"}
             * country : 中国
             * township : 文化路街道
             * businessAreas : [[]]
             * building : {"name":[],"type":[]}
             * neighborhood : {"name":[],"type":[]}
             * citycode : 0371
             */

            private String city;
            private String province;
            private String adcode;
            private String district;
            private String towncode;
            private StreetNumberBean streetNumber;
            private String country;
            private String township;
//            private BuildingBean building;
//            private NeighborhoodBean neighborhood;
            private String citycode;
            private List<?> businessAreas;

            @NoArgsConstructor
            @Data
            public static class StreetNumberBean {
                /**
                 * number : 95号
                 * location : 113.663774,34.785692
                 * direction : 北
                 * distance : 99.3483
                 * street : 文化路
                 */

                private String number;
                private String location;
                private String direction;
                private String distance;
                private String street;
            }

//            @NoArgsConstructor
//            @Data
//            public static class BuildingBean extends BaseObservable {
//                private List<?> name;
//                private List<?> type;
//            }

//            @NoArgsConstructor
//            @Data
//            public static class NeighborhoodBean extends BaseObservable {
//                private List<?> name;
//                private List<?> type;
//            }
        }
    }
}
