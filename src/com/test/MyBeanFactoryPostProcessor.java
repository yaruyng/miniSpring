package com.test;

import com.yaruyng.beans.BeansException;
import com.yaruyng.beans.factory.BeanFactory;
import com.yaruyng.beans.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor{

	@Override
	public void postProcessBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println(".........MyBeanFactoryPostProcessor...........");
		
	}

}
