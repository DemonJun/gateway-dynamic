package com.demon.gateway.service;

import com.alibaba.fastjson.JSON;
import com.demon.gateway.config.RouterConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @description:
 * @author: fanjunxiang
 * @date: 2019年02月14日
 */
@Slf4j
@Service
@AutoConfigureAfter(RouterConfigurationProperties.class)
public class GatewayRouteService
    implements RouteDefinitionRepository {

  private final RouterConfigurationProperties routerConfigurationProperties;

  public GatewayRouteService(
      RouterConfigurationProperties routerConfigurationProperties) {
    this.routerConfigurationProperties = routerConfigurationProperties;
  }

  @Override
  public Flux<RouteDefinition> getRouteDefinitions() {
    log.info(JSON.toJSONString(routerConfigurationProperties.getRoutes()));

    return Flux.fromIterable(routerConfigurationProperties.getRoutes());
  }

  @Override
  public Mono<Void> save(Mono<RouteDefinition> route) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> delete(Mono<String> routeId) {
    return Mono.empty();
  }

}
