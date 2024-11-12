package com.lt.set.like;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 点赞实现
 *
 * @author liutong
 * @date 2024年03月18日 10:48
 */
@Component
@RequiredArgsConstructor
public class LikeMain {

    private final RedisTemplate<String, Integer> redisTemplate;

    private final String prefix = "like:article:";


    public Long like(Long articleId, Integer... userIds) {
        String key = prefix + articleId;
        return redisTemplate.opsForSet().add(key, userIds);
    }

    public Long likeNum(Long articleId) {
        String key = prefix + articleId;
        return redisTemplate.opsForSet().size(key);
    }

    public Boolean isLike(Long articleId, Integer userId) {
        String key = prefix + articleId;
        return redisTemplate.opsForSet().isMember(key, userId);
    }
}
