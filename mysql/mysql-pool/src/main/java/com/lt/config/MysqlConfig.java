package com.lt.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * @author liutong
 * @date 2024年05月21日 15:25
 */
public class MysqlConfig {

    /**
     * mysql配置
     */
    private String mysqlDriver;
    private String mysqlUrl;
    private String mysqlUser;
    private String mysqlPass;
    private int mysqlMaxPoolSize;
    private int mysqlMinPoolSize;
    private int mysqlConnTimeout;
    private int mysqlIdleTimeout;
    private int mysqlMaxLifeTimeout;
    private int mysqlWorkerPoolCoreThreads;
    private int mysqlWorkerPoolNonCoreThreads;
    private int mysqlWorkerPoolAliveTime;

    private MysqlConfig() {
    }

    private static MysqlConfig mysqlConfig;

    public static MysqlConfig getInstance() {
        return mysqlConfig;
    }


    public static void initConfig() {
        if (!Objects.isNull(mysqlConfig)){
            return;
        }
        try (InputStream resourceInputStream = MysqlConfig.class.getClassLoader().getResourceAsStream("server.properties")) {
            Properties properties = new Properties();
            properties.load(resourceInputStream);
            mysqlConfig = new MysqlConfig();
            mysqlConfig.setMysqlDriver(properties.getProperty("mysqlDriver"));
            mysqlConfig.setMysqlUrl(properties.getProperty("mysqlUrl"));
            mysqlConfig.setMysqlUser(properties.getProperty("mysqlUser"));
            mysqlConfig.setMysqlPass(properties.getProperty("mysqlPass"));
            mysqlConfig.setMysqlMaxPoolSize(Integer.parseInt(properties.getProperty("mysqlMaxPoolSize")));
            mysqlConfig.setMysqlMinPoolSize(Integer.parseInt(properties.getProperty("mysqlMinPoolSize")));
            mysqlConfig.setMysqlConnTimeout(Integer.parseInt(properties.getProperty("mysqlConnTimeout")));
            mysqlConfig.setMysqlIdleTimeout(Integer.parseInt(properties.getProperty("mysqlIdleTimeout")));
            mysqlConfig.setMysqlMaxLifeTimeout(Integer.parseInt(properties.getProperty("mysqlMaxLifeTimeout")));
            mysqlConfig.setMysqlWorkerPoolCoreThreads(Integer.parseInt(properties.getProperty("mysqlWorkerPoolCoreThreads")));
            mysqlConfig.setMysqlWorkerPoolNonCoreThreads(Integer.parseInt(properties.getProperty("mysqlWorkerPoolNonCoreThreads")));
            mysqlConfig.setMysqlWorkerPoolAliveTime(Integer.parseInt(properties.getProperty("mysqlWorkerPoolAliveTime")));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public String getMysqlDriver() {
        return mysqlDriver;
    }

    public void setMysqlDriver(String mysqlDriver) {
        this.mysqlDriver = mysqlDriver;
    }

    public String getMysqlUrl() {
        return mysqlUrl;
    }

    public void setMysqlUrl(String mysqlUrl) {
        this.mysqlUrl = mysqlUrl;
    }

    public String getMysqlUser() {
        return mysqlUser;
    }

    public void setMysqlUser(String mysqlUser) {
        this.mysqlUser = mysqlUser;
    }

    public String getMysqlPass() {
        return mysqlPass;
    }

    public void setMysqlPass(String mysqlPass) {
        this.mysqlPass = mysqlPass;
    }

    public int getMysqlMaxPoolSize() {
        return mysqlMaxPoolSize;
    }

    public void setMysqlMaxPoolSize(int mysqlMaxPoolSize) {
        this.mysqlMaxPoolSize = mysqlMaxPoolSize;
    }

    public int getMysqlMinPoolSize() {
        return mysqlMinPoolSize;
    }

    public void setMysqlMinPoolSize(int mysqlMinPoolSize) {
        this.mysqlMinPoolSize = mysqlMinPoolSize;
    }

    public int getMysqlConnTimeout() {
        return mysqlConnTimeout;
    }

    public void setMysqlConnTimeout(int mysqlConnTimeout) {
        this.mysqlConnTimeout = mysqlConnTimeout;
    }

    public int getMysqlIdleTimeout() {
        return mysqlIdleTimeout;
    }

    public void setMysqlIdleTimeout(int mysqlIdleTimeout) {
        this.mysqlIdleTimeout = mysqlIdleTimeout;
    }

    public int getMysqlMaxLifeTimeout() {
        return mysqlMaxLifeTimeout;
    }

    public void setMysqlMaxLifeTimeout(int mysqlMaxLifeTimeout) {
        this.mysqlMaxLifeTimeout = mysqlMaxLifeTimeout;
    }

    public int getMysqlWorkerPoolCoreThreads() {
        return mysqlWorkerPoolCoreThreads;
    }

    public void setMysqlWorkerPoolCoreThreads(int mysqlWorkerPoolCoreThreads) {
        this.mysqlWorkerPoolCoreThreads = mysqlWorkerPoolCoreThreads;
    }

    public int getMysqlWorkerPoolNonCoreThreads() {
        return mysqlWorkerPoolNonCoreThreads;
    }

    public void setMysqlWorkerPoolNonCoreThreads(int mysqlWorkerPoolNonCoreThreads) {
        this.mysqlWorkerPoolNonCoreThreads = mysqlWorkerPoolNonCoreThreads;
    }

    public int getMysqlWorkerPoolAliveTime() {
        return mysqlWorkerPoolAliveTime;
    }

    public void setMysqlWorkerPoolAliveTime(int mysqlWorkerPoolAliveTime) {
        this.mysqlWorkerPoolAliveTime = mysqlWorkerPoolAliveTime;
    }
}
