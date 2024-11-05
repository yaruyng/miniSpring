package com.yaruyng.context;

public class ContextRefeshEvent extends ApplicationEvent{
    private static final long serialVersionUID = 1L;
    public ContextRefeshEvent(Object source) {
        super(source);
    }

    public String toString(){
        return this.msg;
    }
}
