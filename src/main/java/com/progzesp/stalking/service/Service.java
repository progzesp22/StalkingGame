package com.progzesp.stalking.service;

import java.lang.reflect.Field;

public interface Service {

    default <T> void copyDiff(T destination, T source) throws IllegalAccessException, NoSuchFieldException {
        for (Field field : source.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(source);

            if (!java.lang.reflect.Modifier.isStatic(field.getModifiers()))
            {

                Field destField = destination.getClass().getDeclaredField(name);
                destField.setAccessible(true);

                destField.set(destination, value);
            }
        }
    }
}
