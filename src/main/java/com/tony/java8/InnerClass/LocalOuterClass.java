package com.tony.java8.InnerClass;

public class LocalOuterClass {
    private int data=30;//instance variable

    void display(){
        /**
         * A class i.e. created inside a method is called local inner class in java.
         * If you want to invoke the methods of local inner class, you must instantiate this class inside the method.
         *
         */
        class LocalInnerClass{
            void msg(){System.out.println(data);}
        }
        LocalInnerClass localInnerClass = new LocalInnerClass();
        localInnerClass.msg();
    }

    public static void main(String[] args){
        LocalOuterClass localOuterClass = new LocalOuterClass();
        localOuterClass.display();
    }
}
