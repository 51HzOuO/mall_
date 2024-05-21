package com.demo.mall1.utils;

import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

public class MyQueryRunner extends QueryRunner {
    public MyQueryRunner(DataSource ds) {
        super(ds);
    }

    @Override
    public int execute(String sql, Object... params) throws SQLException {
        return super.execute(GetQueryRunner.getConnection(), sql, params);
    }

    @Override
    public int update(String sql, Object... params) throws SQLException {
        return super.update(GetQueryRunner.getConnection(), sql, params);
    }
}
