package com.yaruyng.web.method.annotation;

import com.yaruyng.beans.BeansException;
import com.yaruyng.context.ApplicationContext;
import com.yaruyng.context.ApplicationContextAware;
import com.yaruyng.web.RequestMapping;
import com.yaruyng.web.context.WebApplicationContext;
import com.yaruyng.web.servlet.HandlerMapping;
import com.yaruyng.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

public class RequestMappingHandlerMapping implements HandlerMapping, ApplicationContextAware {
    ApplicationContext applicationContext;
    private MappingRegistry mappingRegistry = null;

    public RequestMappingHandlerMapping(){
    }

    protected void initMapping(){
        Class<?> clz = null;
        Object obj = null;
        String[] controllerNames = this.applicationContext.getBeanDefinitionNames();
        for (String controllerName : controllerNames) {
            try {
                clz = Class.forName(controllerName);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            try {
                obj = this.applicationContext.getBean(controllerName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
            Method[] methods = clz.getDeclaredMethods();
            if(methods!=null){
                for(Method method : methods){
                    boolean isRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                    if (isRequestMapping){
                        String methodName = method.getName();
                        String urlmapping = method.getAnnotation(RequestMapping.class).value();
                        this.mappingRegistry.getUrlMappingNames().add(urlmapping);
                        this.mappingRegistry.getMappingObjs().put(urlmapping, obj);
                        this.mappingRegistry.getMappingMethods().put(urlmapping, method);
                    }
                }
            }
        }
    }

    @Override
    public HandlerMethod getHandler(HttpServletRequest request) throws Exception {
        if(this.mappingRegistry == null){
            this.mappingRegistry = new MappingRegistry();
            initMapping();
        }
        String sPath = request.getServletPath();

        if (!this.mappingRegistry.getUrlMappingNames().contains(sPath)) {
            return null;
        }

        Method method = this.mappingRegistry.getMappingMethods().get(sPath);
        Object obj = this.mappingRegistry.getMappingObjs().get(sPath);
        Class<?> clz = this.mappingRegistry.getMappingClasses().get(sPath);
        String methodName = this.mappingRegistry.getMappingMethodNames().get(sPath);
        HandlerMethod handlerMethod = new HandlerMethod(method, obj, clz, methodName);

        return handlerMethod;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
