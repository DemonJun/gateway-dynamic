package com.demon.gateway.manage.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: fanjunxiang
 * @date: 2019年02月14日
 */
@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Validated
public class RouteDefinition implements Serializable {
  private static final long serialVersionUID = -3674133171390339924L;
  @Id private String id;
  /** 路由id */
  @NotBlank(message = "路由id不能为空")
  private String routeId;
  /** 路由转发地址 */
  @NotBlank(message = "路由转发地址不能为空")
  private String uri;
  /** 路由顺序 */
  @NotNull(message = "路由顺序不能为空")
  private Integer order;
  /** 规则集合 */
  private List<PredicateDefinition> predicates;
  /** 过滤器集合 */
  private List<FilterDefinition> filters;

  private String createTime;
  private String updateTime;
  private Boolean deleted;

  public static final String UPDATE_TIME = "updateTime";
}
