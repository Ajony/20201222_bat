package com.h3c.vdi.viewscreen.event;

/**
 * @Date 2020/10/14 15:50
 * @Created by lgw2845
 */
@FunctionalInterface
public interface EventListener {

    void onEvent(RequestData data);
}
