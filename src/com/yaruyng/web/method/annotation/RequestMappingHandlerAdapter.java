package com.yaruyng.web.method.annotation;

import com.yaruyng.beans.BeansException;
import com.yaruyng.context.ApplicationContext;
import com.yaruyng.context.ApplicationContextAware;
import com.yaruyng.http.converter.HttpMessageConverter;
import com.yaruyng.web.bind.annotation.ResponseBody;
import com.yaruyng.web.context.WebApplicationContext;
import com.yaruyng.web.bind.support.WebBindingInitializer;
import com.yaruyng.web.bind.WebDataBinder;
import com.yaruyng.web.bind.support.WebDataBinderFactory;
import com.yaruyng.web.servlet.HandlerAdapter;
import com.yaruyng.web.method.HandlerMethod;
import com.yaruyng.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequestMappingHandlerAdapter implements HandlerAdapter, ApplicationContextAware {
    private ApplicationContext applicationContext = null;
    private WebBindingInitializer webBindingInitializer = null;
    private HttpMessageConverter httpMessageConverter = null;

    public HttpMessageConverter getMessageConverter() {
        return httpMessageConverter;
    }

    public void setMessageConverter(HttpMessageConverter messageConverter) {
        this.httpMessageConverter = messageConverter;
    }

    public RequestMappingHandlerAdapter() {
    }

//    public RequestMappingHandlerAdapter(WebApplicationContext wac){
//        this.wac =wac;
//        try {
//            this.webBindingInitializer = (WebBindingInitializer) this.wac.getBean("webBindingInitializer");
//        } catch (BeansException e) {
//            e.printStackTrace();
//        }
//    }
    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    //todo: handle
        return handleInternal(request, response, (HandlerMethod) handler);
    }

    private ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response,
                                HandlerMethod handler) {
        ModelAndView mv = null;
        try {
            mv = invokeHandlerMethod(request,response,handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    protected  ModelAndView invokeHandlerMethod(HttpServletRequest request,
                                        HttpServletResponse response, HandlerMethod handlerMethod)throws Exception{
        WebDataBinderFactory binderFactory = new WebDataBinderFactory();
        Parameter[] parameters = handlerMethod.getMethod().getParameters();
        Object[] methodParamObjs = new Object[parameters.length];

        int i = 0;
        for (Parameter parameter : parameters) {
            if (parameter.getType() != HttpServletRequest.class && parameter.getType() != HttpServletResponse.class){
                Object paramObject = parameter.getType().newInstance();
                WebDataBinder binder = binderFactory.createBinder(request, paramObject, parameter.getName());
                webBindingInitializer.initBinder(binder);
                binder.bind(request);
                methodParamObjs[i] = paramObject;
            } else if (parameter.getType() == HttpServletRequest.class) {
                methodParamObjs[i] = request;
            } else if (parameter.getType() == HttpServletResponse.class) {
                methodParamObjs[i] = response;
            }
            i++;
        }
        Method invocableMethod = handlerMethod.getMethod();
        Object returnObj = invocableMethod.invoke(handlerMethod.getBean(), methodParamObjs);
        Class<?> returnType = invocableMethod.getReturnType();
        ModelAndView mav = null;
        if (invocableMethod.isAnnotationPresent(ResponseBody.class)){
            this.httpMessageConverter.write(returnObj, response);
        }else if (returnType == void.class) {

        }else {
            if (returnObj instanceof ModelAndView){
                mav = (ModelAndView) returnObj;
            } else if (returnObj instanceof String) {
                String sTarget = (String) returnObj;
                mav = new ModelAndView();
                mav.setViewName(sTarget);
            }
        }
        return mav;

    }
    public WebBindingInitializer getWebBindingInitializer() {
        return webBindingInitializer;
    }

    public void setWebBindingInitializer(WebBindingInitializer webBindingInitializer) {
        this.webBindingInitializer = webBindingInitializer;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
