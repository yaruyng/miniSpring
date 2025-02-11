package com.test.controller;

import com.test.entity.User;
import com.test.service.IAction;
import com.yaruyng.beans.factory.annotation.Autowired;
import com.test.service.BaseService;
import com.yaruyng.web.RequestMapping;
import com.yaruyng.web.bind.annotation.ResponseBody;
import com.yaruyng.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @RequestMapping("/test4")
    public void doTest2(HttpServletRequest request, HttpServletResponse response) {
        String str = "test 2, hello world!";
        try {
            response.getWriter().write(str);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @RequestMapping("/test5")
    public ModelAndView doTest5(User user) {
        ModelAndView mav = new ModelAndView("test","msg",user.getName());
        return mav;
    }
    @RequestMapping("/test6")
    public String doTest6(User user) {
        return "error";
    }

    @RequestMapping("/test7")
    @ResponseBody
    public User doTest7(User user) {
        System.out.println(user.getBirthday());
        user.setName(user.getName() + "---");
        //user.setBirthday(new Date());
        return user;
    }

    @Autowired
    IAction action;

    @RequestMapping("/testAop")
    public void doTestAop(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("action -------------- " + action + "----------------");

        action.doAction();

        String str = "test aop, hello world!";
        try {
            response.getWriter().write(str);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}