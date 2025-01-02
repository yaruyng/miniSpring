package com.yaruyng.web.servlet.view;

import com.yaruyng.web.servlet.View;
import com.yaruyng.web.servlet.ViewResolver;

public class InternalResourceViewResolver implements ViewResolver {

    private Class<?> viewClass = null;
    private String viewClassName = "";
    private String prefix = "";
    private String suffix = "";
    private String contentType;

    public InternalResourceViewResolver(){
        if(getViewClass() == null){
            setViewClass(JstlView.class);
        }
    }

    @Override
    public View resolveViewName(String viewName) throws Exception {

        return null;
    }

    protected View buildView(String viewName) throws Exception{
        Class<?> viewClass = getViewClass();
        View view = (View) viewClass.newInstance();
        view.setUrl(getPrefix() + viewName + getSuffix());

        String contentType = getContentType();
        view.setContentType(contentType);
        return view;
    }

    public void setViewClassName(String viewClassName) {
        this.viewClassName = viewClassName;
        Class<?> clz = null;
        try {
            clz =Class.forName(viewClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        setViewClass(clz);
    }

    protected String getViewClassName() {
        return this.viewClassName;
    }
    public void setViewClass(Class<?> viewClass) {
        this.viewClass = viewClass;
    }
    protected Class<?> getViewClass() {
        return this.viewClass;
    }
    public void setPrefix(String prefix) {
        this.prefix = (prefix != null ? prefix : "");
    }
    protected String getPrefix() {
        return this.prefix;
    }
    public void setSuffix(String suffix) {
        this.suffix = (suffix != null ? suffix : "");
    }
    protected String getSuffix() {
        return this.suffix;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    protected String getContentType() {
        return this.contentType;
    }

}
