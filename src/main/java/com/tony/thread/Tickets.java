package com.tony.thread;

import java.util.concurrent.Callable;

public class Tickets<Object> implements Callable<Object> {
    @Override
    public Object call() {
        System.out.println(Thread.currentThread().getName() + " --> I am implemented by Callable!");
        return null;
    }
}
