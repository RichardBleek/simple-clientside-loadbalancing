package com.rbleek.loadbalancing.helloservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@SpringBootApplication
@Slf4j
public class HelloServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloServiceApplication.class, args);
	}

	@Bean
	public RouterFunction<ServerResponse> route() {
		return RouterFunctions
				.route(GET("/hello"),
						serverRequest -> ok().body(sayHello(), String.class));
	}

	private Mono<String> sayHello() {
		log.info("sayHello called");
	    return Mono.just("Hello!");
	}
}
