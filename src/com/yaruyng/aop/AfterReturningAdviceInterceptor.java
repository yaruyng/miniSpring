package com.yaruyng.aop;

public class AfterReturningAdviceInterceptor implements MethodInterceptor {

    private final AfterReturningAdvice advice;

    public AfterReturningAdviceInterceptor(AfterReturningAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object retVal = invocation.proceed();
		this.advice.afterReturning(retVal, invocation.getMethod(), invocation.getArguments(), invocation.getThis());
		return retVal;
    }
    
}
