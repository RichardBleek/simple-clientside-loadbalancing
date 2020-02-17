package com.rbleek.loadbalancing.loadbalancedclient;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Configuration
public class HelloServiceConfiguration {

    @Bean
    @Primary
    ServiceInstanceListSupplier serviceInstanceListSupplier(){
        return new HelloServiceInstaceListSupplier();
    }
}

class HelloServiceInstaceListSupplier implements ServiceInstanceListSupplier {

    private final String serviceName = "hello-service";

    @Override
    public String getServiceId() {
        return serviceName;
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return Flux.just(Arrays.asList(
                new DefaultServiceInstance(serviceName + "-a", serviceName, "localhost", 8081, false),
                new DefaultServiceInstance(serviceName + "-b", serviceName, "localhost", 8082, false),
                new DefaultServiceInstance(serviceName + "-c", serviceName, "localhost", 8083, false)
        ));
    }
}
