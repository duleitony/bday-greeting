package com.tony.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewThreadByThreadPool {
    private static int MAX_POOL_NUM = 10;
    private static int INITIL_POOL_NUM = 5;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(INITIL_POOL_NUM);
        for(int i = 0; i<MAX_POOL_NUM; i++){
            RunnableThread runnableThread = new RunnableThread();
            executorService.execute(runnableThread);

        }
        executorService.shutdown();
    }

    static class RunnableThread implements Runnable {
        @Override
        public void run(){
            System.out.println(Thread.currentThread().getName() + " --> I am impletemented by implementing Thread Pool！");
        }
    }
}
