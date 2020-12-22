package com.h3c.vdi.viewscreen.utils.httpclient;

/**
 * @author lgq
 * @since 2020/7/8 15:22
 */
public interface HttpConstant {

    String KEY = "bafe6da64c8c7b7659d2eee05759f5de";
    String NULL = "null";
    String ADDRESS_TO_LONGITUDE_URL = "https://restapi.amap.com/v3/geocode/geo?output=json";
    String LOCATION_TO_ADDRESS_URL = "https://restapi.amap.com/v3/geocode/regeo?output=json&radius=1000&extensions=base&batch=false";
    String ADDRESS_UPD = "https://restapi.amap.com/v3/config/district?subdistrict=2&extensions=base";
    String HTTP = "http";
    String HTTPS = "https";
    String HOSTNAME = "devproxy.h3c.com";
    String SSLV3 = "SSLv3";
    int PORT = 8080;
    String USERNAME = "lgw2845";
    String PASSWORD = "qQ123456";

}
