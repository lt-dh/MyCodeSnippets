package com.lt.single;

import com.lt.config.RedisConfig;
import io.lettuce.core.RedisFuture;

import java.util.concurrent.ExecutionException;

/**
 * @author liutong
 * @date 2024年11月12日 16:30
 */
public class SingleTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RedisConfig.initConfig();
        PoolLettuceSingle.initRedis();
        PoolLettuceSingle.set("test", "test".getBytes());
        RedisFuture<byte[]> test = PoolLettuceSingle.get("test");
        System.out.println(new String(test.get()));
    }
}
