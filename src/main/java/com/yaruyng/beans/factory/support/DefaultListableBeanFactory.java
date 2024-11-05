package com.yaruyng.beans.factory.support;

import com.yaruyng.beans.BeansException;
import com.yaruyng.beans.factory.config.AbstractAutowireCapableBeanFactory;
import com.yaruyng.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.Map;

public class DefaultListableBeanFactory
        extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory {
    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return (String[]) this.beanDefinitionNames.toArray();
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return new String[0];
    }

    @SuppressWarnings("uncheaked")
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return null;
    }

}
