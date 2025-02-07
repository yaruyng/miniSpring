package com.test.service;

import com.test.entity.User;
import com.yaruyng.beans.factory.annotation.Autowired;
import com.yaruyng.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;

public class UserService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public User getUserInfo(int userId){
        final String sql = "select id, name, birthday from users where id = ?";
        return (User)jdbcTemplate.query(sql, new Object[]{ (Integer)userId},
                (pstmt)->{
                    ResultSet rs = pstmt.executeQuery();
                    User rtnUser = null;
                    if (rs.next()){
                        rtnUser = new User();
                        rtnUser.setName(rs.getString("name"));
                        rtnUser.setBirthday(new java.util.Date(rs.getDate("birthday").getTime()));
                    }else {
                    }
                    return  rtnUser;
                });
    }
}
