package com.kazma233.blog.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BeanUtils {

    public static <T> Map<String, Object> bean2Map(T obj) {
        Map<String, Object> entityMap = new HashMap<>();
        if (obj == null) {
            return entityMap;
        }

        Class base = obj.getClass();
        do {
            Field[] declaredFields = base.getDeclaredFields();

            for (Field declaredField : declaredFields) {
                if (!declaredField.canAccess(obj)) {
                    declaredField.trySetAccessible();
                }

                String name = declaredField.getName();
                try {
                    Object val = declaredField.get(obj);
                    entityMap.put(name, val);
                } catch (IllegalAccessException ignore) {
                }
            }

            base = base.getSuperclass();
        } while (base != null);

        return entityMap;
    }

}
