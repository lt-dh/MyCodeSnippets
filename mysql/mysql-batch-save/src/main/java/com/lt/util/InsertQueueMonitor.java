package com.lt.util;

import com.lt.dao.ConsumerInterface;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author liutong
 * @date 2024年06月20日 10:23
 */
public class InsertQueueMonitor<T extends ConsumerInterface> {
    private final BlockingQueue<T> queue;
    private final ScheduledExecutorService executor;
    private final String sql;
    private final DataSource hikariDataSource;
    private final int batchSize;


    public InsertQueueMonitor(BlockingQueue<T> queue, ScheduledExecutorService executor,
                              DataSource hikariDataSource, String sql, int batchSize) {
        this.queue = queue;
        this.executor = executor;
        this.sql = sql;
        this.hikariDataSource = hikariDataSource;
        this.batchSize = batchSize;
    }

    public void startMonitoring(long period) {
        executor.scheduleAtFixedRate(this::processQueue, 0, period, TimeUnit.MILLISECONDS);
    }

    private void processQueue() {
        try {
            int size = queue.size();
            if (size > 0) {
                int loop = Math.min(size, batchSize);
                try (Connection connection = this.hikariDataSource.getConnection()) {
                    try (PreparedStatement pst = connection.prepareStatement(sql)) {
                        for (int i = 0; i < loop; i++) {
                            T consumerInterface = queue.take();
                            consumerInterface.consumer(pst);
                        }
                        pst.executeBatch();
                    }
                }
            } else {
                // 阻塞
                T take = queue.take();
                queue.put(take);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void putElement(T t) throws InterruptedException {
        this.queue.put(t);
    }
}
