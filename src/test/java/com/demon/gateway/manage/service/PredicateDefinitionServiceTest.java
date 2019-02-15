package com.demon.gateway.manage.service;

import com.demon.gateway.GatewayApplicationTests;
import com.demon.gateway.manage.document.PredicateDefinition;
import com.demon.gateway.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class PredicateDefinitionServiceTest extends GatewayApplicationTests {
    @Autowired
    private PredicateDefinitionService predicateDefinitionService;

    @Test
    public void getPredicateList() {
    }

    @Test
    public void savePredicate() {
        predicateDefinitionService.savePredicate(
                PredicateDefinition.builder()
                        .name("test").createTime(DateUtils.getNowDateTimeString())
                        .updateTime(DateUtils.getNowDateTimeString())
                        .deleted(false)
                        .build()
        ).block();
    }

    @Test
    public void updatePredicate() {
        predicateDefinitionService.updatePredicate(
                "170511246718443521",
                PredicateDefinition.builder()
                        .name("test1")
                        .updateTime(DateUtils.getNowDateTimeString())
                        .build()
        ).block();
    }

    @Test
    public void deletePredicate() {
    }
}