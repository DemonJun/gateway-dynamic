package com.demon.gateway.service;

import com.demon.gateway.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.demon.gateway.constant.Constants.ROUTE_CACHE_KEY;

/**
 * @description:
 * @author: fanjunxiang
 * @date: 2019年02月14日
 */
@Slf4j
@Service
@SuppressWarnings("ALL")
public class GatewayRouteService
    implements RouteDefinitionRepository, ApplicationEventPublisherAware {
  private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
  private ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  public GatewayRouteService(ReactiveRedisTemplate<String, String> reactiveRedisTemplate) {
    this.reactiveRedisTemplate = reactiveRedisTemplate;
  }

  @Override
  public Flux<RouteDefinition> getRouteDefinitions() {

    return reactiveRedisTemplate
        .opsForHash()
        .values(ROUTE_CACHE_KEY)
        .map(
            routeJson -> {
              log.info("route info {}", routeJson);

              return JsonUtils.fromJson(
                  routeJson.toString(),
                  org.springframework.cloud.gateway.route.RouteDefinition.class);
            });
  }

  @Override
  public Mono<Void> save(Mono<RouteDefinition> route) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> delete(Mono<String> routeId) {
    return Mono.empty();
  }

  public void refreshRoute() {
    this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
  }

  @Override
  public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }
}
