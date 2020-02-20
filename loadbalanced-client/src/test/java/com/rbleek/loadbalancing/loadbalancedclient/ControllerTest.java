package com.rbleek.loadbalancing.loadbalancedclient;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

@SpringBootTest
public class ControllerTest {

    @Autowired
    HelloController helloController;

    @Test
    public void test() {
        Mono<String> hello = helloController.hello();
        hello.subscribe(System.out::println);
    }
}
