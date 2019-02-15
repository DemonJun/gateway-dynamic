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
import java.io.Serializable;
import java.util.Map;

/**
 * @description:
 * @author: fanjunxiang
 * @date: 2019年02月14日
 **/
@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Validated
public class PredicateDefinition implements Serializable {
    private static final long serialVersionUID = 1420017893461621711L;
    @Id
    private String id;
    @NotBlank(message = "规则名称不能为空")
    private String name;
    private Map<String, String> args;

    private String createTime;
    private String updateTime;
    private Boolean deleted;

    public static final String UPDATE_TIME = "updateTime";
}
