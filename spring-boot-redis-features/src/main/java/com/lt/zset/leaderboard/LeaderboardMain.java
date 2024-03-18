package com.lt.zset.leaderboard;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 排行榜功能
 * @author liutong
 * @date 2024年03月18日 11:02
 */
@Component
@RequiredArgsConstructor
public class LeaderboardMain {

    private final RedisTemplate redisTemplate;

    private static final String KEY_RANKING = "ranking:";

    public Boolean add(Integer userId, Double score) {
        Boolean add = redisTemplate.opsForZSet().add(KEY_RANKING, userId, score);
        return add;
    }

    /**
     * @author liutong
     * @date 2024/3/18 11:05
     * @param min 从0开始
     * @param max -1代表所有
     * @return java.util.Set<org.springframework.data.redis.core.DefaultTypedTuple>
     */
    public Set<DefaultTypedTuple> range(long min, long max) {
        // 降序
        Set<DefaultTypedTuple> set = redisTemplate.opsForZSet().reverseRangeWithScores(KEY_RANKING, min, max);
        // 升序
        //Set<DefaultTypedTuple> set = redisTemplate.opsForZSet().rangeWithScores(KEY_RANKING, min, max);
        return set;
    }

}
