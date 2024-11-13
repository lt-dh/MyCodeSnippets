package com.lt.mysql;

import com.lt.config.MysqlConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class DbPoolFactory {


    private DbPoolFactory() {
    }

    private static HikariDataSource mysqlPool;

    public static void initMysql() {
        try {
            if (!Objects.isNull(mysqlPool)) {
                return;
            }
            MysqlConfig c = MysqlConfig.getInstance();
            HikariConfig h = new HikariConfig();
            h.setMaximumPoolSize(c.getMysqlMaxPoolSize());
            h.setMinimumIdle(c.getMysqlMinPoolSize());
            h.setJdbcUrl(c.getMysqlUrl());
            h.setUsername(c.getMysqlUser());
            h.setPoolName("mysql-pool");
            h.setPassword(c.getMysqlPass());
            h.setDriverClassName(c.getMysqlDriver());
            h.setConnectionTimeout(TimeUnit.SECONDS.toMillis(c.getMysqlConnTimeout()));
            h.setIdleTimeout(TimeUnit.SECONDS.toMillis(c.getMysqlIdleTimeout()));
            h.setMaxLifetime(TimeUnit.SECONDS.toMillis(c.getMysqlMaxLifeTimeout()));
            mysqlPool = new HikariDataSource(h);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static Connection getConnection(boolean autoCommit) throws SQLException {
        Connection connection = mysqlPool.getConnection();
        connection.setAutoCommit(autoCommit);
        return connection;
    }

    public static void rollback(Connection connection) throws SQLException {
        if (connection != null) {
            connection.rollback();
        }
    }
}
