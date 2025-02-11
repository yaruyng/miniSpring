package com.yaruyng.beans.factory;

public interface FactoryBean<T> {
    T getObject() throws Exception;

    Class<?> getObjectType();

    default boolean isSingle(){
        return true;
    }
}
