package com.rutuja.address;

import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        Mono<String> stringMono1 = Mono.just("Datta Jadhav");
        Mono<String> stringMono2 = Mono.just("Rutja Jadhav");
        Mono<String> stringMono3 = Mono.just("Rudransh Jadhav");
       List<String> list = Mono.zip(stringMono1,stringMono2,stringMono3).map(tuple -> {
           return Arrays.asList(tuple.getT1(),tuple.getT2(),tuple.getT3());
        }).block();
         System.out.println("Subscribe :"+list);
    }
}
