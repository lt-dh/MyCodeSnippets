package com.lt.util;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.util.io.IoUtils;
import org.apache.sshd.sftp.client.SftpClient;
import org.apache.sshd.sftp.client.SftpClientFactory;

import java.io.*;
import java.nio.file.Path;
import java.util.regex.Pattern;

/**
 * @author liutong
 * @date 2024年11月25日 14:14
 */
public class SftpClientUtil implements Closeable {

    private final String host;
    private final int port;
    private final String username;
    private final String pass;
    private static final SshClient CLIENT;
    private ClientSession session;
    private SftpClient sftp;

    static {
        CLIENT = SshClient.setUpDefaultClient();
        CLIENT.start();
    }


    public ClientSession getSession() {
        return session;
    }

    public void setSession(ClientSession session) {
        this.session = session;
    }

    public SftpClient getSftp() {
        return sftp;
    }

    public void setSftp(SftpClient sftp) {
        this.sftp = sftp;
    }

    public SftpClientUtil(String host, int port, String username, String pass) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.pass = pass;
    }

    public SftpClient connect() throws Exception {
        session = CLIENT.connect(this.username, this.host, this.port).verify().getSession();
        session.addPasswordIdentity(this.pass);
        session.auth().verify();
        sftp = SftpClientFactory.instance().createSftpClient(session).singleSessionInstance();
        return sftp;
    }

    /**
     * 上传文件
     *
     * @param inputStream 输入流
     * @param remoteDir   远程目录
     * @param remoteFile  远程文件名
     */
    public void uploadFile(FileInputStream inputStream, String remoteDir, String remoteFile) throws Exception {
        try (OutputStream write = sftp.write(Path.of(remoteDir == null ? "" : remoteDir, remoteFile).toString())) {
            IoUtils.copy(inputStream, write);
        }
    }

    public void checkRemoteDir(String remoteDir) {
        StringBuilder filePath = new StringBuilder();

        for (String s : remoteDir.split(Pattern.quote(File.separator))) {
            try {
                filePath.append(File.separator).append(s);
                sftp.mkdir(filePath.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 下载文件
     *
     * @param remoteFile 远程文件名
     * @param localFile  本地文件名
     */
    public void downloadFile(String remoteFile, String localFile) throws Exception {
        try (OutputStream write = new FileOutputStream(localFile)) {
            sftp.read(remoteFile).transferTo(write);
        }
    }

    public void verifyStatus() {
        System.out.println(sftp.isOpen());
        System.out.println(session.isOpen());
    }

    @Override
    public void close() throws IOException {
        sftp.close();
        session.close();
    }
}
