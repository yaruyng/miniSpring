package com.yaruyng.batis;

import com.yaruyng.jdbc.core.JdbcTemplate;
import com.yaruyng.jdbc.core.PreparedStatementCallBack;

public interface SqlSession {
    void setJdbcTemplate(JdbcTemplate jdbcTemplate);
    void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory);
    Object selectOne(String sqlid, Object[] args, PreparedStatementCallBack pstmtcallback);
}
