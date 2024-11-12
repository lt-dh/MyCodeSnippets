package com.lt.geo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 地图
 * @author liutong
 * @date 2024年03月18日 14:44
 */
@Component
@RequiredArgsConstructor
public class GEOMain {

    private final StringRedisTemplate redisTemplate;

    private final String KEY = "GEO_KEY";

    private Long add(String name, Point point) {
        return redisTemplate.opsForGeo().add(KEY, point, name);
    }


    private List<Point> get(String... names) {
        return redisTemplate.opsForGeo().position(KEY, names);
    }

    private Long del(String... names) {
        return redisTemplate.opsForGeo().remove(KEY, names);
    }

    /**
     * 根据坐标 获取指定范围的位置
     *
     */
    private GeoResults getNearByXY(Point point, Distance distance) {
        Circle circle = new Circle(point, distance);
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.
                newGeoRadiusArgs().
                includeDistance(). // 包含距离
                        includeCoordinates(). // 包含坐标
                        sortAscending(). // 排序 还可选sortDescending()
                        limit(5); // 获取前多少个
        return redisTemplate.opsForGeo().radius(KEY, circle, args);
    }

    /**
     * 根据一个位置，获取指定范围内的其他位置
     *
     */
    private GeoResults getNearByPlace(String name, Distance distance) {
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.
                newGeoRadiusArgs().
                includeDistance(). // 包含距离
                        includeCoordinates(). // 包含坐标
                        sortAscending(). // 排序 还可选sortDescending()
                        limit(5); // 获取前多少个
        return redisTemplate.opsForGeo()
                .radius(KEY, name, distance, args);
    }

    /**
     * 获取GEO HASH
     *
     */
    private List<String> getGeoHash(String... names) {
        return redisTemplate.opsForGeo().hash(KEY, names);
    }

//    @PostConstruct
//    public void doIt(){
//        add("a", new Point(116.62445, 39.86206));
//        add("b", new Point(117.3514785, 38.7501247));
//        add("c", new Point(116.538542, 39.75412));
//
//        List<Point> points = get("a", "b", "c");
//
//        GeoResults nearByXY = getNearByXY(new Point(116, 39), new Distance(120, Metrics.KILOMETERS));
//        List<GeoResult> content = nearByXY.getContent();
//        for (GeoResult geoResult : content) {
//            System.out.println(geoResult.getContent());
//        }
//
//        GeoResults nearByPlace = getNearByPlace("a", new Distance(120, Metrics.KILOMETERS));
//        content = nearByPlace.getContent();
//        for (GeoResult geoResult : content) {
//            System.out.println(geoResult.getContent());
//        }
//
//        getGeoHash("a", "b", "c");
//
//        del("b", "c");
//    }
}
