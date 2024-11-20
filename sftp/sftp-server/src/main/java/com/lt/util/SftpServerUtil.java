package com.lt.util;

import com.lt.config.CustomAuthenticator;
import com.lt.config.CustomSftpFileSystemAccessor;
import com.lt.config.SftpServerConfig;
import org.apache.sshd.common.config.keys.KeyUtils;
import org.apache.sshd.common.file.virtualfs.VirtualFileSystemFactory;
import org.apache.sshd.common.session.Session;
import org.apache.sshd.common.session.SessionListener;
import org.apache.sshd.common.util.security.SecurityUtils;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.channel.ChannelSessionFactory;
import org.apache.sshd.server.keyprovider.AbstractGeneratorHostKeyProvider;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.sftp.server.FileHandle;
import org.apache.sshd.sftp.server.SftpEventListener;
import org.apache.sshd.sftp.server.SftpSubsystemFactory;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

/**
 * @author liutong
 * @date 2024年04月22日 11:19
 */
public class SftpServerUtil {

    private SftpServerUtil(){}

    public static void initSftpServer(SftpServerConfig sftpServerConfig) {
        SshServer sshServer = SshServer.setUpDefaultServer();
        sshServer.setHost(sftpServerConfig.getHost());
        sshServer.setPort(sftpServerConfig.getPort());

        sshServer.setSubsystemFactories(Collections.singletonList(getSftpSubsys()));
        sshServer.setChannelFactories(Collections.singletonList(ChannelSessionFactory.INSTANCE));
        // 设置用户根目录
        Path rootPath = getRootPath(sftpServerConfig);
        VirtualFileSystemFactory virtualFileSystemFactory = new VirtualFileSystemFactory(rootPath);
        virtualFileSystemFactory.setUserHomeDir(sftpServerConfig.getHost(), rootPath);

        sshServer.setFileSystemFactory(virtualFileSystemFactory);
        sshServer.setPasswordAuthenticator(new CustomAuthenticator(sftpServerConfig));
        sshServer.addSessionListener(getSessionListener());
        sshServer.setKeyPairProvider(getKeyPair());
        try {
            sshServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static AbstractGeneratorHostKeyProvider getKeyPair() {
        final AbstractGeneratorHostKeyProvider provider;
        if (SecurityUtils.isBouncyCastleRegistered()) {
            Path pemPath = Paths.get("hostkey.pem");
            if (Files.exists(pemPath)) {
                provider = new SimpleGeneratorHostKeyProvider(pemPath);
            } else {
                provider = SecurityUtils.createGeneratorHostKeyProvider(pemPath);
            }
        } else {
            provider = new SimpleGeneratorHostKeyProvider(Paths.get("hostkey.ser"));
        }
        provider.setAlgorithm(KeyUtils.RSA_ALGORITHM);
        return provider;
    }

    private static SessionListener getSessionListener() {
        return new SessionListener() {
            @Override
            public void sessionCreated(Session session) {
                // TODO: 2024/11/20 日志
            }

            @Override
            public void sessionClosed(Session session) {
                // TODO: 2024/11/20 日志
            }
        };
    }


    private static Path getRootPath(SftpServerConfig sftpServerConfig) {
        return Paths.get(sftpServerConfig.getRootDir());
    }

    private static SftpSubsystemFactory getSftpSubsys() {
        final SftpSubsystemFactory sftpSubsys = new SftpSubsystemFactory.Builder()
                .withFileSystemAccessor(new CustomSftpFileSystemAccessor()).build();
        sftpSubsys.addSftpEventListener(new SftpEventListener() {
            @Override
            public void moved(ServerSession session, Path srcPath, Path dstPath, Collection<CopyOption> opts, Throwable thrown) {
            }

            @Override
            public void written(ServerSession session, String remoteHandle, FileHandle localHandle, long offset, byte[] data, int dataOffset, int dataLen, Throwable thrown) {
            }
        });
        return sftpSubsys;
    }
}
