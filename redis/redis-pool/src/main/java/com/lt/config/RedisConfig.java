package com.lt.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author liutong
 * @date 16点19分
 */
public class RedisConfig {

    /**
     * redis配置
     */
    private String redisUrl;
    private int redisHasPassword;
    private String redisPassword;
    private int redisWorks;
    private int redisMaxRedirects;
    private int redisRefreshAttempts;
    private int redisRefreshPeriod;
    private int redisRefreshTimeout;


    private RedisConfig() {
    }

    private static RedisConfig redisConfig;

    public static RedisConfig getInstance() {
        return redisConfig;
    }


    public static void initConfig() {
        try (InputStream resourceInputStream = RedisConfig.class.getClassLoader().getResourceAsStream("server.properties")) {
            Properties properties = new Properties();
            properties.load(resourceInputStream);
            redisConfig = new RedisConfig();
            redisConfig.setRedisUrl(properties.getProperty("redisUrl"));
            redisConfig.setRedisPassword(properties.getProperty("redisPassword"));
            redisConfig.setRedisHasPassword(Integer.parseInt(properties.getProperty("redisHasPassword")));
            redisConfig.setRedisWorks(Integer.parseInt(properties.getProperty("redisWorks")));
            redisConfig.setRedisMaxRedirects(Integer.parseInt(properties.getProperty("redisMaxRedirects")));
            redisConfig.setRedisRefreshAttempts(Integer.parseInt(properties.getProperty("redisRefreshAttempts")));
            redisConfig.setRedisRefreshPeriod(Integer.parseInt(properties.getProperty("redisRefreshPeriod")));
            redisConfig.setRedisRefreshTimeout(Integer.parseInt(properties.getProperty("redisRefreshTimeout")));
        } catch (IOException e) {
            System.exit(-1);
        }
    }

    public String getRedisUrl() {
        return redisUrl;
    }

    public void setRedisUrl(String redisUrl) {
        this.redisUrl = redisUrl;
    }

    public int getRedisHasPassword() {
        return redisHasPassword;
    }

    public void setRedisHasPassword(int redisHasPassword) {
        this.redisHasPassword = redisHasPassword;
    }

    public String getRedisPassword() {
        return redisPassword;
    }

    public void setRedisPassword(String redisPassword) {
        this.redisPassword = redisPassword;
    }

    public int getRedisWorks() {
        return redisWorks;
    }

    public void setRedisWorks(int redisWorks) {
        this.redisWorks = redisWorks;
    }

    public int getRedisMaxRedirects() {
        return redisMaxRedirects;
    }

    public void setRedisMaxRedirects(int redisMaxRedirects) {
        this.redisMaxRedirects = redisMaxRedirects;
    }

    public int getRedisRefreshAttempts() {
        return redisRefreshAttempts;
    }

    public void setRedisRefreshAttempts(int redisRefreshAttempts) {
        this.redisRefreshAttempts = redisRefreshAttempts;
    }

    public int getRedisRefreshPeriod() {
        return redisRefreshPeriod;
    }

    public void setRedisRefreshPeriod(int redisRefreshPeriod) {
        this.redisRefreshPeriod = redisRefreshPeriod;
    }

    public int getRedisRefreshTimeout() {
        return redisRefreshTimeout;
    }

    public void setRedisRefreshTimeout(int redisRefreshTimeout) {
        this.redisRefreshTimeout = redisRefreshTimeout;
    }

    public static RedisConfig getRedisConfig() {
        return redisConfig;
    }

    public static void setRedisConfig(RedisConfig redisConfig) {
        RedisConfig.redisConfig = redisConfig;
    }
}
