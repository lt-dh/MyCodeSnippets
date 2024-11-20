package com.lt.dao;

import java.sql.PreparedStatement;

/**
 * @author liutong
 */
public interface ConsumerInterface {

    /**
     * 消费者接口
     *
     * @param pstmt 预编译sql
     */
    void consumer(PreparedStatement pstmt) throws Exception;

}
