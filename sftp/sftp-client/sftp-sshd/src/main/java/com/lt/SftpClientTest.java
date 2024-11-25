package com.lt;

import com.lt.util.SftpClientUtil;
import org.apache.sshd.sftp.client.SftpClient;

import java.io.FileInputStream;

/**
 * @author liutong
 * @date 2024年11月25日 14:10
 */
public class SftpClientTest {
    public static void main(String[] args) throws Exception {
        SftpClientUtil sftpClientUtil = new SftpClientUtil("127.0.0.1", 5022, "lt", "lt");
        String remoteDir = "/lt/llt";
        try (SftpClient ignored = sftpClientUtil.connect()) {
            sftpClientUtil.checkRemoteDir(remoteDir);
            sftpClientUtil.uploadFile(new FileInputStream("D:\\sftp\\test\\lt\\a.txt"), remoteDir, "a.txt");
            sftpClientUtil.downloadFile(remoteDir+"/a.txt", "D:\\sftp\\test\\lt\\b.txt");
        }
        sftpClientUtil.verifyStatus();
    }
}
