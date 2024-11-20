package com.lt.config;

/**
 * @author liutong
 * @date 2024年04月22日 14:14
 */
public class SftpServerConfig {
    private String host = "127.0.0.1";
    private Integer port = 5022;
    private String rootDir = "D:\\sftp\\test";
    private String username = "lt";
    private String password = "lt";

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getRootDir() {
        return rootDir;
    }

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
