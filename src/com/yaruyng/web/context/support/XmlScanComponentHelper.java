package com.yaruyng.web.context.support;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XmlScanComponentHelper {
    public static List<String> getNodeValue(URL xmlPath){
        List<String> packages = new ArrayList<>();
        SAXReader saxReader = new SAXReader();
        Document document = null;

        try {
            document = saxReader.read(xmlPath);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        Element root = document.getRootElement();
        Iterator<Element> it = root.elementIterator();

        while (it.hasNext()){
            Element element = it.next();
            packages.add(element.attributeValue("base-package"));
        }
        return packages;
    }
}
