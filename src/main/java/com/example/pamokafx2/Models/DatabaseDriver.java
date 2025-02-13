package com.example.pamokafx2.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseDriver {
    //Database connection instance
    private Connection conn;

    //Logger for logging info
    private static final Logger logger = Logger.getLogger(DatabaseDriver.class.getName());

    /**
     * Manage connection to DB
     */
    public DatabaseDriver(){
        try{
            this.conn = DriverManager.getConnection("jdbc:sqlite:library.db");
        }catch (SQLException e){
            logger.severe("Can not connect to DB: " + e.getMessage());
        }
    }

    /**
     * Get current DB connection
     * @return the database connection object
     */
    public Connection getConnection (){
        return conn;
    }

    public void closeConnection(){
        try {
            if (conn != null && !conn.isClosed()){
                //Closing connection if it is not already closed
                conn.close();
            }
        }catch (SQLException e){
            logger.severe("Error closing database connection: " + e.getMessage());
        }
    }
}
