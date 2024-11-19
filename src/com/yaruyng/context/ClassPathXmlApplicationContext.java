package com.yaruyng.context;

import com.yaruyng.beans.BeansException;
import com.yaruyng.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.yaruyng.beans.factory.config.BeanFactoryPostProcessor;
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
        ApplicationListener listener = new ApplicationListener();
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    public void initApplicationEventPublisher() {
        ApplicationEventPublisher applicationEventPublisher = new SimpleApplicationEventPublisher();
        this.setApplicationEventPublisher(applicationEventPublisher);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory bf) {
    }

    @Override
    public void registerBeanPostProcessors(ConfigurableListableBeanFactory bf) {
        this.beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    @Override
    public void onRefresh(){
        this.beanFactory.refresh();
    }

    @Override
    public void finishRefresh() {
        publishEvent(new ContextRefeshEvent("Context Refreshed.."));
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
