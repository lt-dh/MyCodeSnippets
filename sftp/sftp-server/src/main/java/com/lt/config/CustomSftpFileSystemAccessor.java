package com.lt.config;

import org.apache.sshd.sftp.server.SftpFileSystemAccessor;
import org.apache.sshd.sftp.server.SftpSubsystemProxy;

import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.AclEntry;
import java.util.List;

/**
 * @author liutong
 */
public class CustomSftpFileSystemAccessor implements SftpFileSystemAccessor {

    @Override
    public void setFileAccessControl(final SftpSubsystemProxy subsystem,
                                     final Path file, final List<AclEntry> acl, final LinkOption... options) {
        throw new UnsupportedOperationException("ACL set not supported");
    }

    @Override
    public void createLink(final SftpSubsystemProxy subsystem, final Path link,
                           final Path existing, final boolean symLink) {
        throw new UnsupportedOperationException("Link not supported");
    }

    @Override
    public String toString() {
        return SftpFileSystemAccessor.class.getSimpleName() + "[CUSTOM]";
    }
}