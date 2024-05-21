package com.demo.mall1.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class GetQueryRunner {
    protected static final MyQueryRunner queryRunner;
    private static final ThreadLocal<Connection> connection = new ThreadLocal<>();
    protected static DataSource dataSource;

    static {
        Properties properties = new Properties();
        try {
            properties.load(GetQueryRunner.class.getResourceAsStream("/druid.properties"));
//            properties.load(new FileInputStream("src/main/resources/druid.properties"));
            dataSource = DruidDataSourceFactory.createDataSource(properties);
            queryRunner = new MyQueryRunner(dataSource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static MyQueryRunner getQueryRunner() {
        return queryRunner;
    }

    public static Connection getConnection() {
        Connection conn = connection.get();
        if (conn == null) {
            try {
                conn = dataSource.getConnection();
                conn.setAutoCommit(false);
                connection.set(conn);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return conn;
    }

    public static void closeConnection() {
        Connection conn = connection.get();
        if (conn != null) {
            try {
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                connection.remove();
            }
        }
    }
}
