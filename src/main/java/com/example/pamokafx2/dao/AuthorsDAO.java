package com.example.pamokafx2.dao;

import com.example.pamokafx2.Models.Author;
import com.example.pamokafx2.Models.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class AuthorsDAO implements GenericDAO {
    private Connection conn;

    private static final Logger logger = Logger.getLogger(AuthorsDAO.class.getName());

    public AuthorsDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Object findById(int id) {
        return null;
    }

    @Override
    public void create(String firstName, String lastName, String email, String city) {
        String sql = "INSERT INTO authors (FirstName, LastName, Email, City, Date, User_id) VALUES (?, ?, ?, ?, ?, ?)";

        int userId = Model.getInstance().getLoggedUserId();

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, city);
            stmt.setDate(5, Date.valueOf(LocalDate.now()));
            stmt.setInt(6, userId);
            stmt.executeUpdate();
            logger.info("Authors created successfully");
        }catch (SQLException e) {
            logger.severe("Error creating author: " + e.getMessage());
        }
    }

    @Override
    public void update(Object entity) {
        if(!(entity instanceof Author)){
            throw new IllegalArgumentException("Expected Author object");
        }

        Author author = (Author) entity;

        String sql = "UPDATE authors SET FirstName = ?, LastName = ?, Email = ?, City = ? WHERE Id = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, author.getFirstName());
            stmt.setString(2, author.getLastName());
            stmt.setString(3, author.getEmail());
            stmt.setString(4, author.getCity());
            stmt.setInt(5, author.getId());

            int rowsUpdated = stmt.executeUpdate();

            if(rowsUpdated > 0){
                logger.info("Author updated: " + author);
            }else{
                logger.warning("No author found with id: " + author.getId());
            }
        }catch (SQLException e){
            logger.severe("Error updating author: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM authors WHERE Id = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0){
                logger.info("Author with ID " + id + " wes deleted successfully");
            }else{
                logger.warning("No author found with ID " + id);
            }
        }catch(SQLException e){
            logger.severe("Error deleting author with  ID: " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Author> findAll() {
        ObservableList<Author> authors = FXCollections.observableArrayList();
        String sql = "SELECT id, FirstName, LastName, Email, City, Date FROM authors";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("Id");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String email = resultSet.getString("Email");
                String city = resultSet.getString("City");
                Author author = new Author(id, firstName, lastName, email, city);
                authors.add(author);
            }
        }catch (SQLException e) {
            logger.severe("Error fetching authors: " + e.getMessage());
        }
        return authors;
    }

    public ObservableList<String> findLastNames(){
        ObservableList<String> authorNames = FXCollections.observableArrayList();
        String sql = "SELECT LastName FROM authors";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                String lastName = resultSet.getString("LastName");
                authorNames.add(lastName);
            }
        }catch (SQLException e) {
            logger.severe("Error fetching authors lastNames: " + e.getMessage());
        }
        return authorNames;
      }
}
