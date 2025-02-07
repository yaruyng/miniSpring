package com.yaruyng.jdbc.core;

import java.sql.SQLException;
import java.sql.Statement;

public interface StatementCallBack {
    Object doInStatement(Statement stmt) throws SQLException;
}
