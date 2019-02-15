package com.demon.gateway.manage.service;

import com.demon.gateway.constant.Constants;
import com.demon.gateway.manage.document.RouteDefinition;
import com.demon.gateway.utils.GenerateUniqueIdUtils;
import com.demon.gateway.utils.JsonUtils;
import com.demon.gateway.utils.MongoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @description:
 * @author: fanjunxiang
 * @date: 2019年01月19日
 */
@Slf4j
@Service
public class RouteDefinitionService {

  private final ReactiveMongoTemplate reactiveMongoTemplate;
  private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

  @Autowired
  public RouteDefinitionService(
      ReactiveMongoTemplate reactiveMongoTemplate,
      ReactiveRedisTemplate<String, String> reactiveRedisTemplate) {
    this.reactiveMongoTemplate = reactiveMongoTemplate;
    this.reactiveRedisTemplate = reactiveRedisTemplate;
  }

  /**
   * 获取路由列表
   *
   * @param page 页码
   * @param size 每页显示数量
   * @return 路由列表
   */
  public Flux<RouteDefinition> getRouteList(Integer page, Integer size) {

    return reactiveMongoTemplate.find(
        Query.query(Criteria.byExample(RouteDefinition.builder().deleted(false).build()))
            .with(
                PageRequest.of(page, size, Sort.by(Sort.Order.desc(RouteDefinition.UPDATE_TIME)))),
        RouteDefinition.class);
  }

  /** 创建路由 */
  public Mono<RouteDefinition> saveRoute(RouteDefinition routeDefinition) {
    routeDefinition.setId(GenerateUniqueIdUtils.generateUniqueId());

    return reactiveMongoTemplate
        .save(routeDefinition)
        .doOnNext(
            rout ->
                reactiveRedisTemplate
                    .opsForHash()
                    .put(Constants.ROUTE_CACHE_KEY, rout.getId(), JsonUtils.toJsonString(rout))
                    .subscribe());
  }

  /** 修改路由 */
  public Mono<Boolean> updateRoute(String id, RouteDefinition routeDefinition) {

    return reactiveMongoTemplate
        .findAndModify(
            Query.query(
                Criteria.byExample(RouteDefinition.builder().id(id).deleted(false).build())),
            MongoUtils.buildUpdate(routeDefinition),
            RouteDefinition.class)
        .doOnNext(
            rout ->
                reactiveRedisTemplate
                    .opsForHash()
                    .put(Constants.ROUTE_CACHE_KEY, rout.getId(), JsonUtils.toJsonString(rout))
                    .subscribe())
        .hasElement();
  }

  /** 删除路由 */
  public Mono<Boolean> deleteRoute(String id) {

    return reactiveMongoTemplate
        .findAndModify(
            Query.query(Criteria.byExample(RouteDefinition.builder().id(id).build())),
            MongoUtils.buildUpdate(RouteDefinition.builder().id(id).deleted(true).build()),
            RouteDefinition.class)
        .doOnNext(
            rout ->
                reactiveRedisTemplate
                    .opsForHash()
                    .remove(Constants.ROUTE_CACHE_KEY, rout.getId())
                    .subscribe())
        .hasElement();
  }
}
