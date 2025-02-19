package com.yaruyng.aop;

public interface AopProxyFactory {
    AopProxy createAopProxy(Object target, Advisor advisor);
}
