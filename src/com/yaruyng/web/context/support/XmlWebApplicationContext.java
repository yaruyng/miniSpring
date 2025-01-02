package com.yaruyng.web.context.support;

import com.yaruyng.context.ClassPathXmlApplicationContext;
import com.yaruyng.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

public class XmlWebApplicationContext extends ClassPathXmlApplicationContext implements WebApplicationContext {
    private ServletContext servletContext;
    public XmlWebApplicationContext(String fileName) {
        super(fileName);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }
}
