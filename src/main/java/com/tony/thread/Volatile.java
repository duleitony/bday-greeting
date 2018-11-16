package com.tony.thread;

public class Volatile {
    private static volatile int count = 0;
    //private static int count = 0;

    public static void main(String[] args) {
        new MultiThread1().start();
        new MultiThread2().start();
    }

    static class MultiThread1 extends Thread {
        public void run() {
            int val = count;
            while(count < 3) {
                if (val != count) {
                    String message = getName() + ": val = " + val + ", count = " + count;
                    System.out.println(message + " 更新");
                    val = count;
                } else {
                    String message = getName() + ": val = " + val + ", count = " + count;
                    System.out.println(message + " 没更新");
                }
            }
        }
    }

    static class MultiThread2 extends Thread {
        public void run() {
            int val = count;
            while(count < 3) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String message = getName() + ": val = " + val + ", count = " + count;
                System.out.println(message);
                count = ++val;
            }
        }
    }
}
