package com.demon.gateway.utils;

import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Map;

/**
 * @description:
 * @author: fanjunxiang
 * @date: 2018年12月03日
 **/
public class MongoUtils {


    /**
     * 根据对象创建mongo的update对象，忽略属性为null的字段
     *
     * @param jsonObject 对象json
     * @return update对象
     */
    @SuppressWarnings("ALL")
    public static Update buildUpdate(Object jsonObject) {
        Update update = new Update();
        Map jsonMap = JsonUtils.fromJson(JsonUtils.toJsonString(jsonObject), Map.class);
        jsonMap.forEach((k, v) -> {
            update.set(String.valueOf(k), v);
        });

        return update;
    }


    /**
     * 判断upsert方法是否执行成功
     *
     * @param updateResult 执行结果
     * @return success or false
     */
    public static Boolean upsertSuccess(UpdateResult updateResult) {

        return updateResult.getUpsertedId() != null || updateResult.getModifiedCount() > 0;
    }
}
