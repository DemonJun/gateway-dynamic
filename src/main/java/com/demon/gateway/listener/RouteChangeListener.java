package com.demon.gateway.listener;

import com.demon.gateway.constant.Constants;
import com.demon.gateway.service.GatewayRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @description: 路由变化监听器
 * @author: fanjunxiang
 * @date: 2019年02月15日
 **/
@Component
@SuppressWarnings("ALL")
@Slf4j
public class RouteChangeListener implements ApplicationListener<ApplicationStartedEvent> {
    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
    @Autowired
    private GatewayRouteService gatewayRouteService;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        log.info("RouteChangeListener Started");
        reactiveRedisTemplate.listenToChannel(Constants.ROUTE_CHANGE_REDIS_CHANNEL_NAME)
                .doOnNext(message -> {
                    // 反序列化消息，发送刷新路由事件
                    log.info(message.getMessage());
                    gatewayRouteService.refreshRoute();
                }).subscribe();
    }
}
