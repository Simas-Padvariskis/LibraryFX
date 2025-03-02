package com.example.pamokafx2.dao;

import com.example.pamokafx2.Models.Book;
import com.example.pamokafx2.Models.HandedBook;
import com.example.pamokafx2.Models.Model;
import com.example.pamokafx2.Models.Reader;
import com.example.pamokafx2.Utilities.AlertUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Logger;

public class HandedBooksDAO {
    private Connection conn;

    private static final Logger logger = Logger.getLogger(HandedBooksDAO.class.getName());

    public HandedBooksDAO(Connection conn) {
        this.conn = conn;
    }

    public void create(Book book, Reader reader, String returnDate) {
        String sql = "INSERT INTO handed_books (ID, ISBN, Name, Category, Author, Reserved, Return_date, Reader_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        int bookId = book.getId();
        int readerId = reader.getId();
        String isbn = book.getIsbn();
        String name = book.getName();
        String category = book.getCategory();
        String author = book.getAuthor();
        String reserved = book.getReserved();

        //Find if handedBook is already taken
        boolean handedBookIsTaken = false;
        ObservableList<HandedBook> handedBooks = findAll();
        for (HandedBook handedBook : handedBooks) {
            if (handedBook.getId() == bookId) {
                if (handedBook.getReserved().equals("Paimta")) {
                    handedBookIsTaken = true;
                    break;
                }
            }
        }

        if (!handedBookIsTaken) {
            try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
                stmt.setInt(1, bookId);
                stmt.setString(2, isbn);
                stmt.setString(3, name);
                stmt.setString(4, category);
                stmt.setString(5, author);
                stmt.setString(6, reserved);
                stmt.setString(7, returnDate);
                stmt.setInt(8, readerId);
                stmt.executeUpdate();
                book.setReserved("Paimta");
                AlertUtility.displayInformation("Knyga sėkmingai išduota");
                logger.info("Handed Book created successfully");
                setReserved(book, bookId);
            } catch (SQLException e) {
                logger.severe("Error creating a Handed Book: " + e.getMessage());
            }
        }else{
            AlertUtility.displayInformation("Knyga jau paimta");
            logger.info("Book is already taken");
        }

    }

    public ObservableList<HandedBook> findAll() {
        ObservableList<HandedBook> handedBooks = FXCollections.observableArrayList();
        String sql = "SELECT ID, ISBN, Name, Category, Author, Reserved, Return_date FROM handed_books";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("ID");
                String isbn = resultSet.getString("ISBN");
                String name = resultSet.getString("Name");
                String category = resultSet.getString("Category");
                String author = resultSet.getString("Author");
                String reserved = resultSet.getString("Reserved");
                String returnDate = resultSet.getString("Return_date");
                HandedBook handedBook = new HandedBook(id, isbn, name, category, author, reserved, returnDate);
                handedBooks.add(handedBook);
            }
        }catch (SQLException e) {
            logger.severe("Error fetching books: " + e.getMessage());
        }
        return handedBooks;
    }

    public void delete(int id) {
        String sql = "DELETE FROM handed_books WHERE ID = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0){
                logger.info("handedBook with ID " + id + " wes deleted successfully");
            }else{
                logger.warning("No handedBook found with ID " + id);
            }
        }catch(SQLException e){
            logger.severe("Error deleting handedBook with  ID: " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void returnBook (int id) {
        String sql = "UPDATE handed_books SET Reserved = ?, Return_date = ? WHERE ID = ?";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, "Grąžinta");
            stmt.setString(2, LocalDate.now().toString());
            stmt.setInt(3, id);

            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0){
                logger.info("handedBook with ID " + id + " was returned successfully");
            }else{
                logger.warning("No handedBook found with ID " + id);
            }
        }catch(SQLException e){
            logger.severe("Error updating handedBook with  ID: " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
//
//    public void delete(int id) {
//        String sql = "DELETE FROM books WHERE ID = ?";
//        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
//            stmt.setInt(1, id);
//            int rowsAffected = stmt.executeUpdate();
//            if(rowsAffected > 0){
//                logger.info("Book with ID " + id + " wes deleted successfully");
//            }else{
//                logger.warning("No book found with ID " + id);
//            }
//        }catch(SQLException e){
//            logger.severe("Error deleting book with  ID: " + id + ": " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    public void update(Object entity) {
//        if(!(entity instanceof Book)){
//            throw new IllegalArgumentException("Expected Book object");
//        }
//
//        Book book = (Book) entity;
//
//        String sql = "UPDATE books SET ISBN = ?, Name = ?, Category = ?, Description = ?, Page_number = ?, Year = ?, Price = ?, Author = ?, Reserved = ?, Author_id = ? WHERE ID = ?";
//        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
//            stmt.setString(1, book.getIsbn());
//            stmt.setString(2, book.getName());
//            stmt.setString(3, book.getCategory());
//            stmt.setString(4, book.getDescription());
//            stmt.setString(5, book.getPage_number());
//            stmt.setString(6, book.getYear());
//            stmt.setString(7, book.getPrice());
//            stmt.setString(8, book.getAuthor());
//            stmt.setString(9, book.getReserved());
//            stmt.setInt(10, Model.getInstance().getAuthorId(book.getAuthor()));
//            stmt.setInt(11, book.getId());
//
//            int rowsUpdated = stmt.executeUpdate();
//
//            if(rowsUpdated > 0){
//                logger.info("Book updated: " + book);
//            }else{
//                logger.warning("No book found with id: " + book.getId());
//            }
//        }catch (SQLException e){
//            logger.severe("Error updating book: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    public ObservableList<String> findBooksWithAuthors(){
//        ObservableList<String> booksNamesAuthors = FXCollections.observableArrayList();
//        String sql = "SELECT Name, Author FROM books";
//
//        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
//            ResultSet resultSet = stmt.executeQuery();
//            while(resultSet.next()){
//                String name = resultSet.getString("Name");
//                String author = resultSet.getString("Author");
//                booksNamesAuthors.add(name + " - " + author);
//            }
//        }catch (SQLException e) {
//            logger.severe("Error fetching bookNames and authors: " + e.getMessage());
//        }
//        return booksNamesAuthors;
//    }

    public void setReserved(Object entity, int bookId) {
        if(!(entity instanceof Book)){
            throw new IllegalArgumentException("Expected Book object");
        }

        Book book = (Book) entity;

        String sql = "UPDATE handed_books SET Reserved = ? WHERE ID = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, "Paimta");
            stmt.setInt(2, bookId);

            int rowsUpdated = stmt.executeUpdate();

            if(rowsUpdated > 0){
                logger.info("Reservation status set successfully: " + book);
            }else{
                logger.warning("No book found with id: " + book.getId());
            }
        }catch (SQLException e){
            logger.severe("Error error setting books reservation status: " + e.getMessage());
            e.printStackTrace();
        }
    }

//    public ObservableList<String> findBooksWithAuthors(){
//        ObservableList<String> booksNamesAuthors = FXCollections.observableArrayList();
//        String sql = "SELECT Name, Author FROM books";
//
//        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
//            ResultSet resultSet = stmt.executeQuery();
//            while(resultSet.next()){
//                String name = resultSet.getString("Name");
//                String author = resultSet.getString("Author");
//                booksNamesAuthors.add(name + " - " + author);
//            }
//        }catch (SQLException e) {
//            logger.severe("Error fetching bookNames and authors: " + e.getMessage());
//        }
//        return booksNamesAuthors;
//    }
}
