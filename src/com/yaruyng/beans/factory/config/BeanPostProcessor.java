package com.yaruyng.beans.factory.config;

import com.yaruyng.beans.BeansException;
import com.yaruyng.beans.factory.BeanFactory;

public interface BeanPostProcessor {
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

    void setBeanFactory(BeanFactory beanFactory);
}
