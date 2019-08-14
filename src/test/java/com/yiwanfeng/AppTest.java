package com.yiwanfeng;

import static org.junit.Assert.assertTrue;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void selectColumn() throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUsername("root");
            dataSource.setPassword("vrv123456.");
            dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/mysql");
            Connection connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from user limit 10;");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("user"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
