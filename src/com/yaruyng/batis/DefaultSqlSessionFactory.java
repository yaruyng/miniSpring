package com.yaruyng.batis;

import com.yaruyng.beans.factory.annotation.Autowired;
import com.yaruyng.jdbc.core.JdbcTemplate;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DefaultSqlSessionFactory implements SqlSessionFactory{

    @Autowired
    JdbcTemplate jdbcTemplate;

    String mapperLocations;

    Map<String, MapperNode> mapperNodeMap = new HashMap<>();


    public String getMapperLocations() {
        return mapperLocations;
    }
    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public Map<String, MapperNode> getMapperNodeMap() {
        return mapperNodeMap;
    }


    public DefaultSqlSessionFactory(){

    }

    public void init(){
        scanLocation(this.mapperLocations);
        for (Map.Entry<String, MapperNode> entry : this.mapperNodeMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    private void scanLocation(String location){
        String sLocation = this.getClass().getClassLoader().getResource("").getPath()+location;
        System.out.println("mapper location :" + sLocation);
        File dir = new File(sLocation);
        for (File file : dir.listFiles()) {
            if (file.isDirectory()){
                scanLocation(location+"/"+file.getName());
            }else {
                buildMapperNodes(location+"/"+file.getName());
            }
        }
    }

    private Map<String, MapperNode> buildMapperNodes(String filePath){
        System.out.println("filePath :" +filePath);
        SAXReader saxReader=new SAXReader();
        URL xmlPath=this.getClass().getClassLoader().getResource(filePath);
        try {
            Document document = saxReader.read(xmlPath);
            Element rootElement=document.getRootElement();

            String namespace = rootElement.attributeValue("namespace");

            Iterator<Element> nodes = rootElement.elementIterator();;
            while (nodes.hasNext()) {
                Element node = nodes.next();
                String id = node.attributeValue("id");
                String parameterType = node.attributeValue("parameterType");
                String resultType = node.attributeValue("resultType");
                String sql = node.getText();

                MapperNode selectnode = new MapperNode();
                selectnode.setNamespace(namespace);
                selectnode.setId(id);
                selectnode.setParameterType(parameterType);
                selectnode.setResultType(resultType);
                selectnode.setSql(sql);
                selectnode.setParameter("");

                this.mapperNodeMap.put(namespace + "." + id, selectnode);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.mapperNodeMap;

    }

    @Override
    public SqlSession openSession() {
        SqlSession newSqlSession = new DefaultSqlSession();
        newSqlSession.setJdbcTemplate(jdbcTemplate);
        newSqlSession.setSqlSessionFactory(this);
        return newSqlSession;
    }

    @Override
    public MapperNode getMapperNode(String name) {
        return this.mapperNodeMap.get(name);
    }
}
