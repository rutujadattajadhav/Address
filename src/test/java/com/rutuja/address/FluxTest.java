package com.rutuja.address;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxTest {
    @Test
    public void fluxTest(){
        Flux<String> stringFlux = Flux.just("Datta Jadhav","Rutuja jadhav","Rudransh Jadhav")
                .doOnComplete(() -> {
                    System.out.println("Completed");
                })
                .concatWithValues("Rohan Jadhav")
                .log();
        stringFlux.subscribe(s -> System.out.println(s));
    }
}
