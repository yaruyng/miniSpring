package com.test.controller;

import com.yaruyng.beans.factory.annotation.Autowired;
import com.yaruyng.test.BaseService;
import com.yaruyng.web.RequestMapping;

public class HelloWorldBean {
    @Autowired
    BaseService baseservice;
    @RequestMapping("/test1")
    public String doTest1() {
        return "test 1, hello world!";
    }
    @RequestMapping("/test2")
    public String doTest2() {
        return "test 2, hello world!";
    }
    @RequestMapping("/test3")
    public String doTest3() {
        return baseservice.getHello();
    }

}
