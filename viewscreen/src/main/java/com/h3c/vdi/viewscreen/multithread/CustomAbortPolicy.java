package com.h3c.vdi.viewscreen.multithread;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Date 2020/10/14 16:20
 * @Created by lgw2845
 * 自定义的中断策略
 */
@Log4j2
public class CustomAbortPolicy implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        log.warn("Waiting queue is full, {} rejected, {}", String.valueOf(r), String.valueOf(executor));
        throw new RejectedExecutionException("当前系统繁忙，请稍后再试。");
    }
}
