package com.example.pamokafx2.dao;

import com.example.pamokafx2.Models.User;
import com.example.pamokafx2.Utilities.UserUtility;

import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Logger;

public class UserDAO {
    private Connection conn;
    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());

    /**
     * Initialize connection to DB
     */

    public UserDAO(Connection conn){
        this.conn = conn;
    }

    /**
     * Find user by credentials
     *
     * @param userName the userName of the user
     * @param password the uer password
     */

    public User findUserByCredentials(String userName, String password){
        ResultSet resultSet = null;
        User user = null;
        String sql = "SELECT id, UserName, Password FROM users WHERE UserName = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, userName);
            resultSet = stmt.executeQuery();
            if(resultSet.next()){
                String storedPasswordHash = resultSet.getString("Password");
                if (UserUtility.verifyPassword(password, storedPasswordHash)){
                    user = new User(resultSet.getInt("Id"), resultSet.getString("UserName"));
                }
            }
        }catch(SQLException e){
            logger.severe("Database error while trying to find user by credentials");
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Create new user in DB
     * @param userName string
     * @param password string
     * @param date date
     */
    public void createUser(String userName, String password, LocalDate date){
        String sql = "INSERT INTO users (UserName, Password, Date) VALUES (?, ?, ?)";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, userName);
            stmt.setString(2, UserUtility.hashPassword(password));
            stmt.setDate(3, Date.valueOf(date));
            stmt.executeUpdate();
        }catch (SQLException e){
            logger.severe("Error creating user: " + e.getMessage());
        }
    }

    /**
     * Check if user exists in DB
     *
     * @param userName - user name
     * @return true - if user exist in DB
     */

    public boolean isUserExist (String userName){
        String sql = "SELECT COUNT(*) AS user_count FROM users WHERE UserName = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, userName);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt("user_count") > 0;
            }
        }catch (SQLException e){
            logger.severe("Database checking user exist: " + e.getMessage());
        }
        return false;
    }

    /**
     * Count users in DB
     *
     * @return total count of users
     */

    public int countUsers(){
        int userCount = 0;
        String sql = "SELECT COUNT(*) AS user_count FROM users";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery()){
                if(resultSet.next()){
                    userCount = resultSet.getInt("user_count");
                    logger.info("User count: " + userCount);
                }
            }
        catch (SQLException e){
            logger.severe("Error counting users: " + e.getMessage());
        }
        return userCount;
    }
}
