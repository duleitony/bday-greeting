package com.tony.java8.InnerClass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberOuterClass {
    private String name ;
    private int age;

    public static void main(String[] args) {
        MemberOuterClass outerClass = new MemberOuterClass();
        MemberOuterClass.MemberInnerClass innerClass = outerClass.new MemberInnerClass();
        innerClass.display();
    }

    /**
     * A non-static class that is created inside a class but outside a method is called member inner class.
     *
     */
    public class MemberInnerClass{
        public MemberInnerClass(){
            //Can access private member in outterclass
            name = "Tony";
            age = 23;
        }

        public void display(){
            System.out.println("name：" + name +"   ;age：" + age);
        }
    }
}
