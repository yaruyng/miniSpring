package com.yaruyng.context;

import com.yaruyng.beans.BeansException;
import com.yaruyng.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.yaruyng.beans.factory.config.BeanDefinition;
import com.yaruyng.beans.factory.config.BeanFactoryPostProcessor;
import com.yaruyng.beans.factory.config.BeanPostProcessor;
import com.yaruyng.beans.factory.config.ConfigurableListableBeanFactory;
import com.yaruyng.beans.factory.support.DefaultListableBeanFactory;
import com.yaruyng.beans.factory.xml.XmlBeanDefinitionReader;
import com.yaruyng.core.ClassPathXmlResource;
import com.yaruyng.core.Resource;

import java.util.ArrayList;
import java.util.List;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext{

    DefaultListableBeanFactory beanFactory;

    private final List<BeanFactoryPostProcessor>  beanFactoryPostProcessors = new ArrayList<>();
    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh){
        Resource res = new ClassPathXmlResource(fileName);
        DefaultListableBeanFactory beanFactory1 = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory1);
        reader.loadBeanDefinitions(res);
        this.beanFactory = beanFactory1;
        if(isRefresh){
            try {
                refresh();
            } catch (BeansException e) {
                e.printStackTrace();
            } catch (IllegalStateException e){
                e.printStackTrace();
            }
        }
    }

    public ClassPathXmlApplicationContext(String fileName){
        this(fileName,true);
    }

    @Override
    public void registerListeners() {
        String[] beanDefinitionNames = this.beanFactory.getBeanDefinitionNames();
        for (String bdName : beanDefinitionNames) {
            Object bean = null;
            try {
                bean = getBean(bdName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
            if(bean instanceof ApplicationListener){
                this.getApplicationEventPublisher().addApplicationListener((ApplicationListener<?>) bean);
            }
        }

    }

    @Override
    public void initApplicationEventPublisher() {
        ApplicationEventPublisher applicationEventPublisher = new SimpleApplicationEventPublisher();
        this.setApplicationEventPublisher(applicationEventPublisher);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory bf) {
        String[] bdNames = this.beanFactory.getBeanDefinitionNames();
        for (String bdName : bdNames) {
            BeanDefinition bd = this.beanFactory.getBeanDefinition(bdName);
            String clzName = bd.getClassName();
            Class<?> clz = null;
            try {
                clz = Class.forName(clzName);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            // determine whether a class is the parent class,interface,or identical class og another class
            if (BeanFactoryPostProcessor.class.isAssignableFrom(clz)) {
                try {
                    this.beanFactoryPostProcessors.add((BeanFactoryPostProcessor) clz.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        for (BeanFactoryPostProcessor processor : this.beanFactoryPostProcessors) {
            try {
                processor.postProcessBeanFactory(bf);
            } catch (BeansException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void registerBeanPostProcessors(ConfigurableListableBeanFactory bf) {
        String[] bdNames = this.beanFactory.getBeanDefinitionNames();
        for (String bdName : bdNames) {
            BeanDefinition bd = this.beanFactory.getBeanDefinition(bdName);
            String clzName = bd.getClassName();
            Class<?> clz = null;
            try {
                clz = Class.forName(clzName);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            if (BeanPostProcessor.class.isAssignableFrom(clz)) {
                try {
                    this.beanFactory.addBeanPostProcessor((BeanPostProcessor) clz.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRefresh(){
        this.beanFactory.refresh();
    }

    @Override
    public void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        this.getApplicationEventPublisher().publishEvent(event);
    }

}
