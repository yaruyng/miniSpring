package com.yaruyng.beans.factory;

import com.yaruyng.beans.BeansException;

public interface BeanFactory {
    Object getBean(String beanName) throws BeansException;
    boolean containsBean(String name);
    boolean isSingleton(String name);
    boolean isPrototype(String name);
    Class<?> getType(String name);
}
