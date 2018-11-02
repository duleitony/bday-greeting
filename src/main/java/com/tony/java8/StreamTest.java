package com.tony.java8;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamTest {
    public static void main(String[] args){
        //Get words starting with a and convert to upper case, then sort and print out
        List<String> myList = Arrays.asList("a1", "a2", "b1", "b3", "c2");
        myList
                .stream()
                .filter(x -> x.startsWith("a"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);


        //从一列单词中选出以字母a开头的单词，按字母排序后返回前3个
        List<String> list = Lists.newArrayList("are", "where", "anvato", "java", "abc");

        //implement by stream
        list.stream()
                .filter(s -> s.startsWith("a"))
                .sorted()
                .limit(3)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        //implement without stream
        List<String> tempList = Lists.newArrayList();
        List<String> result = Lists.newArrayList();
        for( int i = 0; i < list.size(); i++) {
            if (list.get(i).startsWith("a")) {
                tempList.add(list.get(i));
            }
        }
        tempList.sort(Comparator.naturalOrder());
        result = tempList.subList(0,3);
        result.iterator();
        result.forEach(System.out::println);
    }
}
