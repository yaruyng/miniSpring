package com.yaruyng.beans;

import com.yaruyng.utils.NumberUtils;
import com.yaruyng.utils.StringUtils;

import java.text.NumberFormat;

public class CustomNumberEditor implements PropertyEditor{

    private Class<? extends Number> numberClass;
    private boolean allowEmpty;
    private NumberFormat numberFormat;
    private Object value;

    public CustomNumberEditor(Class<? extends Number> numberClass,
                              boolean allowEmpty) throws IllegalArgumentException{
        this(numberClass, null, allowEmpty);
    }

    public CustomNumberEditor(Class<? extends Number> numberClass,
                              NumberFormat numberFormat, boolean allowEmpty)throws IllegalArgumentException{
        this.numberClass = numberClass;
        this.numberFormat = numberFormat;
        this.allowEmpty = allowEmpty;
    }
    @Override
    public void setAsText(String text) {
        if(this.allowEmpty && !StringUtils.hasText(text)){
            // treat empty string as null
            setValue(null);
        } else if (this.numberFormat != null) {
            setValue(NumberUtils.parseNumber(text, this.numberClass, this.numberFormat));
        } else {
           setValue(NumberUtils.parseNumber(text, this.numberClass));
        }
    }

    @Override
    public void setValue(Object value) {
        if(value instanceof Number){
            this.value = (NumberUtils.convertNumberToTargetClass((Number) value, this.numberClass));
        }else {
            this.value = value;
        }
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public String getAsText() {
        Object value = this.value;
        if(value == null){
            return "";
        }
        if(this.numberFormat != null){
            return this.numberFormat.format(value);
        }else {
            return value.toString();
        }
    }
}
