package com.demon.gateway.utils;

import org.joda.time.LocalDateTime;

/**
 * @description:
 * @author: fanjunxiang
 * @date: 2019年01月22日
 */
public class DateUtils {
  /** 时间格式：yyyy-MM-dd HH:mm:ss */
  public static final String DATA_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
  /** 时间格式：yyyy-MM-dd */
  public static final String DATA_PATTERN = "yyyy-MM-dd";

  /**
   * 获取当前时间，格式：yyyy-MM-dd HH:mm:ss
   *
   * @return 当前时间的字符串
   */
  public static String getNowDateTimeString() {

    return LocalDateTime.now().toString(DATA_TIME_PATTERN);
  }

  /**
   * 获取当前时间，格式：yyyy-MM-dd
   *
   * @return 当前时间的字符串
   */
  public static String getNowDateString() {

    return LocalDateTime.now().toString(DATA_PATTERN);
  }
}
