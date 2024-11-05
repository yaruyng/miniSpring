package com.yaruyng.test;

import com.yaruyng.beans.factory.annotation.Autowired;

public class BaseService {
    @Autowired
    private BaseBaseService basebaseservice;

    public BaseBaseService getBasebaseservice(){
        return basebaseservice;
    }

    public void setBasebaseservice(BaseBaseService basebaseservice){
        this.basebaseservice = basebaseservice;
    }
    public BaseService(){}

    public void sayHello(){
        System.out.println("Base service says hello");
        basebaseservice.sayHello();
    }
}
