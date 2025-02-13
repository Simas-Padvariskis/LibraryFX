package com.example.pamokafx2.dao;

import com.example.pamokafx2.Models.Author;
import com.example.pamokafx2.Models.Book;
import com.example.pamokafx2.Models.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class BooksDAO {
    private Connection conn;

    private static final Logger logger = Logger.getLogger(BooksDAO.class.getName());

    public BooksDAO(Connection conn) {
        this.conn = conn;
    }

    public void create(String name, String author, String year) {
        String sql = "INSERT INTO books (Name, Author, Year, User_id, Author_id) VALUES (?, ?, ?, ?, ?)";

        int userId = Model.getInstance().getLoggedUserId();
        int authorId = Model.getInstance().getAuthorId(author);

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, name);
            stmt.setString(2, author);
            stmt.setString(3, year);
            stmt.setInt(4, userId);
            stmt.setInt(5, authorId);
            stmt.executeUpdate();
            logger.info("Books created successfully");
        }catch (SQLException e) {
            logger.severe("Error creating book: " + e.getMessage());
        }
    }

    public ObservableList<Book> findAll() {
        ObservableList<Book> books = FXCollections.observableArrayList();
        String sql = "SELECT ID, Name, Author, Year FROM books";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                String author = resultSet.getString("Author");
                String year = resultSet.getString("Year");
                Book book = new Book(id, name, author, year);
                books.add(book);
            }
        }catch (SQLException e) {
            logger.severe("Error fetching books: " + e.getMessage());
        }
        return books;
    }

    public void delete(int id) {
        String sql = "DELETE FROM books WHERE ID = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0){
                logger.info("Book with ID " + id + " wes deleted successfully");
            }else{
                logger.warning("No book found with ID " + id);
            }
        }catch(SQLException e){
            logger.severe("Error deleting book with  ID: " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void update(Object entity) {
        if(!(entity instanceof Book)){
            throw new IllegalArgumentException("Expected Book object");
        }

        Book book = (Book) entity;

        String sql = "UPDATE books SET Name = ?, Author = ?, Year = ? WHERE ID = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, book.getName());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getYear());
            stmt.setInt(4, book.getId());

            int rowsUpdated = stmt.executeUpdate();

            if(rowsUpdated > 0){
                logger.info("Book updated: " + book);
            }else{
                logger.warning("No book found with id: " + book.getId());
            }
        }catch (SQLException e){
            logger.severe("Error updating book: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
