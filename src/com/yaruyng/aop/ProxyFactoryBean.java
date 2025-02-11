package com.yaruyng.aop;

import com.yaruyng.beans.factory.FactoryBean;
import com.yaruyng.utils.ClassUtils;

public class ProxyFactoryBean implements FactoryBean<Object> {

    private AopProxyFactory aopProxyFactory;
    private String[] interceptorNames;
    private String targetName;
    private Object target;
    private ClassLoader proxyClassLoader = ClassUtils.getDefaultClassLoader();
    private Object singletonInstance;

    public ProxyFactoryBean() {
        this.aopProxyFactory = new DefaultAopProxyFactory();
    }
    public void setAopProxyFactory(AopProxyFactory aopProxyFactory) {
        this.aopProxyFactory = aopProxyFactory;
    }
    public AopProxyFactory getAopProxyFactory() {
        return this.aopProxyFactory;
    }
    protected AopProxy createAopProxy() {
        System.out.println("----------createAopProxy for :"+target+"--------");
        return getAopProxyFactory().createAopProxy(target);
    }
    public void setInterceptorNames(String... interceptorNames) {
        this.interceptorNames = interceptorNames;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }
    public Object getTarget() {
        return target;
    }
    public void setTarget(Object target) {
        this.target = target;
    }

    private synchronized Object getSingletonInstance() {
        if (this.singletonInstance == null) {
            this.singletonInstance = getProxy(createAopProxy());
        }
        System.out.println("----------return proxy for :"+this.singletonInstance+"--------"+this.singletonInstance.getClass());

        return this.singletonInstance;
    }
    protected Object getProxy(AopProxy aopProxy) {
        return aopProxy.getProxy();
    }

    @Override
    public Object getObject() throws Exception {
        return getSingletonInstance();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
