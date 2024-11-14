package com.yaruyng.beans.factory.config;

import com.yaruyng.beans.BeansException;
import com.yaruyng.beans.factory.BeanFactory;

public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(BeanFactory beanFactory) throws BeansException;
}
