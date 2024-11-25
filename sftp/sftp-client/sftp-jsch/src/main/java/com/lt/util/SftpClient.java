package com.lt.util;

import com.jcraft.jsch.*;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author liutong
 * @date 2024年11月21日 11:16
 */
public class SftpClient {

    private String host;
    private int port;
    private String username;
    private String pass;
    private String privateKey;
    private ChannelSftp sftp;
    private Session sshSession;

    public ChannelSftp getSftp() {
        return sftp;
    }

    public SftpClient(String host, int port, String username, String pass) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.pass = pass;
    }

    public SftpClient(String username, String host, int port, String privateKey) {
        this.username = username;
        this.host = host;
        this.port = port;
        this.privateKey = privateKey;
    }

    /**
     * 建立连接
     *
     * @author liutong
     * @date 2022/12/9 16:47
     */
    public void openConnection() {
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            if (privateKey != null) {
                // 设置私钥
                jsch.addIdentity(privateKey);
            }
            sshSession = jsch.getSession(username, host, port);
            if (pass != null) {
                sshSession.setPassword(pass);
            }
//            Host: 指定配置应用的主机。可以使用通配符（* 和 ?）来匹配多个主机。
//            HostName: 实际连接的主机名或IP地址。
//            User: 连接时使用的用户名。
//            Port: 连接时使用的端口号，默认为22。
//            IdentityFile: 指定私钥文件的位置。
//            IdentitiesOnly: 是否仅使用 IdentityFile 中指定的密钥进行身份验证。
//            PreferredAuthentications: 指定优先使用的认证方法，例如 publickey, password。
//            PubkeyAuthentication: 是否启用公钥认证。
//            PasswordAuthentication: 是否启用密码认证。
//            ForwardAgent: 是否启用SSH代理转发。
//            ForwardX11: 是否启用X11转发。
//            Compression: 是否启用压缩。
//            Ciphers: 指定加密算法。
//            KexAlgorithms: 指定密钥交换算法。
//            MACs: 指定消息认证码算法。
//            ServerAliveInterval: 客户端发送心跳包的时间间隔，单位为秒。
//            ServerAliveCountMax: 在没有收到服务器响应的情况下，客户端发送心跳包的最大次数。
//            StrictHostKeyChecking: 是否严格检查主机密钥。
//            CheckHostIP: 是否检查主机的IP地址。
//            BatchMode: 是否禁用所有交互式输入。
//            ConnectTimeout: 连接超时时间，单位为秒
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * 关闭连接
     *
     * @author liutong
     * @date 2022/12/9 16:47
     */
    public void closeClient() {
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
            }
        }
        if (sshSession != null) {
            if (sshSession.isConnected()) {
                sshSession.disconnect();
            }
        }
    }

    /**
     * 判断目录是否存在
     *
     * @param dir 目录
     * @return boolean true:存在 false:不存在
     * @author liutong
     * @date 2022/12/9 16:47
     */
    public boolean checkDir(String dir) {
        try {
            sftp.cd(dir);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 确保目录存在
     *
     * @param dir 目录
     * @throws SftpException SftpException
     * @author liutong
     * @date 2022/12/9 16:47
     */
    public void ensureDirExists(String dir) throws SftpException {
        if (dir == null) {
            return;
        }
        if (!checkDir(dir)) {
            Path parent = Path.of(dir).getParent();
            ensureDirExists(parent == null ? null : parent.toString());
            // mkdir 中 remoteAbsolutePath(path) 方法太拉了
            String replace = dir.replace('\\', '/');
            sftp.mkdir(replace);
        }
    }

    /**
     * 执行命令,不可用于java写的sftp服务器
     *
     * @param cmd 命令
     * @return java.util.List<java.lang.String> 执行结果
     * @throws JSchException JSchException
     * @author liutong
     * @date 2022/12/9 16:47
     */
    public List<String> execCommand(String cmd) throws JSchException {
        ChannelExec channelExec = null;
        ArrayList<String> list = new ArrayList<>();
        StringBuilder errLog = new StringBuilder();
        channelExec = (ChannelExec) sshSession.openChannel("exec");
        channelExec.setCommand(cmd);
        channelExec.connect();  // 执行命令
        try (BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(channelExec.getInputStream()));
             BufferedReader errInputStreamReader = new BufferedReader(new InputStreamReader(channelExec.getErrStream()))) {
            String line;
            while ((line = inputStreamReader.readLine()) != null) {
                list.add(line);
            }
            String errLine;
            while ((errLine = errInputStreamReader.readLine()) != null) {
                errLog.append(errLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 上传文件
     *
     * @param localFile  本地文件
     * @param remoteFile 远程文件
     * @throws Exception Exception
     * @author liutong
     * @date 2022/12/9 16:47
     */
    public void uploadFile(String localFile, String remoteFile) throws Exception {
        try (FileInputStream fi = new FileInputStream(localFile)) {
            uploadFile(fi, remoteFile);
        }
    }

    /**
     * 上传文件
     *
     * @param inputStream 输入流
     * @param remoteFile  远程文件
     * @throws Exception Exception
     * @author liutong
     * @date 2022/12/9 16:47
     */
    public void uploadFile(InputStream inputStream, String remoteFile) throws Exception {
        sftp.put(inputStream, remoteFile);
    }

    /**
     * 下载文件
     *
     * @param remoteFile 远程文件
     * @param localFile  本地文件
     * @throws Exception Exception
     * @author liutong
     * @date 2022/12/9 16:47
     */
    public void downloadFile(String remoteFile, String localFile) throws Exception {
        try (FileOutputStream fo = new FileOutputStream(localFile)) {
            sftp.get(remoteFile, fo);
        }
    }
}
