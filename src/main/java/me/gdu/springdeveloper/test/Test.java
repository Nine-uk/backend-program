package me.gdu.springdeveloper.test;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args){
        List<Integer> nums = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
//        List<Integer> result = new ArrayList<>();
//
//        for(int i: nums){
//             System.out.println(i);
//            if (i%2==0){
//                result.add(i*2);
//            }
//        }

        List<Integer> result = nums.stream().filter(n -> n%2==0).map(n -> n*2).toList();

        System.out.print(result);
    }
}
