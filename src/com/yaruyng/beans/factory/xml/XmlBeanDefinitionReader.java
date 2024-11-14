package com.yaruyng.beans.factory.xml;

import com.yaruyng.beans.*;
import com.yaruyng.beans.factory.config.BeanDefinition;
import com.yaruyng.beans.factory.config.ConstructorArgumentValue;
import com.yaruyng.beans.factory.config.ConstructorArgumentValues;
import com.yaruyng.beans.factory.support.AbstractBeanFactory;
import com.yaruyng.core.Resource;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;


public class XmlBeanDefinitionReader {
    AbstractBeanFactory bf;
    public XmlBeanDefinitionReader(AbstractBeanFactory bf){
        this.bf = bf;
    }
    public void loadBeanDefinitions(Resource resource){
        while (resource.hasNext()){
            Element element = (Element) resource.next();
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanID,beanClassName);
            //handle properties
            List<Element> propertyElements = element.elements("property");
            PropertyValues pvs = new PropertyValues();
            List<String> refs = new ArrayList<>();
            for (Element e: propertyElements) {
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pValue = e.attributeValue("value");
                String pRef = e.attributeValue("ref");
                String pV = "";
                boolean isRef = false;
                if(pValue != null && !pValue.equals("")){
                    isRef = false;
                    pV = pValue;
                }else if(pRef != null && !pRef.equals("")){
                    isRef = true;
                    pV = pRef;
                    refs.add(pRef);
                }
                pvs.addPropertyValue(new PropertyValue(pType, pName, pV, isRef));
            }
            beanDefinition.setPropertyValues(pvs);
            String[] refArray = refs.toArray(new String[0]);
            beanDefinition.setDependsOn(refArray);
            //end of handle properties

            //get constructor
            List<Element> constructorElements = element.elements("constructor-arg");
            ConstructorArgumentValues avs = new ConstructorArgumentValues();
            for (Element e : constructorElements) {
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pValue = e.attributeValue("value");
                avs.addArgumentValue(new ConstructorArgumentValue(pType,pName,pValue));
            }
            beanDefinition.setConstructorArgumentValues(avs);
            //end of handle constructor
            this.bf.registerBeanDefinition(beanID, beanDefinition);
        }
    }
}
