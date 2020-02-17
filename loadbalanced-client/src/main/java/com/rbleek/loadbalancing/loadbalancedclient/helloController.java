package com.rbleek.loadbalancing.loadbalancedclient;

import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class helloController {

    private final WebClient.Builder webClientBuilder;
    private final ReactorLoadBalancerExchangeFilterFunction loadBalancerFunction;

    public helloController(WebClient.Builder webClientBuilder, ReactorLoadBalancerExchangeFilterFunction loadBalancerFunction) {
        this.webClientBuilder = webClientBuilder;
        this.loadBalancerFunction = loadBalancerFunction;
    }

    @RequestMapping("/hello")
    public Mono<String> hello() {
        return webClientBuilder.build().get().uri("http://hello-service/hello")
                .retrieve()
                .bodyToMono(String.class);
    }
}
