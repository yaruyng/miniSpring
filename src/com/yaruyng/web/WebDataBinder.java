package com.yaruyng.web;

import com.yaruyng.beans.AbstractPropertyAccessor;
import com.yaruyng.beans.PropertyEditor;
import com.yaruyng.beans.PropertyValues;
import com.yaruyng.utils.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class WebDataBinder {
    private Object target;
    private Class<?> clz;
    private String objectName;

    AbstractPropertyAccessor propertyAccessor;

    public WebDataBinder(Object target){
        this(target, "");
    }

    public WebDataBinder(Object target, String targetName){
        this.target = target;
        this.objectName = targetName;
        this.clz = this.target.getClass();
        this.propertyAccessor = new BeanWrapperImpl(this.target);
    }

    public void bind(HttpServletRequest request){
        PropertyValues mpvs = assignParameters(request);
        addBindValues(mpvs, request);
        doBind(mpvs);
    }

    public void doBind(PropertyValues mpvs){
        applyPropertyValues(mpvs);
    }

    protected  void applyPropertyValues(PropertyValues mpvs){
        getPropertyAccessor().setPropertyValues(mpvs);
    }

    protected AbstractPropertyAccessor getPropertyAccessor(){
        return this.propertyAccessor;
    }


    public PropertyValues assignParameters(HttpServletRequest request){
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, "");

        return new PropertyValues(map);
    }

    public void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor){
        getPropertyAccessor().registerCustomEditor(requiredType, propertyEditor);
    }

    public void addBindValues(PropertyValues mpvs, HttpServletRequest request){

    }
}
