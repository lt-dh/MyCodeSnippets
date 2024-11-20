package com.lt.config;

import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.session.ServerSession;

/**
 * @author liutong
 */
public class CustomAuthenticator implements PasswordAuthenticator {
    private final SftpServerConfig sftpServerConfig;
    public CustomAuthenticator(SftpServerConfig sftpServerConfig) {
        this.sftpServerConfig = sftpServerConfig;
    }

    @Override
    public boolean authenticate(String username, String password, ServerSession session) {
        // TODO: 2024/11/20 自定义策略
        return sftpServerConfig.getUsername().equals(username) && sftpServerConfig.getPassword().equals(password);
    }
}