package com.lt.cluster;

import com.lt.cluster.PoolLettuceCluster;
import com.lt.config.RedisConfig;

/**
 * @author liutong
 * @date 2024年11月12日 16:30
 */
public class ClusterTest {
    public static void main(String[] args) {
        RedisConfig.initConfig();
        PoolLettuceCluster.initRedis();
        PoolLettuceCluster.get("test");
    }
}
