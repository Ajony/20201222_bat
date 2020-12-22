package com.h3c.vdi.viewscreen.event;

/**
 * @Date 2020/10/14 15:43
 * @Created by lgw2845
 * 事件处理器
 */
public interface EventHandler {

    /**
     * 添加事件监听者
     *
     * @param eventType     事件类型
     * @param eventListener 事件监听者
     */
    void addListener(EventType eventType, EventListener eventListener);

    /**
     * 发布事件
     *
     * @param eventType   事件类型
     * @param requestData 事件数据
     * @param <T>
     */
    <T> void publish(EventType eventType, RequestData<T> requestData);


}
