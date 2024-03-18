package com.lt.map.shopping;

/**
 * 购物车
 * @author liutong
 * @date 2024年03月18日 15:08
 */
public class ShoppingMain {
    /**
     * 通常缓存一个商品信息，减少查表次数
     * HSET key field value : 将哈希表 key 中的字段 field 的值设为 value ;
     * HMSET key field1 value1 [field2 value2 ] ：同时将多个 field-value (域-值)对设置到哈希表 key 中。
     * HGET key field：获取存储在哈希表中指定字段的值。
     * HGETALL key ：获取在哈希表中指定 key 的所有字段和值
     * HINCRBY key field increment ：为哈希表 key 中的指定字段的整数值加上增量 increment 。
     * HLEN key：获取哈希表中字段的数量
     */
}
