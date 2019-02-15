package com.demon.gateway.utils;

import java.util.Random;

/** 单号生成工具；类 */
public class GenerateUniqueIdUtils {
  private static SnowflakeIdWorker idSnWorker =
      new SnowflakeIdWorker((long) (new Random().nextInt(31)));

  public static synchronized String generateUniqueId() {
    return String.valueOf(idSnWorker.nextId());
  }
}
