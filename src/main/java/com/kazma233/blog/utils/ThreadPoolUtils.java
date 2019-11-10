package com.kazma233.blog.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolUtils
 *
 * @author Zly
 * @date 2017/1/3
 */
public class ThreadPoolUtils {

    private static Executor cachedThreadPool = new ThreadPoolExecutor(
            100,
            200,
            60,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(100)
    );

    private static class SingleHandler {
        private static ThreadPoolUtils threadPoolUtils = new ThreadPoolUtils();
    }

    private ThreadPoolUtils() {
        /**
         * public ThreadPoolExecutor(int corePoolSize,
         * int maximumPoolSize,
         * long keepAliveTime,
         * TimeUnit unit,
         * BlockingQueue<Runnable> workQueue)
         * <p>
         * corePoolSize 指的是保留的线程池大小。
         * maximumPoolSize 指的是线程池的最大大小。
         * keepAliveTime 指的是空闲线程结束的超时时间。
         * unit 是一个枚举，表示 keepAliveTime 的单位。
         * workQueue 表示存放任务的队列。
         * <p>
         * 不使用 // Executors.newCachedThreadPool();方式创建
         * <p>
         * BlockingQueue 只是一个接口，常用的实现类有 LinkedBlockingQueue 和 ArrayBlockingQueue。
         * 用 LinkedBlockingQueue 的好处在于没有大小限制。这样的话，因为队列不会满，
         * 所以 execute() 不会抛出异常，而线程池中运行的线程数也永远不会超过 corePoolSize 个，keepAliveTime 参数也就没有意义了。
         */
        //cachedThreadPool =
    }

    // 线程池
    public static Executor getCachedThreadPool() {
        return cachedThreadPool;
    }

}
