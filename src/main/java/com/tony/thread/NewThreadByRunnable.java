package com.tony.thread;

public class NewThreadByRunnable {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName()+ " --> I am main Thread！");
        Thread thread_01 = new Thread(new MyThread());
        thread_01.setName("I am thread_01");
        Thread thread_02 = new Thread(new MyThread());
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
