package com.lt.cluster;


import com.lt.config.RedisConfig;
import io.lettuce.core.*;
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

public class PoolLettuceCluster {

    private static PoolLettuceCluster poolLettuceCluster;
    private static final String REDIS_PREFIX = "pre_";


    private RedisAdvancedClusterAsyncCommands<String, byte[]> async;

    public static void initRedis() {
        try {
            poolLettuceCluster = new PoolLettuceCluster();
            RedisConfig c = RedisConfig.getInstance();
            String[] urls = c.getRedisUrl().split(",");
            List<RedisURI> redisUrlList = new ArrayList<>(urls.length);
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
                    if (c.getRedisHasPassword() == 1) {
                        return c.getRedisPassword().toCharArray();
                    }
                    return null;
                }

                @Override
                public boolean hasPassword() {
                    return c.getRedisHasPassword() == 1;
                }
            };

            for (String url : urls) {
                RedisURI redisUri = RedisURI.create("redis://" + url);
                redisUri.setCredentialsProvider(new StaticCredentialsProvider(redisCredentials));
                redisUrlList.add(redisUri);
            }
            ClientResources clientResources = DefaultClientResources
                    .builder()
                    .threadFactoryProvider(poolName -> new DefaultThreadFactory("redis"))
                    .ioThreadPoolSize(c.getRedisWorks())
                    .build();

            //开启 自适应集群拓扑刷新和周期拓扑刷新
            ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                    // 开启全部自适应刷新
                    .enableAllAdaptiveRefreshTriggers() // 开启自适应刷新,自适应刷新不开启,Redis集群变更时将会导致连接异常
                    // 自适应刷新超时时间(默认5秒) //默认关闭开启后时间为5秒
                    .adaptiveRefreshTriggersTimeout(Duration.ofSeconds(c.getRedisRefreshTimeout()))
                    .refreshTriggersReconnectAttempts(c.getRedisRefreshAttempts())
                    // 开周期刷新// 默认关闭开启后时间为30秒 ClusterTopologyRefreshOptions.DEFAULT_REFRESH_PERIOD 60  .enablePeriodicRefresh(Duration.ofSeconds(2)) = .enablePeriodicRefresh().refreshPeriod(Duration.ofSeconds(2))
                    .enablePeriodicRefresh(Duration.ofSeconds(c.getRedisRefreshPeriod()))
                    .build();

            ClusterClientOptions options = ClusterClientOptions.builder()
                    .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
                    .topologyRefreshOptions(clusterTopologyRefreshOptions)
                    .autoReconnect(true)
                    .maxRedirects(c.getRedisMaxRedirects())
                    .build();
            RedisClusterClient redisClient = RedisClusterClient.create(clientResources, redisUrlList);
            redisClient.setOptions(options);
            StatefulRedisClusterConnection<String, byte[]> connection = redisClient.connect(RedisCodec.of(new StringCodec(), new ByteArrayCodec()));
            RedisAdvancedClusterAsyncCommands<String, byte[]> async = connection.async();
            poolLettuceCluster.setAsync(async);
        } catch (Exception e) {
            System.exit(-1);
        }
    }

    public static RedisFuture<String> set(String key, byte[] value) {
        return poolLettuceCluster.async.set(REDIS_PREFIX + key, value);
    }

    public static RedisFuture<byte[]> get(String s) {
        return poolLettuceCluster.async.get(REDIS_PREFIX + s);
    }

    public static RedisFuture<Long> del(String s) {
        return poolLettuceCluster.async.del(REDIS_PREFIX + s);
    }

    public static void deleteKeysByPattern(String patternSuffix) {

        // 使用scan命令获取匹配的键，然后异步删除
        poolLettuceCluster.async.scan(new ScanArgs().match(patternSuffix))
                .whenComplete((cursor, throwable) -> {
                    if (throwable != null) {
                        return;
                    }
                    // 遍历匹配到的键并异步删除
                    for (String keyStr : cursor.getKeys()) {
                        poolLettuceCluster.async.del(keyStr);
                    }
                });
    }

    public void setAsync(RedisAdvancedClusterAsyncCommands<String, byte[]> async) {
        this.async = async;
    }
}
