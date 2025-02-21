package com.yaruyng.aop;

public interface PointcutAdvisor extends Advisor {
    Pointcut getPointcut();
}
