package com.yaruyng.web;

import com.yaruyng.context.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;

public class XmlWebApplicationContext extends ClassPathXmlApplicationContext implements WebApplicationContext {
    public XmlWebApplicationContext(String fileName) {
        super(fileName);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {

    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }
}
