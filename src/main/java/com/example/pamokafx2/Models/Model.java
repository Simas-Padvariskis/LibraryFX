package com.example.pamokafx2.Models;

import com.example.pamokafx2.Views.ViewFactory;
import com.example.pamokafx2.dao.*;
import javafx.collections.ObservableList;

import javax.swing.text.View;
import java.sql.SQLOutput;
import java.time.LocalDate;

public class Model {
    private static Model model; //Singleton instance
    private final ViewFactory viewFactory;
    public final UserDAO userDAO;
    private boolean loginSuccessFlag;
    private User currentUser;
    public final AuthorsDAO authorsDAO;;
    public final BooksDAO booksDAO;
    public final ReadersDAO readersDAO;
    public final HandedBooksDAO handedBooksDAO;

    private Model () {
        this.viewFactory = new ViewFactory();
        this.userDAO = new UserDAO(new DatabaseDriver().getConnection());
        this.authorsDAO = new AuthorsDAO(new DatabaseDriver().getConnection());
        this.booksDAO = new BooksDAO(new DatabaseDriver().getConnection());
        this.readersDAO = new ReadersDAO(new DatabaseDriver().getConnection());
        this.handedBooksDAO = new HandedBooksDAO(new DatabaseDriver().getConnection());
        this.currentUser = null;
    }

    /*
    * Return singleton instance of the Model class
    * @return the singleton instance
     */

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    /*
    * Get ViewFactory instance
    * @return ViewFactory instance
     */

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    /**
     *
     * @return loginSuccessFlag
     */
    public boolean getLoginSuccessFlag(){
        return loginSuccessFlag;
    }

    /**
     * Set loginSuccessFlag
     * @param flag
     */
    public void setLoginSuccessFlag(boolean flag){
        this.loginSuccessFlag = flag;
    }

    /**
     * Create new use in DB
     *
     * @param userName
     * @param password
     */

    public void createUser(String userName, String password){
//        System.out.println("user name " + userName);
//        System.out.println("password " + password);
        userDAO.createUser(userName, password, LocalDate.now());
    }

    public void checkCredentials(String userName, String password){
        User user = userDAO.findUserByCredentials(userName, password);
        if(user != null){
            this.loginSuccessFlag = true;
            this.currentUser = user;
        }
    }

    /**
     * Get current user name
     *
     * @return userName
     */

    public String getLoggedUserName(){
        return currentUser != null ? currentUser.userNameProperty(): null;
    }

    /**
     * Get current user id
     *
     * @return id - user id
     */

    public int getLoggedUserId(){
        return currentUser != null ? currentUser.getId() : null;
    }

    /*
    * Check if user exists in system,
    * @param userName
    * @return true if user exist
     */

    public boolean isUserExist(String userName){
        return userDAO.isUserExist(userName);
    }

    /**
     * Check if exist users in system
     *
     * @return true if users exist
     */

    public boolean hasRegisteredUsers(){
        return userDAO.countUsers() > 0;
    }

    /**
     * Create author
     */

    public void createAuthor(String firstName, String lastName, String email, String city){
        authorsDAO.create(firstName, lastName, email, city);
    }

    /**
     * Retrieves all authors from DB
     *
     * @return authors
     */

    public ObservableList<Author> getAuthors(){
        return authorsDAO.findAll();
    }

    /**
     * Retrieves all authors lastNames from DB
     *
     * @return String
     */

    public ObservableList<String> getAuthorLastNames(){
        return authorsDAO.findLastNames();
    }

//    /**
//     * Retrieves all readers names from DB
//     *
//     * @return String
//     */
//
//    public ObservableList<String> getReadersNames(){
//        return readersDAO.findReadersNames();
//    }

//    /**
//     * Retrieves all booksNames and authors from DB
//     *
//     * @return String
//     */
//
//    public ObservableList<String> getBooksWithAuthors(){
//        return booksDAO.findBooksWithAuthors();
//    }

    /**
     * Create book
     */

    public void createBook(String isbn, String name, String category, String description, String pageNumber, String year, String price, String author){
        booksDAO.create(isbn, name, category, description, pageNumber, year, price, author);
    }

    /**
     * Retrieves all books from DB
     *
     * @return books
     */

    public ObservableList<Book> getBooks(){
        return booksDAO.findAll();
    }

    /**
     * Find author id by last name
     * @param lastName
     */

    public int getAuthorId(String lastName){
        int id = -1;
        for (int i = 0; i < getAuthors().size(); i++) {
            if (getAuthors().get(i).getLastName().equals(lastName)) {
                id = getAuthors().get(i).getId();
                break;
            }
        }
        return id;
    }

//    /**
//     * Find book id by bookName and bookAuthor
//     * @param bookName
//     * @param authorName
//     */
//
//    public int getBookId(String bookName, String authorName){
//        int id = -1;
//        for (int i = 0; i < getBooks().size(); i++) {
//            if (getBooks().get(i).getName().equals(bookName) && getBooks().get(i).getAuthor().equals(authorName)) {
//                id = getBooks().get(i).getId();
//                break;
//            }
//        }
//        return id;
//    }

//    /**
//     * Find reader id by reader firstName and lastName
//     * @param firstName
//     * @param lastName
//     */
//
//    public int getReaderId(String firstName, String lastName){
//        int id = -1;
//        for (int i = 0; i < getReaders().size(); i++) {
//            if (getReaders().get(i).getFirstName().equals(firstName) && getReaders().get(i).getLastName().equals(lastName)) {
//                id = getReaders().get(i).getId();
//                break;
//            }
//        }
//        return id;
//    }

    /**
     * Delete author from DB by ID
     * @param id the ID author
     */
    public void deleteAuthor(int id){
        authorsDAO.delete(id);
    }

    /**
     * Update existing author in the database
     */

    public void updateAuthor(Author author){
        authorsDAO.update(author);
    }

    /**
     * Delete book from DB by ID
     * @param id the ID book
     */
    public void deleteBook(int id){
        booksDAO.delete(id);
    }

    /**
     * Update existing book in the database
     */

    public void updateBook(Book book){
        booksDAO.update(book);
    }

    /**
     * Retrieves all readers from DB
     *
     * @return readers
     */

    /**
     * Create reader
     */

    public void createReader(String firstName, String lastName){
        readersDAO.create(firstName, lastName);
    }

    public ObservableList<Reader> getReaders(){
        return readersDAO.findAll();
    }

    /**
     * Delete reader from DB by ID
     * @param id the ID reader
     */
    public void deleteReader(int id){
        readersDAO.delete(id);
    }

    /**
     * Update existing reader in the database
     */

    public void updateReader(Reader reader){
        readersDAO.update(reader);
    }

    /**
     * Create handed book
     */

    public void createHandedBook(Book book, Reader reader, String returnDate){
        handedBooksDAO.create(book, reader, returnDate);
    }

    /**
     * Retrieves all handedBooks from DB
     *
     * @return authors
     */

    public ObservableList<HandedBook> getHandedBooks(){
        return handedBooksDAO.findAll();
    }

    /**
     * Delete handedBook from DB by ID
     * @param id the ID handedBook
     */
    public void deleteHandedBook(int id){
        handedBooksDAO.delete(id);
    }

    /**
     * Return handedBook from DB by ID
     * @param id the ID handedBook
     */
    public void returnHandedBook(int id){
        handedBooksDAO.returnBook(id);
    }

//    /**
//     * Find book by bookName and authorName
//     * @param bookName
//     * @param authorName
//     */
//
//    public Book getBookByNameAndAuthor (String bookName, String authorName){
//        Book foundBook = null;
//        for (int i = 0; i < getBooks().size(); i++) {
//            if (getBooks().get(i).getName().equals(bookName) && getBooks().get(i).getAuthor().equals(authorName)) {
//                foundBook = getBooks().get(i);
//                break;
//            }
//        }
//        return foundBook;
//    }
}
