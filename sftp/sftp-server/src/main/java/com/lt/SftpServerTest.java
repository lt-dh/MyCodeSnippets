package com.lt;

import com.lt.config.SftpServerConfig;
import com.lt.util.SftpServerUtil;

import java.util.concurrent.locks.LockSupport;

/**
 * @author liutong
 * @date 2024年11月20日 17:21
 */
public class SftpServerTest {
    public static void main(String[] args) {
        SftpServerUtil.initSftpServer(new SftpServerConfig());
        LockSupport.park();
        // sftp -P 5022 lt@127.0.0.1 测试连接
    }
}
