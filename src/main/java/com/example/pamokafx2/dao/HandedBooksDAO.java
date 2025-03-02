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
        String sql = "INSERT INTO handed_books (ISBN, Name, Category, Author, Reserved, Returned_date, Return_date, Reader_id, Book_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int bookId = book.getId();
        int readerId = reader.getId();
        String isbn = book.getIsbn();
        String name = book.getName();
        String category = book.getCategory();
        String author = book.getAuthor();
        String reserved = "Paimta";

        //Find if handedBook is already taken
//        String idsql = "SELECT Book_id FROM handed_books WHERE ID = ?";

        boolean handedBookIsTaken = false;
        ObservableList<HandedBook> handedBooks = findAll();
        for (HandedBook handedBook : handedBooks) {
            if (retrieveBook_idFromHandedBook(handedBook.getId()) == bookId) {
                if (handedBook.getReserved().equals("Paimta")) {
                    handedBookIsTaken = true;
                    break;
                }
            }
        }

        if (!handedBookIsTaken) {
            try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
                stmt.setString(1, isbn);
                stmt.setString(2, name);
                stmt.setString(3, category);
                stmt.setString(4, author);
                stmt.setString(5, reserved);
                stmt.setString(6, null);
                stmt.setString(7, returnDate);
                stmt.setInt(8, readerId);
                stmt.setInt(9, bookId);
                stmt.executeUpdate();
                AlertUtility.displayInformation("Knyga sėkmingai išduota");
                logger.info("Handed Book created successfully");
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
        String sql = "SELECT ID, ISBN, Name, Category, Author, Reserved, Returned_date, Return_date FROM handed_books";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("ID");
                String isbn = resultSet.getString("ISBN");
                String name = resultSet.getString("Name");
                String category = resultSet.getString("Category");
                String author = resultSet.getString("Author");
                String reserved = resultSet.getString("Reserved");
                String returnedDate = resultSet.getString("Returned_date");
                String returnDate = resultSet.getString("Return_date");
                HandedBook handedBook = new HandedBook(id, isbn, name, category, author, reserved, returnedDate, returnDate);
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
        String sql = "UPDATE handed_books SET Reserved = ?, Returned_date = ? WHERE ID = ?";

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

    public int retrieveBook_idFromHandedBook(int handedBookId) {
        String sql = "SELECT Book_id FROM handed_books WHERE ID = ?";
        int id = -1;
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setInt(1, handedBookId);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("Book_id");
            } else {
                System.out.println("No Book_id found.");
            }
        }catch (SQLException e) {
            logger.severe("Error finding Book_id: " + e.getMessage());
        }
        return id;
    }
}
