package cn.hjf.lzsdq.article.dao.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ResourceCloser {

    public static void closeSilent(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException sqlEx) {
            }
        }
    }

    public static void closeSilent(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException sqlEx) {
            }
        }
    }

    public static void closeSilent(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException sqlEx) {
            }
        }
    }
}
