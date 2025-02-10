package com.test.service;

import com.test.entity.User;
import com.yaruyng.batis.SqlSession;
import com.yaruyng.batis.SqlSessionFactory;
import com.yaruyng.beans.factory.annotation.Autowired;

import java.sql.ResultSet;

public class UserService {
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    public User getUserInfo(int userid){
        String sqlid = "com.test.entity.User.getUserInfo";
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return (User) sqlSession.selectOne(sqlid, new Object[]{userid}, (pstmt) -> {
            ResultSet rs = pstmt.executeQuery();
            User rtnUser = null;
            if (rs.next()) {
                rtnUser = new User();
                rtnUser.setId(userid);
                rtnUser.setName(rs.getString("name"));
                rtnUser.setBirthday(new java.util.Date(rs.getDate("birthday").getTime()));
            } else {
            }
            return rtnUser;
        });

    }
}
