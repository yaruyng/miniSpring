package com.yaruyng.test;

import com.yaruyng.beans.BeansException;
import com.yaruyng.beans.NoSuchBeanDefinitionException;
import com.yaruyng.context.ClassPathXmlApplicationContext;

public class Test1 {
    public static void main(String[] args) throws NoSuchBeanDefinitionException {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
        AService aService ;
        try {
            aService = (AService) classPathXmlApplicationContext.getBean("aservice");
            aService.sayHello();
        } catch (BeansException e) {
            throw new RuntimeException(e);
        }
    }
}
