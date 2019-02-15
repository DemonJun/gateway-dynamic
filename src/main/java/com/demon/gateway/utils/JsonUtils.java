package com.demon.gateway.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @description: Json工具类 默认使用FastJson，额外提供Gson方法
 * @author: fanjunxiang
 * @date: 2019年01月10日
 */
public class JsonUtils {
  private static final Gson GSON_DEFAULT = new GsonBuilder().create();
  private static final Gson GSON_WITH_NULL_FIELD = new GsonBuilder().serializeNulls().create();

  public static Gson getGson() {
    return GSON_DEFAULT;
  }

  public static Gson getWithNullFieldGson() {
    return GSON_WITH_NULL_FIELD;
  }

  /**
   * 对象转json（不含空属性）
   *
   * @param o 待转对象
   * @return json数据
   */
  public static String toJsonString(Object o) {
    return JSON.toJSONString(o);
  }

  /**
   * 对象转json（含空属性，属性值为null）
   *
   * @param o 待转对象
   * @return json数据
   */
  public static String toJsonStringWithNull(Object o) {

    return JSON.toJSONString(o, SerializerFeature.WriteMapNullValue);
  }

  /**
   * json转对象
   *
   * @param json json数据
   * @param type 对象Class
   * @return 对象实例
   */
  public static <T> T fromJson(String json, Class<T> type) {

    return JSONObject.parseObject(json, type);
  }

  /**
   * json转对象List集合
   *
   * @param json json数据
   * @param clazz 对象class
   * @return 对象List集合
   */
  public static <T> List<T> fromJsonToList(String json, Class clazz) {
    Type type = new ParameterizedTypeImpl(clazz);

    return JSONObject.parseObject(json, type);
  }

  /**
   * 使用Gson进行对象转json（不含空属性）
   *
   * @param o 待转对象
   * @return json数据
   */
  public static String toJsonStringUseGson(Object o) {

    return getGson().toJson(o);
  }

  /**
   * 使用Gson进行对象转json（含空属性，属性值为null）
   *
   * @param o 待转对象
   * @return json数据
   */
  public static String toJsonStringWithNullUseGson(Object o) {

    return getWithNullFieldGson().toJson(o);
  }

  /**
   * 使用Gson进行json转对象
   *
   * @param json json数据
   * @param type 对象Class
   * @return 对象实例
   */
  public static <T> T fromJsonUseGson(String json, Class<T> type) {

    return getGson().fromJson(json, type);
  }

  /**
   * 使用Gson进行json转对象List集合
   *
   * @param json json数据
   * @param clazz 对象class
   * @return 对象List集合
   */
  public static <T> List<T> fromJsonToListUseGson(String json, Class clazz) {
    Type type = new ParameterizedTypeImpl(clazz);

    return getGson().fromJson(json, type);
  }

  private static class ParameterizedTypeImpl implements ParameterizedType {
    Class clazz;

    ParameterizedTypeImpl(Class clz) {
      clazz = clz;
    }

    @Override
    public Type[] getActualTypeArguments() {
      return new Type[] {clazz};
    }

    @Override
    public Type getRawType() {
      return List.class;
    }

    @Override
    public Type getOwnerType() {
      return null;
    }
  }
}
