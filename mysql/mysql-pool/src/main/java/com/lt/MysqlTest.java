package com.lt;

import com.lt.config.MysqlConfig;
import com.lt.mysql.DbPoolFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author liutong
 * @date 2024年11月13日 16:17
 */
public class MysqlTest {
    public static void main(String[] args) throws SQLException {
        MysqlConfig.initConfig();
        DbPoolFactory.initMysql();
        try(Connection connection = DbPoolFactory.getConnection(true)){
            try(PreparedStatement preparedStatement = connection.prepareStatement("select  count(*) from a")){
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    System.out.println(resultSet.getInt(1));
                }
            }
        }
    }
}
