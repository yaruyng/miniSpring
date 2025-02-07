package com.yaruyng.jdbc.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementCallBack {
    Object doInPreparedStatement(PreparedStatement stmt) throws SQLException;
}
