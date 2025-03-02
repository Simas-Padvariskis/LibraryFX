package com.example.pamokafx2.Controllers;

import com.example.pamokafx2.Models.Author;
import com.example.pamokafx2.Models.Book;
import com.example.pamokafx2.Models.Model;
import com.example.pamokafx2.Utilities.AlertUtility;
import com.example.pamokafx2.Utilities.DialogUtility;
import com.example.pamokafx2.Views.MenuItems;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class BooksController implements Initializable {
    public Button add_book;
    public TableView books_table;
    public TableColumn col_id;
    public TableColumn col_name;
    public TableColumn col_author;
    public TableColumn col_year;
    public MenuItem remove_book;
    public TableColumn col_isbn;
    public TableColumn col_category;
    public TableColumn col_description;
    public TableColumn col_page_number;
    public TableColumn col_price;
    public TableColumn col_rezervation;
    public TextField filterISBN;
    public TextField filterAuthor;
    public TextField filterName;
    public Button filterButton;

    private FilteredList<Book> filteredBooks;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        add_book.setOnAction(e -> onCreateBook());
        initTableColumns();
        loadBookData();
        remove_book.setOnAction(event -> onRemoveBook());
        setRowFactoryForBooksTable();

        //Data filtering
        filteredBooks = new FilteredList<>(Model.getInstance().getBooks());
        books_table.setItems(filteredBooks);
        filterButton.setOnAction(event -> applyFilters());
    }

    /**
     * Open createBookWindow
     */

    public void onCreateBook(){
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuItems.CREATE_BOOK);
    }

    /*
     * Init the table columns with Book model
     */

    private void initTableColumns(){
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_page_number.setCellValueFactory(new PropertyValueFactory<>("page_number"));
        col_year.setCellValueFactory(new PropertyValueFactory<>("year"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
    }

    /**
     * Load Books data into table
     */

    private void loadBookData(){
        ObservableList<Book> books = Model.getInstance().getBooks();
        books_table.setItems(books);
    }

    /**
     * Handle book remove
     */

    private void onRemoveBook(){
        Book selectedBook = (Book) books_table.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            AlertUtility.displayError("Pasirinkite knygą");
        }

        boolean confirmed = AlertUtility.displayConfirmation(
                "Ar tikrai norite pašalinti knygą? "
        );
        if (confirmed) {
            Model.getInstance().deleteBook(selectedBook.getId());
            ObservableList<Book> books = books_table.getItems();
            books.remove(selectedBook);
            AlertUtility.displayInformation("Knyga pašalinta sėkmingai");
        }
    }

    /**
     * Opens dialog for editing book
     * @param book
     */

    private void editBook(Book book){
        Optional<Book> result = DialogUtility.showEditBookDialog(book);
        result.ifPresent(updateBook ->{
            Model.getInstance().updateBook(updateBook);
            loadBookData();
        });
    }

    /**
     * Sets row factory for the book table
     */

    private void setRowFactoryForBooksTable(){
        books_table.setRowFactory(tv ->{
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    Book selectedBook = row.getItem();
                    editBook(selectedBook);
                }
            });
            return row;
        });
    }

    /**
     * Books data filter
     */

    private void applyFilters(){
        String isbnFilter = filterISBN.getText().toLowerCase();
        String authorFilter = filterAuthor.getText().toLowerCase();
        String nameFilter = filterName.getText().toLowerCase();

        filteredBooks.setPredicate(book -> {
            if(!isbnFilter.isEmpty() && !book.getIsbn().contains(isbnFilter)){
                return false;
            }

            if(!authorFilter.isEmpty() && !book.getAuthor().toLowerCase().contains(authorFilter)){
                return false;
            }

            if(!nameFilter.isEmpty() && !book.getName().toLowerCase().contains(nameFilter)){
                return false;
            }

            return true;
        });
    }
}
