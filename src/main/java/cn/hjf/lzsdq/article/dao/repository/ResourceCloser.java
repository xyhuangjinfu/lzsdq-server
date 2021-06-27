package cn.hjf.lzsdq.article.dao.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ResourceCloser {

    public static void closeSilent(Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException sqlEx) {
            } // ignore
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException sqlEx) {
            } // ignore
        }
    }
}
