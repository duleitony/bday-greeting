package com.tony.thread;

public class NewThreadByThread extends Thread{
    public NewThreadByThread(){}
    public void run(){
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args){
        NewThreadByThread newThreadByThread_01 = new NewThreadByThread();
        newThreadByThread_01.setName("I am a new thread 01");
        NewThreadByThread newThreadByThread_02 = new NewThreadByThread();
        newThreadByThread_02.setName("I am a new thread 02");

        newThreadByThread_01.start();
        newThreadByThread_02.start();
        System.out.println(Thread.currentThread().getName()+ " --> I am main Thread！");
    }
}
