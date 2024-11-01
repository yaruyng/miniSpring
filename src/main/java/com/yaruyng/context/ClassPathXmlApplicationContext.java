package com.yaruyng.context;

import com.yaruyng.beans.*;
import com.yaruyng.beans.factory.BeanFactory;
import com.yaruyng.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.yaruyng.beans.factory.config.AutowireCapableBeanFactory;
import com.yaruyng.beans.factory.config.BeanFactoryPostProcessor;
import com.yaruyng.beans.factory.support.SimpleBeanFactory;
import com.yaruyng.beans.factory.xml.XmlBeanDefinitionReader;
import com.yaruyng.core.ClassPathXmlResource;
import com.yaruyng.core.Resource;

import java.util.ArrayList;
import java.util.List;

public class ClassPathXmlApplicationContext implements BeanFactory,ApplicationEventPublisher{

    AutowireCapableBeanFactory beanFactory;

    private final List<BeanFactoryPostProcessor>  beanFactoryPostProcessors = new ArrayList<>();
    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh){
        Resource res = new ClassPathXmlResource(fileName);
        AutowireCapableBeanFactory beanFactory1 = new AutowireCapableBeanFactory();
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
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    @Override
    public boolean containsBean(String name) {
        return this.beanFactory.containsBean(name);
    }

    public void registerBean(String beanName, Object obj) {
        this.beanFactory.registerBean(beanName,obj);
    }

    @Override
    public boolean isSingleton(String name) {
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        return false;
    }

    @Override
    public Class<?> getType(String name) {
        return null;
    }

    @Override
    public void publishEvent(ApplicationEvent event) {

    }
    public List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors() {
        return this.beanFactoryPostProcessors;
    }

    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        this.beanFactoryPostProcessors.add(postProcessor);
    }
    public void refresh() throws BeansException,IllegalStateException{
        registerBeanPostProcessors(this.beanFactory);
        onRefresh();
    }

    private void registerBeanPostProcessors(AutowireCapableBeanFactory bf) {
        //if (supportAutowire) {
        bf.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
        //}
    }

    private void onRefresh(){
        this.beanFactory.refresh();
    }

}
