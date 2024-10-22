package com.ruby.beans;

import java.util.*;

public class ArgumentValues {

    private final List<ArgumentValue> genericArgumentValues = new LinkedList<>();
    public ArgumentValues(){}
    public void addArgumentValue(ArgumentValue newValue){
        this.genericArgumentValues.add(newValue);
    }

    public ArgumentValue getIndexedArgumentValue(int index)
    { return this.genericArgumentValues.get(index); }
    public int getArgumentCount() {
        return this.genericArgumentValues.size();
    }
    public boolean isEmpty() {
        return this.genericArgumentValues.isEmpty();
    }
}
