package com.progzesp.stalking.service;

import java.lang.reflect.Field;

public interface Service {

    /**
     * Copies all non-static fields to object of the same type.
     * @param destination object to paste to
     * @param source object to copy from
     * @param <T> type of the object
     * @throws IllegalAccessException Cannot access fields of object.
     * @throws NoSuchFieldException No such field in object.
     */
    default <T> void copyNonStatic(T destination, T source) throws IllegalAccessException, NoSuchFieldException {
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

    /**
     * Copies all non-static and not null fields to object of the same type.
     * @param destination object to paste to
     * @param source object to copy from
     * @param <T> type of the object
     * @throws IllegalAccessException Cannot access fields of object.
     * @throws NoSuchFieldException No such field in object.
     */
    default <T> void copyNonStaticNonNull(T destination, T source) throws IllegalAccessException, NoSuchFieldException {
        for (Field field : source.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(source);

            if (!java.lang.reflect.Modifier.isStatic(field.getModifiers()) && value != null)
            {

                Field destField = destination.getClass().getDeclaredField(name);
                destField.setAccessible(true);

                destField.set(destination, value);
            }
        }
    }
}
