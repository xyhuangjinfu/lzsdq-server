package cn.hjf.lzsdq.article.dao.repository;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {

//    public static Connection getConnection() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        Connection conn = null;
//        try {
//            conn =
//                    DriverManager.getConnection("jdbc:mysql://" + System.getenv("MYSQL_HOST") + ":" + System.getenv("MYSQL_PORT") + "/" + System.getenv("MYSQL_SCHEMA") + "?" +
//                            "user=" + System.getenv("MYSQL_USER") + "&password=" + System.getenv("MYSQL_PWD"));
//            return conn;
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//
//        return null;
//    }

    public static Connection getConnection() {
        ComboPooledDataSource source = new ComboPooledDataSource();
        try {
            source.setDriverClass("com.mysql.cj.jdbc.Driver");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        source.setJdbcUrl("jdbc:mysql://" + System.getenv("MYSQL_HOST") + ":" + System.getenv("MYSQL_PORT") + "/" + System.getenv("MYSQL_SCHEMA"));
        source.setUser(System.getenv("MYSQL_USER"));
        source.setPassword(System.getenv("MYSQL_PWD"));
        try {
            return source.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
