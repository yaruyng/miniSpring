package com.test;

import com.yaruyng.beans.BeansException;
import com.yaruyng.beans.factory.BeanFactory;
import com.yaruyng.beans.factory.config.BeanPostProcessor;

public class LogBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("postProcessBeforeInitialization : " + beanName);
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("postProcessAfterInitialization : " + beanName);
		return bean;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		// TODO Auto-generated method stub
		
	}

}
