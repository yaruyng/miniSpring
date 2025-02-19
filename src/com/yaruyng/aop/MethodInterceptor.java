package com.yaruyng.aop;

public interface MethodInterceptor extends Interceptor{
    Object invoke(MethodInvocation invocation) throws Throwable;
}
