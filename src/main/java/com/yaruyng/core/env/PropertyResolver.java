package com.yaruyng.core.env;

public interface PropertyResolver {
    boolean containsProperty(String key);
    String getProperty(String key);
    String getProperty(String key, String defaultValue);
    <T> T getProperty(String key, Class<T> targetType);
    <T> T getProperty(String key, Class<T> targetType, T defaultValue);
    <T> Class<T> getPropertyAsClass(String key, Class<T> targetType);
    String getRequeriedProperty(String key) throws IllegalStateException;
    <T> T getRequeriedProperty(String key, Class<T> targetType) throws IllegalStateException;
    String resolvePlaceholders(String text);
    String resolveRequeriedPlaceHolder(String text) throws IllegalStateException;
}
