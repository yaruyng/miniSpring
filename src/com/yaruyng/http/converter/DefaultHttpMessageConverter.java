package com.yaruyng.http.converter;

import com.yaruyng.utils.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DefaultHttpMessageConverter implements HttpMessageConverter{
    String defaultContentType = "text/json;charset=UTF-8";
    String defaultCharacterEncoding = "UTF-8";
    ObjectMapper objectMapper;

    public Object getObject(){
        return objectMapper;
    }
    public void setObjectMapper(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    public DefaultHttpMessageConverter(){}
    @Override
    public void write(Object obj, HttpServletResponse response) throws IOException {
        response.setContentType(defaultContentType);
        response.setCharacterEncoding(defaultCharacterEncoding);
        writeInternal(obj, response);
        response.flushBuffer();
    }

    private void writeInternal(Object obj, HttpServletResponse response) throws IOException {
        String sJonStr = this.objectMapper.writeValuesAsString(obj);
        PrintWriter pw = response.getWriter();
        pw.write(sJonStr);
    }
}
