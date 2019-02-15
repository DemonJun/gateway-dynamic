package com.demon.gateway.manage.controller;

import com.demon.gateway.manage.document.PredicateDefinition;
import com.demon.gateway.manage.service.PredicateDefinitionService;
import com.demon.gateway.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @description:
 * @author: fanjunxiang
 * @date: 2019年02月14日
 */
@RestController
@RequestMapping("/predicate")
@Slf4j
public class PredicateController {

    private final PredicateDefinitionService predicateDefinitionService;

    @Autowired
    public PredicateController(PredicateDefinitionService predicateDefinitionService) {
        this.predicateDefinitionService = predicateDefinitionService;
    }

    @GetMapping("/{page}/{size}")
    public Flux<PredicateDefinition> getPredicateList(
        @PathVariable Integer page, @PathVariable Integer size) {
        log.info("请求方法名：{}，请求参数：page-{},size-{}", "getPredicateList", page, size);
        page -= 1;

        return predicateDefinitionService
            .getPredicateList(page, size)
            .map(
                temp -> {
                    log.info("请求方法名：{}，返回参数：{}", "getPredicateList", JsonUtils.toJsonString(temp));

                    return temp;
                });
    }

    @PostMapping
    public Mono<PredicateDefinition> savePredicate(
        @Validated PredicateDefinition predicateDefinition) {
        log.info("请求方法名：{}，请求参数：{}", "savePredicate", JsonUtils.toJsonString(predicateDefinition));

        return predicateDefinitionService
            .savePredicate(predicateDefinition)
            .map(
                temp -> {
                    log.info("请求方法名：{}，返回参数：{}", "savePredicate", JsonUtils.toJsonString(temp));

                    return temp;
                });
    }

    @PutMapping("/{id}")
    public Mono<Boolean> updatePredicate(
        @PathVariable String id, @Validated PredicateDefinition predicateDefinition) {
        log.info("请求方法名：{}，请求参数：{}", "updatePredicate",
            JsonUtils.toJsonString(predicateDefinition));

        return predicateDefinitionService
            .updatePredicate(id, predicateDefinition)
            .map(
                temp -> {
                    log.info("请求方法名：{}，返回参数：{}", "updatePredicate", JsonUtils.toJsonString(temp));

                    return temp;
                });
    }

    @DeleteMapping("/{id}")
    public Mono<Boolean> deletePredicate(@PathVariable String id) {
        log.info("请求方法名：{}，请求参数：{}", "deletePredicate", JsonUtils.toJsonString(id));
        return predicateDefinitionService
            .deletePredicate(id)
            .map(
                temp -> {
                    log.info("请求方法名：{}，返回参数：{}", "deletePredicate", JsonUtils.toJsonString(temp));

                    return temp;
                });
    }
}
