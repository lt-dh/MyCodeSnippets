package com.lt.set.lottery;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * redis 抽奖实现
 * @author liutong
 * @date 2024年03月18日 10:05
 */
@Component
@RequiredArgsConstructor
public class RaffleMain {

    private final RedisTemplate<String,Integer> redisTemplate;

    public List<Integer> choujiang(){
        // 用户添加 可以抽出一个add方法
        String key = "choujiang";
        Integer[] userIds = new Integer[]{1000, 1001, 2233, 7890, 44556, 74512};
        redisTemplate.opsForSet().add(key, userIds);
        // 准备抽取多少人
        int num = 2;
        // 随机抽取
        List<Integer> list;
        // 抽完之后将用户移除奖池
//        list = redisTemplate.opsForSet().pop(key, num);
        // 抽完之后用户保留在池子里
        list = redisTemplate.opsForSet().randomMembers(key, num);
        return list;
    }

//    @PostConstruct
//    public void doIt(){
//        System.out.println("抽奖实现");
//        // 抽奖实现
//        List<Integer> choujiang = choujiang();
//        for (int i = 0; i < choujiang.size(); i++) {
//            System.out.println(choujiang.get(i));
//        }
//    }
}
