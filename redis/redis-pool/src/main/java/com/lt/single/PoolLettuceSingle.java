package com.lt.single;


import com.lt.config.RedisConfig;
import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PoolLettuceSingle {

    private static PoolLettuceSingle poolLettuceSingle;
    private static final String REDIS_PREFIX = "pre_";


    private RedisAsyncCommands<String, byte[]> async;

    public static void initRedis() {
        try {
            poolLettuceSingle = new PoolLettuceSingle();
            RedisConfig redisConfig = RedisConfig.getInstance();
            String redisUrl = redisConfig.getRedisUrl();
            RedisCredentials redisCredentials = new RedisCredentials() {
                @Override
                public String getUsername() {
                    return null;
                }

                @Override
                public boolean hasUsername() {
                    return false;
                }

                @Override
                public char[] getPassword() {
                    if (redisConfig.getRedisHasPassword() == 1) {
                        return redisConfig.getRedisPassword().toCharArray();
                    }
                    return null;
                }

                @Override
                public boolean hasPassword() {
                    return redisConfig.getRedisHasPassword() == 1;
                }
            };
            RedisURI redisUri = RedisURI.create("redis://" + redisUrl);
            redisUri.setCredentialsProvider(new StaticCredentialsProvider(redisCredentials));
            ClientResources clientResources = DefaultClientResources
                    .builder()
                    .threadFactoryProvider(poolName -> new DefaultThreadFactory("redis"))
                    .ioThreadPoolSize(redisConfig.getRedisWorks())
                    .build();

            RedisClient redisClient = RedisClient.create(clientResources,redisUri);
            StatefulRedisConnection<String, byte[]> connection = redisClient.connect(RedisCodec.of(new StringCodec(), new ByteArrayCodec()));
            RedisAsyncCommands<String, byte[]> async = connection.async();
            poolLettuceSingle.setAsync(async);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static RedisFuture<String> set(String key, byte[] value) {
        return poolLettuceSingle.async.set(REDIS_PREFIX + key, value);
    }

    public static RedisFuture<byte[]> get(String s) {
        return poolLettuceSingle.async.get(REDIS_PREFIX + s);
    }

    public static RedisFuture<Long> del(String s) {
        return poolLettuceSingle.async.del(REDIS_PREFIX + s);
    }

    public static void deleteKeysByPattern(String patternSuffix) {

        // 使用scan命令获取匹配的键，然后异步删除
        poolLettuceSingle.async.scan(new ScanArgs().match(patternSuffix))
                .whenComplete((cursor, throwable) -> {
                    if (throwable != null) {
                        return;
                    }
                    // 遍历匹配到的键并异步删除
                    for (String keyStr : cursor.getKeys()) {
                        poolLettuceSingle.async.del(keyStr);
                    }
                });
    }

    public void setAsync(RedisAsyncCommands<String, byte[]> async) {
        this.async = async;
    }
}
