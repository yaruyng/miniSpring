package com.yaruyng.beans.factory.support;

import com.yaruyng.beans.*;
import com.yaruyng.beans.factory.BeanFactory;
import com.yaruyng.beans.factory.config.BeanDefinition;
import com.yaruyng.beans.factory.config.ConstructorArgumentValue;
import com.yaruyng.beans.factory.config.ConstructorArgumentValues;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
    private Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>(256);
    private List<String> beanDefinitionNames = new ArrayList<>();
    private final Map<String, Object> earlySingletonObjects = new HashMap<>();
    public SimpleBeanFactory(){}

    //创建容器中所有Bean
    public void refresh(){
        for (String beanDefinitionName : beanDefinitionNames) {
            try {
                getBean(beanDefinitionName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }

    public Object getBean(String beanName) throws BeansException {
        Object singleton = this.getSingleton(beanName);

        if (singleton == null) {
            singleton = this.earlySingletonObjects.get(beanName);
            if(singleton == null){
                System.out.println("get bean null -------------- " + beanName);
                BeanDefinition beanDefinition = beanDefinitions.get(beanName);
                singleton = createBean(beanDefinition);
                this.registerSingleton(beanName,singleton);
            }
        }
        if (singleton == null) {
            throw new BeansException("bean is null.");
        }
        return singleton;
    }
    @Override
    public boolean containsBean(String name){
        return containsSingleton(name);
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition bd) {
        this.beanDefinitions.put(name,bd);
        this.beanDefinitionNames.add(name);
//        if(!bd.isLazyInit()){
//            try {
//                getBean(name);
//            } catch (BeansException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public void registerBean(String beanName, Object obj){
        this.registerSingleton(beanName,obj);
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitions.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitions.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitions.containsKey(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitions.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitions.get(name).isPrototype();
    }

    @Override
    public Class<?> getType(String name) {
        return this.beanDefinitions.get(name).getClass();
    }

    private Object createBean(BeanDefinition bd){
        Class<?> clz = null;
        Object obj = doCreateBean(bd);
        this.earlySingletonObjects.put(bd.getId(), obj);
        try {
            clz = Class.forName(bd.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        handleProperties(bd, clz, obj);
        return obj;
    }
    private Object doCreateBean(BeanDefinition bd){
        Class<?> clz = null;
        Object obj = null;
        Constructor<?> con = null;
        try{
            clz = Class.forName(bd.getClassName());
            //handle constructor
            ConstructorArgumentValues constructorArgumentValues = bd.getConstructorArgumentValues();
            if (!constructorArgumentValues.isEmpty()) {
                Class<?>[] paramTypes = new Class<?>[constructorArgumentValues.getArgumentCount()];
                Object[] paramValues = new Object[constructorArgumentValues.getArgumentCount()];
                for (int i = 0; i < constructorArgumentValues.getArgumentCount(); i++) {
                    ConstructorArgumentValue indexedConstructorArgumentValue = constructorArgumentValues.getIndexedArgumentValue(i);
                    if("String".equals(indexedConstructorArgumentValue.getType()) || "Java.lang.String".equals(indexedConstructorArgumentValue.getType())){
                        paramTypes[i] = String.class;
                        paramValues[i] = indexedConstructorArgumentValue.getValue();
                    }
                    else if ("Integer".equals(indexedConstructorArgumentValue.getType()) || "java.lang.Integer".equals(indexedConstructorArgumentValue.getType())) {
                        paramTypes[i] = Integer.class;
                        paramValues[i] = Integer.valueOf((String) indexedConstructorArgumentValue.getValue());
                    }
                    else if ("int".equals(indexedConstructorArgumentValue.getType())) {
                        paramTypes[i] = int.class;
                        paramValues[i] = Integer.valueOf((String) indexedConstructorArgumentValue.getValue()).intValue();
                    }
                    else {
                        paramTypes[i] = String.class;
                        paramValues[i] = indexedConstructorArgumentValue.getValue();
                    }
                }
                try{
                    con = clz.getConstructor(paramTypes);
                    obj = con.newInstance(paramValues);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

            }
            else {
                obj = clz.newInstance();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        System.out.println(bd.getId() + "bean created. "+ bd.getClassName() +" : "+ obj.toString());

        return obj;
    }

    private void handleProperties(BeanDefinition bd, Class<?> clz, Object obj){
        //handle properties
        System.out.println("headle properties for bean : " + bd.getId());
        PropertyValues propertyValues = bd.getPropertyValues();
        if(!propertyValues.isEmpty()){
            for (int i = 0; i < propertyValues.size(); i++) {
                PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
                String pName = propertyValue.getName();
                String pType = propertyValue.getType();
                Object pValue = propertyValue.getValue();
                boolean isRef = propertyValue.getIsRef();
                Class<?>[] paramTypes = new Class<?>[1];
                Object[] paramValues = new Object[1];
                if(!isRef){
                    if ("String".equals(pType) || "java.lang.String".equals(pType)) {
                        paramTypes[0] = String.class;
                    }
                    else if ("Integer".equals(pType) || "java.lang.Integer".equals(pType)) {
                        paramTypes[0] = Integer.class;
                    }
                    else if ("int".equals(pType)) {
                        paramTypes[0] = int.class;
                    }
                    else {
                        paramTypes[0] = String.class;
                    }

                    paramValues[0] = pValue;
                }
                else{
                    //is ref, create the dependent beans
                    try{
                        paramTypes[0] = Class.forName(pType);
                    }catch (ClassNotFoundException e){
                        e.printStackTrace();
                    }
                    try {
                        paramValues[0] = getBean((String)pValue);
                    } catch (BeansException e) {
                        throw new RuntimeException(e);
                    }

                }

                String methodName = "set" + pName.substring(0,1).toUpperCase() + pName.substring(1);
                Method method = null;

                try {
                    method = clz.getMethod(methodName, paramTypes);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }

                try {
                    method.invoke(obj, paramValues);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}