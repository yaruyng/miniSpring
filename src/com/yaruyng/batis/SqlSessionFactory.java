package com.yaruyng.batis;

public interface SqlSessionFactory {
    SqlSession openSession();
    MapperNode getMapperNode(String name);
}
