package com.yaruyng.aop;

public class DefaultAdvisor implements Advisor{
    private MethodInterceptor methodInterceptor;

    public DefaultAdvisor() {
    
    }
    @Override
    public MethodInterceptor getMethodInterceptor() {
        // TODO Auto-generated method stub
        return this.methodInterceptor;
    }

    @Override
    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        // TODO Auto-generated method stub
        this.methodInterceptor = methodInterceptor;
    }
    
}
