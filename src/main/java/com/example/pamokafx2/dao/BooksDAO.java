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

    public void create(String isbn, String name, String category, String description, String pageNumber, String year, String price, String author) {
        String sql = "INSERT INTO books (ISBN, Name, Category, Description, Page_number, Year, Price, Author, User_id, Author_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int userId = Model.getInstance().getLoggedUserId();
        int authorId = Model.getInstance().getAuthorId(author);

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, isbn);
            stmt.setString(2, name);
            stmt.setString(3, category);
            stmt.setString(4, description);
            stmt.setString(5, pageNumber);
            stmt.setString(6, year);
            stmt.setString(7, price);
            stmt.setString(8, author);
            stmt.setInt(9, userId);
            stmt.setInt(10, authorId);
            stmt.executeUpdate();
            logger.info("Books created successfully");
        }catch (SQLException e) {
            logger.severe("Error creating book: " + e.getMessage());
        }
    }

    public ObservableList<Book> findAll() {
        ObservableList<Book> books = FXCollections.observableArrayList();
        String sql = "SELECT ID, ISBN, Name, Category, Description, Page_number, Year, Price, Author FROM books";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("ID");
                String isbn = resultSet.getString("ISBN");
                String name = resultSet.getString("Name");
                String category = resultSet.getString("Category");
                String description = resultSet.getString("Description");
                String pageNumber = resultSet.getString("Page_number");
                String year = resultSet.getString("Year");
                String price = resultSet.getString("Price");
                String author = resultSet.getString("Author");
                Book book = new Book(id, isbn, name, category, description, pageNumber, year, price, author);
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

        String sql = "UPDATE books SET ISBN = ?, Name = ?, Category = ?, Description = ?, Page_number = ?, Year = ?, Price = ?, Author = ?, Author_id = ? WHERE ID = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, book.getIsbn());
            stmt.setString(2, book.getName());
            stmt.setString(3, book.getCategory());
            stmt.setString(4, book.getDescription());
            stmt.setString(5, book.getPage_number());
            stmt.setString(6, book.getYear());
            stmt.setString(7, book.getPrice());
            stmt.setString(8, book.getAuthor());
            stmt.setInt(9, Model.getInstance().getAuthorId(book.getAuthor()));
            stmt.setInt(10, book.getId());

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

    public ObservableList<String> findBooksWithAuthors(){
        ObservableList<String> booksNamesAuthors = FXCollections.observableArrayList();
        String sql = "SELECT Name, Author FROM books";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                String name = resultSet.getString("Name");
                String author = resultSet.getString("Author");
                booksNamesAuthors.add(name + " - " + author);
            }
        }catch (SQLException e) {
            logger.severe("Error fetching bookNames and authors: " + e.getMessage());
        }
        return booksNamesAuthors;
    }
}
