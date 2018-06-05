package com.tony.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class NewThreadByCallable {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName()+ " --> I am main Thread！");

        Callable<Object> callable_01 = new Tickets<Object>();
        FutureTask<Object> futureTask_01 = new FutureTask<Object>(callable_01);
        Thread thread_01 = new Thread(futureTask_01);
        thread_01.setName("I am thread_01");

        Callable<Object> callable_02 = new Tickets<Object>();
        FutureTask<Object> futureTask_02 = new FutureTask<Object>(callable_02);
        Thread thread_02 = new Thread(futureTask_02);
        thread_02.setName("I am thread_02");

        thread_01.start();
        thread_02.start();
    }

    static class MyThread implements Runnable {
        @Override
        public void run(){
            System.out.println(Thread.currentThread().getName() + " --> I am impletemented by implementing Runnable IF！");
        }
    }
}
