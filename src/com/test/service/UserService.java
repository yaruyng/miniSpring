package com.test.service;

import com.test.entity.User;
import com.yaruyng.beans.factory.annotation.Autowired;
import com.yaruyng.jdbc.core.JdbcTemplate;
import com.yaruyng.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    public List<User> getUsers(int userid){
        final String sql = "select id, name,birthday from users where id>?";
        return (List<User>)jdbcTemplate.query(sql, new Object[]{(Integer) userid},
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

                        User rtnUser = new User();
                        rtnUser.setId(rs.getInt("id"));
                        rtnUser.setName(rs.getString("name"));
                        rtnUser.setBirthday(new java.util.Date(rs.getDate("birthday").getTime()));

                        return rtnUser;
                    }
                });
    }
}
