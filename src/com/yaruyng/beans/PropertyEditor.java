package com.yaruyng.beans;

public interface PropertyEditor {
    void setAsText(String text);
    void setValue(Object value);
    Object getValue();
    String getAsText();
}