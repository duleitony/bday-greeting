package com.tony.java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class lambdaTest {
    public static void main(String[] args){
        //替代匿名内部类
        //before java8
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("The old runable now is using!");
//            }
//        }).start();
//        //after java8
//        new Thread(()->System.out.println("It's a java8 function!!")).start();
//
//        //对集合进行迭代
//        List<String> languages = Arrays.asList("java","scala","python");
//        //before java8
//        for(String each:languages) {
//            System.out.println(each);
//        }
//        System.out.println();
//
//        //after java8
//        languages.forEach(x -> System.out.println(x));
//        System.out.println();
//        languages.forEach(System.out::println);

//        //用lambda表达式实现map
//        List<Double> cost = Arrays.asList(10.0, 20.0,30.0);
//        cost.stream().map(x -> x + x*0.05).forEach(x -> System.out.println(x));
//
//        //用lambda表达式实现map与reduce
//        double allCost = cost.stream().map(x -> x+x*0.05).reduce((sum,x) -> sum + x).get();
//        System.out.println(allCost);
//
//        //filter
//        List<Double> filteredCost = cost.stream().filter(x -> x > 25.0).collect(Collectors.toList());
//        filteredCost.forEach(x -> System.out.println(x));

        List<String> languages = Arrays.asList("Java","Python","scala","Shell","R");
        System.out.println("Language starts with J: ");
        filterTest(languages,x -> x.startsWith("J"));

        System.out.println("\nLanguage ends with a: ");
        filterTest(languages,x -> x.endsWith("a"));

        System.out.println("\nAll languages: ");
        filterTest(languages,x -> true);

        System.out.println("\nNo languages: ");
        filterTest(languages,x -> false);

        System.out.println("\nLanguage length bigger three: ");
        filterTest(languages,x -> x.length() > 4);
    }

    public static void filterTest(List<String> languages, Predicate<String> condition) {
        languages.stream().filter(x -> condition.test(x)).forEach(x -> System.out.println(x + " "));
    }
}
