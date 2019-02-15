package com.demon.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/** @author jundemon */
@EnableEurekaClient
@SpringCloudApplication
@EnableReactiveMongoRepositories
@EnableHystrix
@Slf4j
public class GatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(GatewayApplication.class, args);
  }
}
