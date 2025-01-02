package com.test;

import com.yaruyng.web.bind.support.WebBindingInitializer;
import com.yaruyng.web.bind.WebDataBinder;

import java.util.Date;

public class DateInitializer implements WebBindingInitializer {
    @Override
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Date.class, "yyyy-MM-dd", false) );
    }
}
