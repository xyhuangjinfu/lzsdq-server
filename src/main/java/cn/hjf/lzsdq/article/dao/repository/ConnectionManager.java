package cn.hjf.lzsdq.article.dao.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        try {
            conn =
                    DriverManager.getConnection("jdbc:mysql://" + System.getenv("MYSQL_HOST") + ":" + System.getenv("MYSQL_PORT") + "/" + System.getenv("MYSQL_SCHEMA") + "?" +
                            "user=" + System.getenv("MYSQL_USER") + "&password=" + System.getenv("MYSQL_PWD"));
            return conn;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
