package com.lt.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author liutong
 * @date 2024年11月20日 15:36
 */
public class Person implements ConsumerInterface {
    private String name;
    private Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public void consumer(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, name);
        pstmt.setInt(2, age);
        pstmt.addBatch();
    }

    public static String SQL = "insert into person" +
            "(name,age) " +
            "values(?,?) ON DUPLICATE KEY UPDATE " +
            "name = values(name)," +
            "age = values(age)";

    public static int CONSUMER_BATCH_SIZE = 2000;
}
