package com.example.pamokafx2.dao;

import com.example.pamokafx2.Models.Model;
import com.example.pamokafx2.Models.Reader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Logger;

public class ReadersDAO {
    private Connection conn;

    private static final Logger logger = Logger.getLogger(ReadersDAO.class.getName());

    public ReadersDAO(Connection conn) {
        this.conn = conn;
    }

    public void create(String firstName, String lastName) {
        String sql = "INSERT INTO readers (FirstName, LastName, User_id) VALUES (?, ?, ?)";

        int userId = Model.getInstance().getLoggedUserId();

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setInt(3, userId);
            stmt.executeUpdate();
            logger.info("Readers created successfully");
        }catch (SQLException e) {
            logger.severe("Error creating reader: " + e.getMessage());
        }
    }


    public ObservableList<Reader> findAll() {
        ObservableList<Reader> readers = FXCollections.observableArrayList();
        String sql = "SELECT ID, FirstName, LastName FROM readers";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("ID");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                Reader reader = new Reader(id, firstName, lastName);
                readers.add(reader);
            }
        }catch (SQLException e) {
            logger.severe("Error fetching readers: " + e.getMessage());
        }
        return readers;
    }

    public void delete(int id) {
        String sql = "DELETE FROM readers WHERE ID = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0){
                logger.info("Reader with ID " + id + " wes deleted successfully");
            }else{
                logger.warning("No reader found with ID " + id);
            }
        }catch(SQLException e){
            logger.severe("Error deleting reader with  ID: " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void update(Object entity) {
        if(!(entity instanceof Reader)){
            throw new IllegalArgumentException("Expected Reader object");
        }

        Reader reader = (Reader) entity;

        String sql = "UPDATE readers SET FirstName = ?, LastName = ? WHERE ID = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, reader.getFirstName());
            stmt.setString(2, reader.getLastName());
            stmt.setInt(3, reader.getId());

            int rowsUpdated = stmt.executeUpdate();

            if(rowsUpdated > 0){
                logger.info("Reader updated: " + reader);
            }else{
                logger.warning("No reader found with id: " + reader.getId());
            }
        }catch (SQLException e){
            logger.severe("Error updating reader: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ObservableList<String> findReadersNames(){
        ObservableList<String> readerNames = FXCollections.observableArrayList();
        String sql = "SELECT firstName, lastName FROM readers";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                readerNames.add(firstName + " " + lastName);
            }
        }catch (SQLException e) {
            logger.severe("Error fetching readersNames: " + e.getMessage());
        }
        return readerNames;
    }
}
