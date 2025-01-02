package com.yaruyng.context;

import com.yaruyng.beans.BeansException;

public interface ApplicationContextAware {
	void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
