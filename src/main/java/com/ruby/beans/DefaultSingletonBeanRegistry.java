package com.ruby.beans;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry{

    protected List<String> beanNames = new ArrayList<>();

    protected Map<String,Object> singletons = new ConcurrentHashMap<>(256);
    protected Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64);
    protected Map<String,Set<String>> dependenciesForBeanMap = new ConcurrentHashMap<>(64);

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletons){
            this.singletons.put(beanName,singletonObject);
            this.beanNames.add(beanName);
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletons.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return this.singletons.containsKey(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return (String[]) this.beanNames.toArray();
    }

    protected void removeSingleton(String beanName){
        synchronized (this.singletons){
            this.beanNames.remove(beanName);
            this.singletons.remove(beanName);
        }
    }

    protected void registerDependentBean(String beanName, String dependentBeanName) {

    }

    protected boolean hasDependentBean(String beanName) {
        return false;
    }
    protected String[] getDependentBeans(String beanName) {
        return null;
    }
    protected String[] getDependenciesForBean(String beanName) {
        return null;
    }

}
