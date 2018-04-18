package com.tony.java8.InnerClass;

public class StaticOuterClass {
    public static int data=30;

    /**
     * A static class i.e. created inside a class is called static nested class in java.
     * It cannot access non-static data members and methods. It can be accessed by outer class name.
     *
     * It can access static data members of outer class including private.
     * Static nested class cannot access non-static (instance) data member or method.
     */
    static class StaticInnerClass{
        void msg(){System.out.println("data is "+data);}
    }

    public static void main(String[] args){
        StaticInnerClass staticInnerClass = new StaticInnerClass();
        staticInnerClass.msg();
    }
}
