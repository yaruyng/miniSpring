package com.yaruyng.batis;

import com.yaruyng.jdbc.core.JdbcTemplate;
import com.yaruyng.jdbc.core.PreparedStatementCallBack;

public class DefaultSqlSession implements SqlSession{

    JdbcTemplate jdbcTemplate;
    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    SqlSessionFactory sqlSessionFactory;
    @Override
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return this.sqlSessionFactory;
    }


    @Override
    public Object selectOne(String sqlid, Object[] args, PreparedStatementCallBack pstmtcallback) {
        System.out.println(sqlid);
        String sql = this.sqlSessionFactory.getMapperNode(sqlid).getSql();
        System.out.println(sql);

        return jdbcTemplate.query(sql, args, pstmtcallback);
    }

    private void buildParameter(){
    }

    private Object resultSet2Obj() {
        return null;
    }
}
