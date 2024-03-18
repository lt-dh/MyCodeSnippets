package com.lt.list.queue;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 队列实现
 * @author liutong
 * @date 2024年03月18日 15:00
 */
@Component
@RequiredArgsConstructor
public class QueueMain {
    /**
     * RPUSH key value1 [value2]：在列表中添加一个或多个值；
     * BLPOP key1 [key2] timeout：移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止；
     * BRPOP key1 [key2] timeout：移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     */
}
