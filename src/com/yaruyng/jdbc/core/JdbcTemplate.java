package com.yaruyng.jdbc.core;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class JdbcTemplate {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public DataSource getDataSource(){
        return this.dataSource;
    }

    public JdbcTemplate(){

    }

    public Object query(StatementCallBack stmtCallBack){
        Connection con = null;
        Statement stmt = null;
        try {
            con = dataSource.getConnection();

            stmt = con.createStatement();

            return stmtCallBack.doInStatement(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stmt.close();
                con.close();
            } catch (SQLException e) {
            }
        }
        return null;
    }

    public Object query(String sql, Object[] args, PreparedStatementCallBack pstmtCallBack){
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement(sql);
            ArgumentPreparedStatementSetter preparedStatementSetter = new ArgumentPreparedStatementSetter(args);
            preparedStatementSetter.setValues(pstmt);
            return pstmtCallBack.doInPreparedStatement(pstmt);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                pstmt.close();
                con.close();
            } catch (SQLException e) {
            }
        }
    }

    public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper ){
        RowMapperResultSetExtractor<T> resultSetExtractor = new RowMapperResultSetExtractor<>(rowMapper);
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = dataSource.getConnection();

            pstmt = con.prepareStatement(sql);
            ArgumentPreparedStatementSetter argumentPreparedStatementSetter
                    = new ArgumentPreparedStatementSetter(args);
            argumentPreparedStatementSetter.setValues(pstmt);
            rs = pstmt.executeQuery();

            return resultSetExtractor.extractData(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
