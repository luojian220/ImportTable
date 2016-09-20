package com.luno.db;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Created by Administrator on 2016-9-10.
 */
public class DbUtils {

    protected Connection connection;
    protected Statement statement;
    private final static Logger logger = LogManager.getLogger(DbUtils.class);

    public DbUtils(int port, String dbHost, String dbName, String dbUser, String dbPass){

        try {
            OracleUtils oracleUtils = new OracleUtils(port,dbHost,dbName,dbUser,dbPass);
            connection = oracleUtils.getConnect();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("设置不自动提交失败",e);
        }
    }


    public Connection getConnection(){
        return connection ;
    }

    public Statement getStatement(){
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    public void close() {
        try{
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }catch(SQLException e){
            logger.error("数据库关闭失败",e);
        }
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    public boolean execute(String sql) throws SQLException {
        return getStatement().execute(sql);
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        return getStatement().executeQuery(sql);
    }

    public Statement getStatement2() throws SQLException {
        if (statement == null) {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        }
        return statement;
    }

    public boolean isClosed() {
        try {
            return connection == null  || connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
