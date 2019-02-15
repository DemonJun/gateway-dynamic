package com.demon.gateway.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @description:
 * @author: fanjunxiang
 * @date: 2018年12月06日
 **/
@Slf4j
@SuppressWarnings("all")
public class HashUtils {

    /**
     * 计算对象json化（空字段的值为null）后的字符串的hash值（采用sha256算法）
     *
     * @param o 对象
     * @return hash值
     */
    public static String hashWithSHA256(Object o) {
        String jsonString = JSON.toJSONString(o, SerializerFeature.WriteMapNullValue);
        log.info(jsonString);
        return Hashing.sha256().hashString(jsonString, UTF_8).toString();
    }

    /**
     * 计算对象json化（空字段的值为null）后的字符串的hash值（采用sha256算法, 加盐）
     *
     * @param o 对象
     * @return hash值
     */
    public static String hashWithSHA256WithSalt(Object o, String salt) {
        String jsonString = JSON.toJSONString(o, SerializerFeature.WriteMapNullValue);
        log.info(jsonString);

        return Hashing.hmacSha256(salt.getBytes()).hashString(jsonString, UTF_8).toString();
    }
}
