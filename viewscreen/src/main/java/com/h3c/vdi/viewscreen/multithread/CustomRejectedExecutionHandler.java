package com.h3c.vdi.viewscreen.multithread;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Date 2020/10/14 16:24
 * @Created by lgw2845
 * RejectedExecutionHandler自定义实现类。当任务无法执行时，触发此handler。
 */
@Log4j2
public class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        log.warn("Waiting queue is full, {} rejected, {}", String.valueOf(r), String.valueOf(executor));
    }
}
