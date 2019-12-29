package com.pisces.platform.core.locale;

import java.lang.reflect.Field;
import java.util.Locale;

public interface ILanguageManager {
    void init();

    Locale getLocale();

    String get(Enum<?> key, Object... arguments);

    String get(String key, Object... arguments);

    String get(Class<?> clazz);

    String get(Field field);

    String get(Class<?> clazz, String field);

    String getTips(Class<?> clazz);

    String getTips(Field field);
}
