package com.test;

import com.yaruyng.context.ApplicationListener;
import com.yaruyng.context.ContextRefreshedEvent;

public class MyListener implements ApplicationListener<ContextRefreshedEvent> {
	   @Override
	   public void onApplicationEvent(ContextRefreshedEvent event) {
	      System.out.println(".........refreshed.........beans count : " + event.getApplicationContext().getBeanDefinitionCount());
	   }

}

