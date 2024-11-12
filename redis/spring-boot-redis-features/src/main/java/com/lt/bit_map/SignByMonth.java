package com.lt.bit_map;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.connection.RedisCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 签到
 * @author liutong
 * @date 2024/3/18 14:58
 */
@Component
@RequiredArgsConstructor
public class SignByMonth {

    private final StringRedisTemplate stringRedisTemplate;

    private int dayOfMonth() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getDayOfMonth();
    }

    /**
     * 按照月份和用户ID生成用户签到标识 UserId:Sign:560:2021-08
     *
     * @param userId 用户id
     */
    private String signKeyWitMouth(String userId) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        return "UserId:Sign:" + userId + ":" +
                currentDate.format(formatter);
    }

    /**
     * 设置标记位
     * 标记是否签到
     */
    public Boolean sign(String key, long offset, boolean tag) {
        return this.stringRedisTemplate.opsForValue().setBit(key, offset, tag);
    }


    /**
     * 统计计数
     *
     * @param key 用户标识
     */
    public Long bitCount(String key) {
        return stringRedisTemplate.execute((RedisCallback<Long>) redisConnection -> {
            RedisCommands commands = redisConnection.commands();
            return commands.bitCount(key.getBytes());
        });
    }

    /**
     * 获取多字节位域
     */
    public List<Long> bitfield(String buildSignKey, int limit, long offset) {
        return this.stringRedisTemplate
                .opsForValue()
                .bitField(buildSignKey, BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.unsigned(limit)).valueAt(offset));
    }


    /**
     * 判断是否被标记
     *
     */
    public Boolean container(String key, long offset) {
        return this.stringRedisTemplate.opsForValue().getBit(key, offset);
    }


    /**
     * 用户今天是否签到
     *
     */
    public int checkSign(String userId) {
        String signKey = this.signKeyWitMouth(userId);
        int offset = dayOfMonth() - 1;
        return Boolean.TRUE.equals(this.container(signKey, offset)) ? 1 : 0;
    }


    /**
     * 查询用户当月签到日历
     *
     */
    public Map<String, Boolean> querySignedInMonth(String userId) {
        LocalDate currentDate = LocalDate.now();

        int lengthOfMonth = currentDate.getMonth().maxLength();
        Map<String, Boolean> signedInMap = new HashMap<>(dayOfMonth());

        String signKey = this.signKeyWitMouth(userId);
        List<Long> bitfield = this.bitfield(signKey, lengthOfMonth, 0);
        if (!CollectionUtils.isEmpty(bitfield)) {
            long signFlag = bitfield.get(0) == null ? 0 : bitfield.get(0);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (int i = lengthOfMonth; i > 0; i--) {
                LocalDate dateTime1 = currentDate.withDayOfMonth(i);
                signedInMap.put(dateTime1.format(formatter), signFlag >> 1 << 1 != signFlag);
                signFlag >>= 1;
            }
        }
        return signedInMap;
    }


    /**
     * 用户签到
     *
     */
    public boolean signWithUserId(String userId) {
        int dayOfMonth = this.dayOfMonth();
        String signKey = this.signKeyWitMouth(userId);
        long offset = (long) dayOfMonth - 1;
        boolean re = Boolean.TRUE.equals(this.sign(signKey, offset, Boolean.TRUE));

        // 查询用户连续签到次数,最大连续次数为7天
        long continuousSignCount = this.queryContinuousSignCount(userId, 7);
        if (continuousSignCount==7){
//            dosomething()
        }
        return re;
    }

    /**
     * 统计当前月份一共签到天数
     *
     */
    public long countSignedInDayOfMonth(String userId) {
        String signKey = this.signKeyWitMouth(userId);
        return this.bitCount(signKey);
    }


    /**
     * 查询用户当月连续签到次数
     *
     */
    public long queryContinuousSignCountOfMonth(String userId) {
        int signCount = 0;
        String signKey = this.signKeyWitMouth(userId);
        int dayOfMonth = this.dayOfMonth();
        List<Long> bitfield = this.bitfield(signKey, dayOfMonth, 0);

        if (!CollectionUtils.isEmpty(bitfield)) {
            long signFlag = bitfield.get(0) == null ? 0 : bitfield.get(0);
            for (int i = 0; i < dayOfMonth; i++) {
                if (signFlag >> 1 << 1 == signFlag) {
                    if (i > 0) {
                        break;
                    }
                } else {
                    signCount += 1;
                }
                signFlag >>= 1;
            }
        }
        return signCount;
    }


    /**
     * 以7天一个周期连续签到次数
     *
     * @param period 周期
     */
    public long queryContinuousSignCount(String userId, Integer period) {
        //查询今日之前(包括或不包括)连续签到次数
        long count = this.queryContinuousSignCountOfMonth(userId);
        //按最大连续签到取余
        if (period != null && period < count) {
            long num = count % period;
            if (num == 0) {
                count = period;
            } else {
                count = num;
            }
        }
        return count;
    }

//    @PostConstruct
//    public void doIt(){
//        System.out.println("签到实现");
//        // 模拟签到 今天18号 offset从0开始
//        stringRedisTemplate.opsForValue().setBit("UserId:Sign:250:2024-03", 17, true);
//        stringRedisTemplate.opsForValue().setBit("UserId:Sign:250:2024-03", 16, true);
//        stringRedisTemplate.opsForValue().setBit("UserId:Sign:250:2024-03", 15, true);
//        stringRedisTemplate.opsForValue().setBit("UserId:Sign:250:2024-03", 10, true);
//        stringRedisTemplate.opsForValue().setBit("UserId:Sign:250:2024-03", 9, true);
//        System.out.println("250用户今日是否已签到:" + checkSign("250"));
//
//        Map<String, Boolean> stringBooleanMap = querySignedInMonth("250");
//        System.out.println("本月签到情况:");
//        for (Map.Entry<String, Boolean> entry : stringBooleanMap.entrySet()) {
//            System.out.println(entry.getKey() + ": " + (entry.getValue() ? "√" : "-"));
//        }
//
//        System.out.println("本月一共签到:" + countSignedInDayOfMonth("250") + "天");
//        System.out.println("目前连续签到:" + queryContinuousSignCount("250", 7) + "天");
//    }
}