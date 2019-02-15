package com.demon.gateway.manage.service;

import com.demon.gateway.manage.document.FilterDefinition;
import com.demon.gateway.utils.GenerateUniqueIdUtils;
import com.demon.gateway.utils.MongoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
public class FilterDefinitionService {

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  @Autowired
  public FilterDefinitionService(ReactiveMongoTemplate reactiveMongoTemplate) {
    this.reactiveMongoTemplate = reactiveMongoTemplate;
  }

  /**
   * 获取过滤器列表
   *
   * @param page 页码
   * @param size 每页显示数量
   * @return 过滤器列表
   */
  public Flux<FilterDefinition> getFilterList(Integer page, Integer size) {

    return reactiveMongoTemplate.find(
        Query.query(Criteria.byExample(FilterDefinition.builder().deleted(false).build()))
            .with(
                PageRequest.of(page, size, Sort.by(Sort.Order.desc(FilterDefinition.UPDATE_TIME)))),
        FilterDefinition.class);
  }

  /** 创建过滤器 */
  public Mono<FilterDefinition> saveFilter(FilterDefinition filterDefinition) {
    filterDefinition.setId(GenerateUniqueIdUtils.generateUniqueId());

    return reactiveMongoTemplate.save(filterDefinition);
  }

  /** 修改过滤器 */
  public Mono<Boolean> updateFilter(String id, FilterDefinition filterDefinition) {

    return reactiveMongoTemplate
        .findAndModify(
            Query.query(
                Criteria.byExample(FilterDefinition.builder().id(id).deleted(false).build())),
            MongoUtils.buildUpdate(filterDefinition),
            FilterDefinition.class)
        .hasElement();
  }

  /** 删除过滤器 */
  public Mono<Boolean> deleteFilter(String id) {

    return reactiveMongoTemplate
        .findAndModify(
            Query.query(Criteria.byExample(FilterDefinition.builder().id(id).build())),
            MongoUtils.buildUpdate(FilterDefinition.builder().id(id).deleted(true).build()),
            FilterDefinition.class)
        .hasElement();
  }
}
