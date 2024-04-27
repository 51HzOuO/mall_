package com.demo.mall1.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.QueryRunner;

import java.util.Properties;

public class GetQueryRunner {
    private static final QueryRunner queryRunner;

    static {
        Properties properties = new Properties();
        try {
            properties.load(GetQueryRunner.class.getResourceAsStream("/druid.properties"));
//            properties.load(new FileInputStream("src/main/resources/druid.properties"));
            queryRunner = new QueryRunner(DruidDataSourceFactory.createDataSource(properties));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static QueryRunner getQueryRunner() {
        return queryRunner;
    }
}
