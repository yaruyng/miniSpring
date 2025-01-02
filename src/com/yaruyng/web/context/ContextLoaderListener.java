package com.yaruyng.web.context;

import com.yaruyng.web.context.support.XmlWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {

    public static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";
    private WebApplicationContext context;
    public ContextLoaderListener(){};
    public ContextLoaderListener(WebApplicationContext context){
        this.context = context;
    }
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //TODO: init
        initWebbApplicationContext(servletContextEvent.getServletContext());
    }

    private void initWebbApplicationContext(ServletContext servletContext){
        String sContextLocation = servletContext.getInitParameter(CONFIG_LOCATION_PARAM);
        System.out.println("sContextLocation："+ sContextLocation);
        WebApplicationContext wac =new XmlWebApplicationContext(sContextLocation);
        wac.setServletContext(servletContext);
        this.context = wac;
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,this.context);
    }
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
