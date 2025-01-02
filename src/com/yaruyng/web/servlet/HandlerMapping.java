package com.yaruyng.web.servlet;

import com.yaruyng.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    HandlerMethod getHandler(HttpServletRequest request) throws Exception;
}
