package com.lt.HeyperLogLog;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * UV（独立访客）统计
 * @author liutong
 * @date 2024年03月18日 11:13
 */
@Component
@RequiredArgsConstructor
public class UVMain {

    private static final String prefix = "prefix:";
    private final RedisTemplate redisTemplate;

    private Long uv(Integer pageId, Integer userId) {
        String key = prefix + pageId;
        return redisTemplate.opsForHyperLogLog().add(key, userId);
    }

    /**
     * 统计页面的UV
     * @param pageId
     * @return
     */
    private Long getUv(Integer pageId) {
        String key = prefix + pageId;
        return redisTemplate.opsForHyperLogLog().size(key);
    }
}
