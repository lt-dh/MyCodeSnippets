package com.lt;

import com.lt.config.MysqlConfig;
import com.lt.dao.Person;
import com.lt.mysql.DbPoolFactory;
import com.lt.util.InsertQueueMonitor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author liutong
 * @date 2024年11月20日 15:25
 */
public class MysqlBatchSaveTest {
    public static void main(String[] args) throws InterruptedException {
        MysqlConfig.initConfig();
        DbPoolFactory.initMysql();
        BlockingQueue<Person> blockingQueue = new LinkedBlockingQueue<>(10_0000);
        InsertQueueMonitor<Person> insertQueueMonitor = new InsertQueueMonitor<>(blockingQueue, Executors.newSingleThreadScheduledExecutor(),
                DbPoolFactory.getDataSource(), Person.SQL, Person.CONSUMER_BATCH_SIZE);
        insertQueueMonitor.startMonitoring(100);
        for (int i = 0; i < 10_0000; i++) {
            insertQueueMonitor.putElement(new Person(i+"",i));
        }
    }
}
