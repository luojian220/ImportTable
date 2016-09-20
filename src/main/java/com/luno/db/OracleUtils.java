package com.luno.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Oracle数据库操作工具
 * Created by ksafe on 2014/5/22.
 */
public class OracleUtils {
    private int port;
    private String dbHost;
    private String dbName;
    private String dbUser;
    private String dbPass;
    protected Connection connection;

    public OracleUtils(int port, String dbHost, String dbName, String dbUser, String dbPass) {
        if(port == 0){
            port = 1521;
        }
        this.port = port;
        this.dbHost = dbHost;
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPass = dbPass;
    }

    public Connection getConnect() throws RuntimeException {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            String db_Url = "jdbc:oracle:thin:@" + dbHost + ":" + port + ":" + dbName;
            connection = DriverManager.getConnection(db_Url, dbUser, dbPass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("找不到Oracle驱动", e);
        }catch (SQLException e) {
            throw new RuntimeException("Oracle数据库连接失败", e);
        }
        return connection;
    }
}
