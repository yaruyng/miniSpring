package com.yaruyng.web.servlet;

import com.yaruyng.beans.BeansException;
import com.yaruyng.web.WebApplicationContext;
import com.yaruyng.web.WebBindingInitializer;
import com.yaruyng.web.WebDataBinder;
import com.yaruyng.web.WebDataBinderFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class RequestMappingHandlerAdapter implements HandlerAdapter{
    WebApplicationContext wac;

    private WebBindingInitializer webBindingInitializer = null;

    public RequestMappingHandlerAdapter(WebApplicationContext wac){
        this.wac =wac;
        try {
            this.webBindingInitializer = (WebBindingInitializer) this.wac.getBean("webBindingInitializer");
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }
    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    //todo: handle
        return handleInternal(request, response, (HandlerMethod) handler);
    }

    private ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response,
                                HandlerMethod handler) {
        ModelAndView mv = null;
        try {
            invokeHandlerMethod(request,response,handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    protected  void invokeHandlerMethod(HttpServletRequest request,
                                        HttpServletResponse response, HandlerMethod handlerMethod)throws Exception{
        WebDataBinderFactory binderFactory = new WebDataBinderFactory();
        Parameter[] parameters = handlerMethod.getMethod().getParameters();
        Object[] methodParamObjs = new Object[parameters.length];

        int i = 0;
        for (Parameter parameter : parameters) {
            Object paramObject = parameter.getType().newInstance();
            WebDataBinder binder = binderFactory.createBinder(request, paramObject, parameter.getName());
            webBindingInitializer.initBinder(binder);
            binder.bind(request);
            methodParamObjs[i] = paramObject;
            i++;
        }
        Method invocableMethod = handlerMethod.getMethod();
        Object returnobj = invocableMethod.invoke(handlerMethod.getBean(), methodParamObjs);

        response.getWriter().append(returnobj.toString());

    }
}
