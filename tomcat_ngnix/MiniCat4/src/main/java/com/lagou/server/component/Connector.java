package com.lagou.server.component;

import com.lagou.server.RequestProcessor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * Connector组件
 * @author wuqi
 * @date 2020-06-29 7:13
 */
public class Connector {
    //端口
    private String port;

    //connector所在的Service
    private Service service;


    /**
     * 启动连接
     */
    public void start() throws Exception {
        ServerSocket serverSocket = new ServerSocket(Integer.valueOf(port));

        //创建线程池
        int coreSize = 10;
        int maxPoolSize = 20;
        long keepAliveTime = 100l;
        TimeUnit timeUnit = TimeUnit.MICROSECONDS;
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(20);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler abortPolicy = new ThreadPoolExecutor.AbortPolicy();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(coreSize, maxPoolSize, keepAliveTime, timeUnit, blockingQueue, threadFactory, abortPolicy);

        System.out.println("=========================>MiniCat4已经启动...");

        while (true){
            Socket socket = serverSocket.accept();

            //使用线程
            RequestProcessor requestProcessor = new RequestProcessor(socket, service);
            //使用线程池执行任务
            executor.execute(requestProcessor);
        }
    }


    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
