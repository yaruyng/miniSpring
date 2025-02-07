package com.yaruyng.jdbc.core;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
            if (args != null){
                for (int i = 0; i < args.length; i++) {
                    Object arg = args[i];
                    if (arg instanceof String){
                        pstmt.setString(i + 1, (String)arg);
                    }else if (arg instanceof Integer){
                        pstmt.setInt(i + 1, (int)arg);
                    }else if (arg instanceof java.util.Date){
                        pstmt.setDate(i + 1, new java.sql.Date(((java.util.Date)arg).getTime()));

                    }

                }
                return pstmtCallBack.doInPreparedStatement(pstmt);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                pstmt.close();
                con.close();
            } catch (SQLException e) {
            }
        }
        return null;
    }

}
