package com.lt;

import com.lt.util.SftpClient;

import java.util.List;

/**
 * @author liutong
 * @date 2024年11月21日 11:15
 */
public class SftpJschTest {
    public static void main(String[] args) throws Exception {
        SftpClient sftpClient = null;
        try {
            String remoteDir = "/lt/ltt/lttt/";
            sftpClient = new SftpClient("127.0.0.1", 5022, "lt", "lt");
            sftpClient.openConnection();
            sftpClient.ensureDirExists("/lt/ltt/lttt");
            sftpClient.uploadFile("D:\\sftp\\test\\lt\\a.txt", remoteDir + "a.txt");
            // 下边这个命令不可用于java写的sftp服务器
//            List<String> strings = sftpClient.execCommand("ls /lt/ltt/lttt/");
            sftpClient.downloadFile(remoteDir + "a.txt", "D:\\sftp\\test\\lt\\ltt\\a.txt");
        } finally {
            assert sftpClient != null;
            sftpClient.closeClient();
        }
    }
}
