package com.tony.java8;

import java.util.Arrays;
import java.util.List;

public class streamTest {
    public static void main(String[] args){
        //Get words starting with a and convert to upper case, then sort and print out
        List<String> myList = Arrays.asList("a1", "a2", "b1", "b3", "c2");
        myList
                .stream()
                .filter(x -> x.startsWith("a"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);
    }
}
