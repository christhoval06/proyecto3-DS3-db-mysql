package utils; /**
 * Created with IntelliJ IDEA.
 * User: cristobal
 * Date: 06/17/14
 * Time: 09:48 AM
 * To change this template use File | Settings | File Templates.
 */

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by Christhoval on 20/5/14.
 */
public class DB {

    private String url = "mysql://localhost:3306", dbname = "tiendadb", usr = "root", psw = "none123", SQL = "", ERROR = null, logFile = "tiendadb.log";

    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    private boolean isOpen = false, isConnected = false, DEBUG = true;
    private Logger log;

    public DB() {
        DEBUG = true;
        init();
    }

    public DB(boolean d) {
        DEBUG = d;
        init();
    }

    public DB(String lf) {
        DEBUG = true;
        logFile = lf;
        init();
    }

    public DB(String lf, boolean d) {
        DEBUG = d;
        logFile = lf;
        init();
    }

    private void init() {
        Properties configFile = new Properties();
        try {
            configFile.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
            url = configFile.getProperty("url");
            dbname = configFile.getProperty("dbname");
            usr = configFile.getProperty("usr");
            psw = configFile.getProperty("psw");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        log = new Logger(logFile, DEBUG);
        abrir();
    }

    public ResultSet select(String sql) {
        try {
            this.SQL = sql;
            if (abrir()) {
                log.log("sql -> " + sql);
                stmt = con.createStatement();
                return stmt.executeQuery(sql);
            }
        } catch (Exception e) {
            ERROR = e.getMessage();
            log.log("Error -> " + ERROR);
            e.printStackTrace();
        }
        return null;
    }

    public boolean isConnected() {
        return isConnected;
    }
    public boolean abrir() {
        log.log("-------- MySQL JDBC Connection Testing ------------");
        if (!isConnected()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                log.log("Error -> " + "Not find MySQL JDBC Driver");
                e.printStackTrace();
                return false;
            }

            log.log("MySQL JDBC Driver Registered!");
            try {
                con = DriverManager.getConnection(String.format("jdbc:%s/%s", url, dbname), usr, psw);
            } catch (SQLException e) {
                log.log("Error -> " + "Connection Failed! Check output console");
                e.printStackTrace();
                return false;
            }

            if (con != null) {
                log.log("You made it, take control your database now!");
                isOpen = true;
                isConnected = true;
                return true;
            } else {
                log.log("Error -> " + "Failed to make connection!");
                return false;
            }
        }
        return true;
    }

    public void close() {
        try {
            if(stmt != null){
             if(!stmt.isClosed())
                 stmt.close();
            }
            con.close();
            isConnected = false;
        } catch (SQLException e) {
            isConnected = true;
            ERROR = e.getMessage();
            log.log("Error -> " + "Failed to close connection!");
            log.log("Error -> " + ERROR);
            e.printStackTrace();
        }
    }

    public boolean execSQL(String sql) {
        log.log("sql -> " + sql);
        try {
            this.SQL = sql;
            if (abrir()) {
                stmt = con.createStatement();
                return stmt.executeUpdate(SQL) > 0;
            }
        } catch (Exception e) {
            ERROR = e.getMessage();
            log.log("Error -> " + ERROR);
            e.printStackTrace();
        }
        return false;
    }

    public void log(String s){
        log.log("sql -> " + s);
    }
}

