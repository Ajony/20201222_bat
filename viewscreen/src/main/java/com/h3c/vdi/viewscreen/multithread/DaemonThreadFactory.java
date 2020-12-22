package com.h3c.vdi.viewscreen.multithread;

import java.util.concurrent.ThreadFactory;

/**
 * @Date 2020/10/14 16:18
 * @Created by lgw2845
 * ThreadFactory自定义实现类
 */
public class DaemonThreadFactory implements ThreadFactory {

    private String threadName = "DaemonThread";

    public DaemonThreadFactory() {
    }

    public DaemonThreadFactory(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        if (this.threadName != null) {
            thread.setName(this.threadName + "-" + thread.getId());
        }
        return thread;
    }
}
