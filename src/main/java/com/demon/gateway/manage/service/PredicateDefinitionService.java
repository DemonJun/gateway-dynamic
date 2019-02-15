package com.demon.gateway.manage.service;

import com.demon.gateway.manage.document.PredicateDefinition;
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
 * @date: 2019年02月14日
 */
@Slf4j
@Service
public class PredicateDefinitionService {

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  @Autowired
  public PredicateDefinitionService(ReactiveMongoTemplate reactiveMongoTemplate) {
    this.reactiveMongoTemplate = reactiveMongoTemplate;
  }

  /**
   * 获取规则列表
   *
   * @param page 页码
   * @param size 每页显示数量
   * @return 规则列表
   */
  public Flux<PredicateDefinition> getPredicateList(Integer page, Integer size) {

    return reactiveMongoTemplate.find(
        Query.query(Criteria.byExample(PredicateDefinition.builder().deleted(false).build()))
            .with(
                PageRequest.of(
                    page, size, Sort.by(Sort.Order.desc(PredicateDefinition.UPDATE_TIME)))),
        PredicateDefinition.class);
  }

    /**
     * 创建规则
     */
  public Mono<PredicateDefinition> savePredicate(PredicateDefinition predicateDefinition) {
    predicateDefinition.setId(GenerateUniqueIdUtils.generateUniqueId());

    return reactiveMongoTemplate.save(predicateDefinition);
  }

    /** 修改规则 */
  public Mono<Boolean> updatePredicate(String id, PredicateDefinition predicateDefinition) {

    return reactiveMongoTemplate
        .findAndModify(
            Query.query(
                Criteria.byExample(PredicateDefinition.builder().id(id).deleted(false).build())),
            MongoUtils.buildUpdate(predicateDefinition),
            PredicateDefinition.class)
        .hasElement();
  }

    /** 删除规则 */
  public Mono<Boolean> deletePredicate(String id) {

    return reactiveMongoTemplate
        .findAndModify(
            Query.query(Criteria.byExample(PredicateDefinition.builder().id(id).build())),
            MongoUtils.buildUpdate(PredicateDefinition.builder().id(id).deleted(true).build()),
            PredicateDefinition.class)
        .hasElement();
  }
}
