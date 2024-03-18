package com.lt.string.anti_sharking;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 防抖
 * 在一定时间内限定提交次数
 * @author liutong
 * @date 2024年03月18日 17:09
 */
@Component
@RequiredArgsConstructor
public class AntiShakingMain {

    private final StringRedisTemplate stringRedisTemplate;

    @PostConstruct
    public void doIt(){
        // 模拟两次请求
        for (int i = 0; i < 2; i++) {
            sendRequest();
        }
    }
    public void sendRequest(){
        // 从请求参数中生成该请求唯一key
        String key = "first";
        int expireTime = 1 * 1000;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        // 请求过来时获取
        final Boolean success = stringRedisTemplate.execute(
                (RedisCallback<Boolean>) connection -> connection.commands().set(key.getBytes(), new byte[0],
                        Expiration.from(expireTime, timeUnit), // 1s
                        RedisStringCommands.SetOption.SET_IF_ABSENT));
        if (!success) {
            System.out.println("您的操作太快了,请稍后重试");
            return;
        }
        System.out.println("sendRequest发出请求");
    }
}
