package com.yaruyng.test;

import com.yaruyng.beans.factory.annotation.Autowired;

public class BaseService {
    @Autowired
    private BaseBaseService bbs;

    public BaseBaseService getBbs(){
        return bbs;
    }

    public void setBbs(BaseBaseService bbs){
        this.bbs = bbs;
    }
    public BaseService(){}

    public void sayHello(){
        System.out.println("Base service says hello");
        bbs.sayHello();
    }
}
