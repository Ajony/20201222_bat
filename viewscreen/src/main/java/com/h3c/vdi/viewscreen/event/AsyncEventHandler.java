package com.h3c.vdi.viewscreen.event;

import com.h3c.vdi.viewscreen.multithread.ThreadPool;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;

/**
 * @Date 2020/10/14 15:57
 * @Created by lgw2845
 * 异步事件处理器
 */
@Log4j2
@Service
public class AsyncEventHandler implements EventHandler {

    Executor threadPool = ThreadPool.INSTANCE.getNewService("AsyncEventHandler", ThreadPool.IO_BUSY_TYPE);

    Map<EventType, Set<EventListener>> listenerMap = new ConcurrentHashMap<>();


    @Override
    public void addListener(EventType eventType, EventListener eventListener) {
        log.debug("Add event listener. eventType={}, eventListener={}", eventType, eventListener);
        Set<EventListener> eventList = listenerMap.get(eventType);
        if (CollectionUtils.isEmpty(eventList)) {
            eventList = new CopyOnWriteArraySet<>();
        }
        eventList.add(eventListener);
        listenerMap.put(eventType, eventList);
    }


    @Override
    public <T> void publish(EventType eventType, RequestData<T> requestData) {
        log.debug("Receiver a publish, eventType ={}, data={}", eventType, requestData);
        Set<EventListener> eventList = listenerMap.get(eventType);
        if (Objects.isNull(eventList)) {
            log.debug("Publish to no one. No listeners typed {}", eventType);
        } else {
            eventList.forEach(item -> threadPool.execute(() -> onEvent(requestData, item)));
        }
    }


    private <T> void onEvent(RequestData<T> requestData, EventListener listener) {
        try {
            listener.onEvent(requestData);
        } catch (Exception e) {
            log.warn("Publish Event failed. reason: {}", e);
        }
    }


}
