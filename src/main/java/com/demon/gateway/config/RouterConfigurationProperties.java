package com.demon.gateway.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;

/**
 * @ClassName RouterConfigurationProperties.java
 * @author DemonJun
 * @version 1.0.0
 * @Description
 * @createTime 2019-04-18 13:44:00
 */

@Slf4j
@Validated
@Data
@ConfigurationProperties(prefix = "spring.cloud.gateway.custom")
public class RouterConfigurationProperties implements ApplicationEventPublisherAware {

  private ApplicationEventPublisher applicationEventPublisher;

  /**
   * List of Routes
   */
  @NotNull
  @Valid
  private List<RouteDefinition> routes = new ArrayList<>();

  /**
   * List of filter definitions that are applied to every route.
   */
  private List<FilterDefinition> defaultFilters = new ArrayList<>();

  private List<MediaType> streamingMediaTypes = Arrays.asList(MediaType.TEXT_EVENT_STREAM,
      MediaType.APPLICATION_STREAM_JSON);


  @Override
  public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void setRoutes(List<RouteDefinition> routes) {
    this.routes = routes;
    if (routes != null && routes.size() > 0 && log.isDebugEnabled()) {
      log.debug("Routes supplied from Gateway Properties: " + routes);
    }
    this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
  }

}
