package com.yaruyng.context;

import com.yaruyng.beans.BeansException;
import com.yaruyng.beans.factory.ListableBeanFactory;
import com.yaruyng.beans.factory.config.BeanFactoryPostProcessor;
import com.yaruyng.beans.factory.config.ConfigurableBeanFactory;
import com.yaruyng.beans.factory.config.ConfigurableListableBeanFactory;
import com.yaruyng.core.env.Environment;
import com.yaruyng.core.env.EnvironmentCapable;

public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory, ConfigurableBeanFactory,ApplicationEventPublisher {

    String getApplicationName();
    long getStartupDate();
    ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;
    void setEnvironment(Environment environment);
    Environment getEnvironment();
    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);
    void refresh() throws BeansException,IllegalStateException;
    void close();
    boolean isActive();
}
