package com.sis.server.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ObjectUtils {

  @SuppressWarnings("unchecked")
  public static <T> T findInstanceOf(Class<T> clazz, Object[] objects) {
    for (Object obj : objects) {
      if (clazz.isInstance(obj)) return (T) obj;
    }
    return null;
  }
}
