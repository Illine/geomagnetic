package com.illine.weather.geomagnetic.test.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReflectionHelper {

    public static <T, R> void setStaticFinalField(T targetObject, String fieldName, R newValue) throws NoSuchFieldException, IllegalAccessException {
        var field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        var modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.set(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }

}