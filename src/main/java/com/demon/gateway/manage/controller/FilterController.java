package com.demon.gateway.manage.controller;

import com.demon.gateway.manage.document.FilterDefinition;
import com.demon.gateway.manage.service.FilterDefinitionService;
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
 **/
@RestController
@RequestMapping("/filter")
@Slf4j
public class FilterController {
    private final FilterDefinitionService filterDefinitionService;

    @Autowired
    public FilterController(FilterDefinitionService filterDefinitionService) {
        this.filterDefinitionService = filterDefinitionService;
    }

    @GetMapping("/{page}/{size}")
    public Flux<FilterDefinition> getFilterList(@PathVariable Integer page, @PathVariable Integer size) {
        log.info("请求方法名：{}，请求参数：page-{},size-{}", "getFilterList", page, size);

        return filterDefinitionService.getFilterList(page, size).map(temp -> {
            log.info("请求方法名：{}，返回参数：{}", "getFilterList", JsonUtils.toJsonString(temp));

            return temp;
        });
    }

    @PostMapping
    public Mono<FilterDefinition> saveFilter(@Validated FilterDefinition filterDefinition) {
        log.info("请求方法名：{}，请求参数：{}", "saveFilter", JsonUtils.toJsonString(filterDefinition));

        return filterDefinitionService.saveFilter(filterDefinition).map(temp -> {
            log.info("请求方法名：{}，返回参数：{}", "saveFilter", JsonUtils.toJsonString(temp));

            return temp;
        });
    }

    @PutMapping("/{id}")
    public Mono<Boolean> updateFilter(@PathVariable String id, @Validated FilterDefinition filterDefinition) {
        log.info("请求方法名：{}，请求参数：{}", "updateFilter", JsonUtils.toJsonString(filterDefinition));
        return filterDefinitionService.updateFilter(id, filterDefinition).map(temp -> {
            log.info("请求方法名：{}，返回参数：{}", "updateFilter", JsonUtils.toJsonString(temp));

            return temp;
        });
    }

    @DeleteMapping("/{id}")
    public Mono<Boolean> deleteFilter(@PathVariable String id) {
        log.info("请求方法名：{}，请求参数：{}", "deleteFilter", JsonUtils.toJsonString(id));
        return filterDefinitionService.deleteFilter(id).map(temp -> {
            log.info("请求方法名：{}，返回参数：{}", "deleteFilter", JsonUtils.toJsonString(temp));

            return temp;
        });
    }
}
