package com.h3c.vdi.viewscreen.multithread;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Date 2020/10/14 16:08
 * @Created by lgw2845
 * 线程池
 */
@Log4j2
public enum ThreadPool {

    //ThreadPool单例
    INSTANCE;

    /**
     * 缺省的核心线程数
     */
    private static final int THREAD_CORE = 6;

    /**
     * 缺省的最大线程数
     */
    private static final int THREAD_MAX = 12;

    /**
     * 缺省的空闲间隔
     */
    private static final int THREAD_IDLE = 30;

    /**
     * 缺省的队列大小
     */
    private static final int THREAD_CAPACITY = 1024;

    /**
     * 当前主机的逻辑核心数
     */
    private final int CORE = Runtime.getRuntime().availableProcessors();

    /**
     * 用于处理IO阻塞类型的线程池，占用CPU资源较少，但是占用时间长。
     */
    private volatile ExecutorService ioBusyService = null;

    /**
     * 用于处理CPU繁忙型工作，VDI 公共事件线程池(异步入库、异步方法执行等)
     */
    private volatile ExecutorService cpuBusyService = null;

    /**
     * 启监控线程
     */
    private volatile ExecutorService monitorService = null;

    /**
     * cpu密集型
     */
    public final static Integer CPU_BUSY_TYPE = 0;

    /**
     * io密集型
     */
    public final static Integer IO_BUSY_TYPE = 1;


    /**
     * 处理异步操作公共线程池
     */
    public ExecutorService getCpuBusyService() {
        if (null == this.cpuBusyService) {
            synchronized (ThreadPool.class) {
                if (null == this.cpuBusyService) {
                    cpuBusyService = new ThreadPoolExecutor(CORE > THREAD_CORE ? CORE + 1 : THREAD_CORE,
                            CORE * 2 > THREAD_MAX ? CORE * 2 : THREAD_MAX, THREAD_IDLE, TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(THREAD_CAPACITY),
                            new DaemonThreadFactory("WebCommonOperateService"),
                            new CustomRejectedExecutionHandler());
                }
            }
        }
        return cpuBusyService;
    }


    public ExecutorService getIoBusyService() {
        if (this.ioBusyService == null) {
            synchronized (ThreadPool.class) {
                if (this.ioBusyService == null) {
                    log.info("the core thread num of cluster is {}", CORE);
                    ioBusyService = new ThreadPoolExecutor(CORE * 10,
                            CORE * 20, THREAD_IDLE, TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(THREAD_CAPACITY * 10),
                            new DaemonThreadFactory("WebPublicCloudService"),
                            new CustomAbortPolicy());
                }
            }
        }
        return ioBusyService;
    }

    /**
     * 创建一个新的线程池
     *
     * @param executorName 线程池名字
     * @param type         线程池类型： 0 为CPU密集型 1 为IO密集型
     * @return
     */
    public ExecutorService getNewService(String executorName, Integer type) {
        log.info("new service,executorName:{},type:{} the core thread num of cluster is {}", executorName, type, CORE);
        if (type.equals(CPU_BUSY_TYPE)) {
            return new ThreadPoolExecutor(CORE > THREAD_CORE ? CORE + 1 : THREAD_CORE,
                    CORE * 2 > THREAD_MAX ? CORE * 2 : THREAD_MAX, THREAD_IDLE, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(THREAD_CAPACITY),
                    new DaemonThreadFactory(executorName),
                    new CustomRejectedExecutionHandler());
        } else if (type.equals(IO_BUSY_TYPE)) {
            return new ThreadPoolExecutor(CORE * 10,
                    CORE * 20, THREAD_IDLE, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(THREAD_CAPACITY),
                    new DaemonThreadFactory(executorName),
                    new CustomAbortPolicy());
        }
        return null;
    }


    /**
     * @return 获取系统监视器的线程池
     */
    public ExecutorService getMonitorService() {
        if (null == monitorService) {
            synchronized (ThreadPool.class) {
                if (null == monitorService) {
                    monitorService = new ThreadPoolExecutor(THREAD_CORE,
                            THREAD_MAX, THREAD_IDLE, TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(THREAD_CAPACITY),
                            new DaemonThreadFactory("WebMonitorService"),
                            new CustomRejectedExecutionHandler());
                }
            }
        }
        return monitorService;
    }


}
