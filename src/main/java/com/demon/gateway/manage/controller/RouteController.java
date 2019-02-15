package com.demon.gateway.manage.controller;

import com.demon.gateway.manage.document.RouteDefinition;
import com.demon.gateway.manage.service.RouteDefinitionService;
import com.demon.gateway.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @description:
 * @author: fanjunxiang
 * @date: 2019年02月14日
 */
@RestController
@RequestMapping("/route")
@Slf4j
public class RouteController {
  private final RouteDefinitionService routeDefinitionService;

  @Autowired
  public RouteController(RouteDefinitionService routeDefinitionService) {
    this.routeDefinitionService = routeDefinitionService;
  }

  @GetMapping("/{page}/{size}")
  public Flux<RouteDefinition> getRouteList(
      @PathVariable Integer page, @PathVariable Integer size) {
    log.info("请求方法名：{}，请求参数：page-{},size-{}", "getRouteList", page, size);

    return routeDefinitionService
        .getRouteList(page, size)
        .map(
            temp -> {
              log.info("请求方法名：{}，返回参数：{}", "getRouteList", JsonUtils.toJsonString(temp));

              return temp;
            });
  }

  @PostMapping
  public Mono<RouteDefinition> saveRoute(@Validated RouteDefinition routeDefinition) {
    log.info("请求方法名：{}，请求参数：{}", "saveRoute", JsonUtils.toJsonString(routeDefinition));

    return routeDefinitionService
        .saveRoute(routeDefinition)
        .map(
            temp -> {
              log.info("请求方法名：{}，返回参数：{}", "saveRoute", JsonUtils.toJsonString(temp));

              return temp;
            });
  }

  @PutMapping("/{id}")
  public Mono<Boolean> updateRoute(
      @PathVariable String id, @Validated RouteDefinition routeDefinition) {
    log.info("请求方法名：{}，请求参数：{}", "updateRoute", JsonUtils.toJsonString(routeDefinition));
    return routeDefinitionService
        .updateRoute(id, routeDefinition)
        .map(
            temp -> {
              log.info("请求方法名：{}，返回参数：{}", "updateRoute", JsonUtils.toJsonString(temp));

              return temp;
            });
  }

  @DeleteMapping("/{id}")
  public Mono<Boolean> deleteRoute(@PathVariable String id) {
    log.info("请求方法名：{}，请求参数：{}", "deleteRoute", JsonUtils.toJsonString(id));
    return routeDefinitionService
        .deleteRoute(id)
        .map(
            temp -> {
              log.info("请求方法名：{}，返回参数：{}", "deleteRoute", JsonUtils.toJsonString(temp));

              return temp;
            });
  }
}
