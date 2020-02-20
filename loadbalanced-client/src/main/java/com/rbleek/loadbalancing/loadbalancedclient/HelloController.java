package com.rbleek.loadbalancing.loadbalancedclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.retry.Retry;

import java.time.Duration;

@RestController
@Slf4j
public class HelloController {

    private final WebClient.Builder webClientBuilder;
    private final ReactorLoadBalancerExchangeFilterFunction loadBalancerFunction;

    public HelloController(WebClient.Builder webClientBuilder, ReactorLoadBalancerExchangeFilterFunction loadBalancerFunction) {
        this.webClientBuilder = webClientBuilder;
        this.loadBalancerFunction = loadBalancerFunction;
    }

    @RequestMapping("/hello")
    public Mono<String> hello() {
        return webClientBuilder.filter(loadBalancerFunction)
                .build().get().uri("http://hello-service/hello")
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.any()
                        .timeout(Duration.ofSeconds(30L))
                        .exponentialBackoff(Duration.ofMillis(1L), Duration.ofSeconds(4L))
                        .retryMax(6L)
                        .doOnRetry(objectRetryContext -> log.warn("retrying {}", objectRetryContext)))
                .doOnError(e -> log.error("call failed {}", e.getMessage()));
    }
}
