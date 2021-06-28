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

    /**
     * ********************************************************************************************************************
     * <p>
     * ********************************************************************************************************************
     */

    private ComboPooledDataSource mDataSource;

    /**
     * ********************************************************************************************************************
     * <p>
     * ********************************************************************************************************************
     */

    private static class InstanceHolder {
        private static ConnectionManager sInstance = new ConnectionManager();
    }

    public static ConnectionManager getInstance() {
        return InstanceHolder.sInstance;
    }

    private ConnectionManager() {
        init();
    }

    /**
     * ********************************************************************************************************************
     * <p>
     * ********************************************************************************************************************
     */

    private void init() {
        mDataSource = new ComboPooledDataSource();
        try {
            mDataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        mDataSource.setJdbcUrl("jdbc:mysql://" + System.getenv("MYSQL_HOST") + ":" + System.getenv("MYSQL_PORT") + "/" + System.getenv("MYSQL_SCHEMA"));
        mDataSource.setUser(System.getenv("MYSQL_USER"));
        mDataSource.setPassword(System.getenv("MYSQL_PWD"));
        mDataSource.setMaxPoolSize(50);
    }

    public Connection getConnection() {
        try {
            return mDataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
