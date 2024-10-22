package com.ruby.test;

import com.ruby.beans.BeansException;
import com.ruby.beans.NoSuchBeanDefinitionException;
import com.ruby.context.ClassPathXmlApplicationContext;

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
