/*
 * Developed by Razil Minneakhmetov on 11/18/18 11:59 AM.
 * Last modified 11/18/18 11:59 AM.
 * Copyright © 2018. All rights reserved.
 */
package services;

import lombok.SneakyThrows;
import org.postgresql.util.PSQLException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnector {

    private static DBConnector instance;

    private DriverManagerDataSource dataSource;

    private boolean connectionStatus = false;

    public void setDataSource(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }


    private static String protocol = "jdbc:postgresql://";
    private static String ip; // = "localhost";
    private static String port; // = "5432";
    private static String databaseName; // = "stickershop";
    private static String username; // = "postgres";
    private static String password; // = "r1a2z3i4l5";

    @SneakyThrows
    private DBConnector() {
        Class.forName("org.postgresql.Driver");
        dataSource = new DriverManagerDataSource();
    }

    public static DBConnector getInstance() {
        if (instance == null) {
            instance = new DBConnector();
        }
        return instance;
    }

    public DriverManagerDataSource getDataSource() {
        return dataSource;
    }

    public void setProperties(){
        dataSource.setUrl(protocol + ip + ":" + port + "/" + databaseName);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
    }

    public boolean connectionStatus() {
        try {
            dataSource.getConnection();
            connectionStatus = true;
            return true;
        } catch (SQLException e){
            connectionStatus = false;
            return false;

        }
    }

    public static String getProtocol() {
        return protocol;
    }

    public static String getIp() {
        return ip;
    }

    public static String getPort() {
        return port;
    }

    public static String getDatabaseName() {
        return databaseName;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }


    public static void setIp(String ip) {
        DBConnector.ip = ip;
    }

    public static void setPort(String port) {
        DBConnector.port = port;
    }

    public static void setDatabaseName(String databaseName) {
        DBConnector.databaseName = databaseName;
    }

    public static void setUsername(String username) {
        DBConnector.username = username;
    }

    public static void setPassword(String password) {
        DBConnector.password = password;
    }

    public boolean isConnectionStatus() {
        return connectionStatus;
    }

}