package com.demon.gateway.utils;

import com.google.common.collect.Maps;
import io.jsonwebtoken.*;

import java.util.Map;

/**
 * @description: Token工具类
 * @author: fanjunxiang
 * @date: 2018年08月29日
 */
public class TokenUtils {
  private static final String KEY = "JinYun";
  private static volatile JwtBuilder jwtbuilder = null;
  private static volatile JwtParser jwtParser = null;
  public static final String CACHE_KEY = "Cache_Key";
  public static final String TOKEN_DATA = "Token_Data";
  public static final String TOKEN_HEADER = "Access_Token";
  public static final String TIMESTAMP = "timestamp";

  private TokenUtils() {}

  private static JwtBuilder getJwtBuilder() {
    if (jwtbuilder == null) {
      synchronized (TokenUtils.class) {
        // when more than two threads run into the first null check same time, to avoid instanced
        // more than one time, it needs to be checked again.
        if (jwtbuilder == null) {
          jwtbuilder = Jwts.builder();
        }
      }
    }
    return jwtbuilder;
  }

  private static JwtParser getJwtParser() {
    if (jwtParser == null) {
      synchronized (TokenUtils.class) {
        // when more than two threads run into the first null check same time, to avoid instanced
        // more than one time, it needs to be checked again.
        if (jwtParser == null) {
          jwtParser = Jwts.parser();
        }
      }
    }
    return jwtParser;
  }

  /**
   * 根据ObjectJson生成token
   *
   * @param objectJson 需写入token的信息
   * @param cacheKey 缓存token的key值
   * @return token字符串
   * @author fanjunxiang
   * @date 2018/8/29
   */
  public static String getTokenFromObjectJson(String objectJson, String cacheKey) {
    return TokenUtils.getJwtBuilder()
        .setHeaderParam(TIMESTAMP, System.currentTimeMillis())
        .setSubject(objectJson)
        .setHeaderParam(CACHE_KEY, cacheKey)
        .signWith(SignatureAlgorithm.HS512, KEY)
        .compact();
  }

  /**
   * 根据token解析token中所包含的信息
   *
   * @param token 待解析的token字符串
   * @return token所包含的信息集合（CACHE_KEY，TOKEN_DATA）
   * @author fanjunxiang
   * @date 2018/8/29
   */
  public static Map<String, String> getObjectJsonFromToken(String token) {
    Jws<Claims> parseClaimsJws = TokenUtils.getJwtParser().setSigningKey(KEY).parseClaimsJws(token);
    Map<String, String> result = Maps.newHashMapWithExpectedSize(2);
    result.put(CACHE_KEY, parseClaimsJws.getHeader().get(CACHE_KEY).toString());
    result.put(TOKEN_DATA, parseClaimsJws.getBody().getSubject());

    return result;
  }
}
