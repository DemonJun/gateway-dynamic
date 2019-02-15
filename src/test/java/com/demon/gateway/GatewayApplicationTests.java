package com.demon.gateway;


import com.demon.gateway.constant.Constants;
import com.demon.gateway.manage.document.PredicateDefinition;
import com.demon.gateway.manage.document.RouteDefinition;
import com.demon.gateway.utils.DateUtils;
import com.demon.gateway.utils.GenerateUniqueIdUtils;
import com.demon.gateway.utils.JsonUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayApplication.class)
@Slf4j
@AutoConfigureWebTestClient
public class GatewayApplicationTests {
    @Autowired
    protected WebTestClient webTestClient;
    @Autowired
    protected ReactiveMongoTemplate reactiveMongoTemplate;
    @Autowired
    protected ReactiveRedisTemplate<String, String> reactiveRedisTemplate;


    @Test
    public void contextLoads() {
        List<PredicateDefinition> predicateDefinitionList = Lists.newArrayList();
        Map<String, String> args = Maps.newHashMap();
        args.put(
                "patterns",
                "/search/**"
        );
        predicateDefinitionList.add(
                PredicateDefinition.builder().name("Path").args(args).build()
        );
        RouteDefinition routeDefinition = RouteDefinition.builder()
                .id(GenerateUniqueIdUtils.generateUniqueId())
                .routeId("google").uri("https://www.google.com").predicates(predicateDefinitionList).build();
        reactiveRedisTemplate.opsForHash().put(Constants.ROUTE_CACHE_KEY, "123123", JsonUtils.toJsonString(routeDefinition)).subscribe();
        reactiveRedisTemplate.convertAndSend(Constants.ROUTE_CHANGE_REDIS_CHANNEL_NAME, DateUtils.getNowDateTimeString()).subscribe();
    }

    @Test
    public void testDynamicRoute() {

    }


}
