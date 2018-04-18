package com.tony.java8.InnerClass;

public class AnonymousOuterClass {
    public static void main(String[] args){
        /**
         *
         * A class that have no name is known as anonymous inner class in java.
         * It should be used if you have to override method of class or interface. Java Anonymous inner class can be created by two ways:
         * 1. Class (may be abstract or concrete).
         * 2. Interface
         */
        Person person = new Person() {
            @Override
            public void eat() {
                System.out.println("This is anonymous inner class.");
            }
        };
        person.eat();
    }
}
