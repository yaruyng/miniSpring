package com.yaruyng.beans.factory.config;

import com.yaruyng.beans.factory.config.ConstructorArgumentValue;

import java.util.*;

public class ConstructorArgumentValues {

    private final List<ConstructorArgumentValue> genericConstructorArgumentValues = new LinkedList<>();
    public ConstructorArgumentValues(){}
    public void addArgumentValue(ConstructorArgumentValue newValue){
        this.genericConstructorArgumentValues.add(newValue);
    }

    public ConstructorArgumentValue getIndexedArgumentValue(int index)
    { return this.genericConstructorArgumentValues.get(index); }
    public int getArgumentCount() {
        return this.genericConstructorArgumentValues.size();
    }
    public boolean isEmpty() {
        return this.genericConstructorArgumentValues.isEmpty();
    }
}
