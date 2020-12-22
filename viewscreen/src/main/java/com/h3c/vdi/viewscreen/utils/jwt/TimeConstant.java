package com.h3c.vdi.viewscreen.utils.jwt;

import java.time.ZoneId;

/**
 * @Date 2020/7/17 17:07
 * @Created by lgw2845
 */
public interface TimeConstant {

    ZoneId BEIJING_ZONE = ZoneId.of("UTC+08:00");

    Long SECOND_IN_MILLS = 1000L;

    Long MINUTE_IN_MILLS = SECOND_IN_MILLS * 60;

    Long HOUR_IN_MILLIS = MINUTE_IN_MILLS * 60;

    Long DEFAULT_LEEWAY_SECONDS = 60 * 30L;

    Long DAY_IN_MILLS = HOUR_IN_MILLIS * 24;

}
