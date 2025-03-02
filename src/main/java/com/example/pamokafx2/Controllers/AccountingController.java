package com.example.pamokafx2.Controllers;

import com.example.pamokafx2.Models.Author;
import com.example.pamokafx2.Models.HandedBook;
import com.example.pamokafx2.Models.Model;
import com.example.pamokafx2.Utilities.AlertUtility;
import com.example.pamokafx2.Utilities.DialogUtility;
import com.example.pamokafx2.Views.MenuItems;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class AccountingController implements Initializable {

    public Button hand_book;
    public TableView books_table;
    public TableColumn col_id;
    public TableColumn col_isbn;
    public TableColumn col_name;
    public TableColumn col_category;
    public TableColumn col_author;
    public TableColumn col_rezervation;
    public MenuItem remove_book;
    public TableColumn col_return;
    public TableColumn col_return_time;
    public MenuItem return_book;
    public ChoiceBox <String> filter_choice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hand_book.setOnAction(e -> onHandBook());
        initTableColumns();
        loadHandedBooksData();

        remove_book.setOnAction(event -> onRemoveHandedBook());
//        setRowFactoryForAuthorsTable();
        return_book.setOnAction(event -> onReturnHandedBook());

        // Initialize the ChoiceBox with filter options
        filter_choice.setItems(FXCollections.observableArrayList("Visos", "Grąžintos", "Vėluojama grąžinti"));
        filter_choice.setValue("Visos"); // Default selection
        filter_choice.setOnAction(e -> filterBooks());
    }

    /**
     * Open createHandBookWindow
     */

    public void onHandBook(){
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuItems.HAND_BOOK);
    }

    /*
     * Init the table columns with handed Book model
     */

    private void initTableColumns(){
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        col_rezervation.setCellValueFactory(new PropertyValueFactory<>("reserved"));
        col_return_time.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    }

    /**
     * Load handedBooks data into table
     */

    private void loadHandedBooksData(){
        ObservableList<HandedBook> handedBooks = Model.getInstance().getHandedBooks();
        books_table.setItems(handedBooks);
    }

    /**
     * Handle handedBook remove
     */

    private void onRemoveHandedBook(){
        HandedBook selectedHandedBook = (HandedBook) books_table.getSelectionModel().getSelectedItem();
        if (selectedHandedBook.getReserved().equals("Grąžinta")) {
            if (selectedHandedBook == null) {
                AlertUtility.displayError("Pasirinkite išduotą knygą");
            }

            boolean confirmed = AlertUtility.displayConfirmation(
                    "Ar tikrai norite pašalinti išduotą knygą? "
            );
            if (confirmed) {
                Model.getInstance().deleteHandedBook(selectedHandedBook.getId());
                ObservableList<HandedBook> handedBooks = books_table.getItems();
                handedBooks.remove(selectedHandedBook);
                AlertUtility.displayInformation("Išduota knyga pašalintas sėkmingai");
            }
        }else{
            AlertUtility.displayInformation("Knygos pašalinti negalima - knyga dar negrąžinta");
        }
    }

    /**
     * Handle handedBook remove
     */

    private void onReturnHandedBook(){
        HandedBook selectedHandedBook = (HandedBook) books_table.getSelectionModel().getSelectedItem();
        if (selectedHandedBook.getReserved().equals("Grąžinta")) {
            AlertUtility.displayInformation("Knyga jau grąžinta");
        }else{
            if (selectedHandedBook == null) {
                AlertUtility.displayError("Pasirinkite išduotą knygą");
            }

            boolean confirmed = AlertUtility.displayConfirmation(
                    "Ar tikrai norite grąžinti išduotą knygą? "
            );
            if (confirmed) {
                selectedHandedBook.setReserved("Grąžinta");
                selectedHandedBook.setReturnDate("");
                Model.getInstance().returnHandedBook(selectedHandedBook.getId());
                AlertUtility.displayInformation("Išduota knyga grąžinta sėkmingai");
            }
        }
    }

    /**
     * Filter handedBooks by chosen filter
     */

    private void filterBooks() {
        String selectedFilter = filter_choice.getValue();

        ObservableList<HandedBook> filteredBooks = Model.getInstance().getHandedBooks().filtered(book -> {
            if ("Visos".equals(selectedFilter)) {
                return true; // Show all handedBooks
            } else if ("Grąžintos".equals(selectedFilter)) {
                return "Grąžinta".equals(book.getReserved()); // Show only "returned" books
            } else if ("Vėluojama grąžinti".equals(selectedFilter)){
                return LocalDate.now().isAfter(LocalDate.parse(book.getReturnDate()));
            }
            return false;
        });

        // Update the table's items with the filtered list
        books_table.setItems(filteredBooks);
    }

//    /**
//     * Sets row factory for the handedBooks table
//     */
//
//    private void setRowFactoryForHandedBooksTable(){
//        books_table.setRowFactory(tv ->{
//            TableRow<HandedBook> row = new TableRow<>();
//            row.setOnMouseClicked(event -> {
//                if(event.getClickCount() == 2 && (!row.isEmpty())){
//                    HandedBook selectedhandedBook = row.getItem();
//                    editHandedBook(selectedhandedBook);
//                }
//            });
//            return row;
//        });
//    }
//
//    /**
//     * Opens dialog for editing handedBook
//     * @param handedBook
//     */
//
//    private void editHandedBook(HandedBook handedBook){
//        Optional<HandedBook> result = DialogUtility.showEditHandedBookDialog(handedBook);
//        result.ifPresent(updateHandedBook ->{
//            Model.getInstance().updateHandedBook(updateHandedBook);
//            loadHandedBooksData();
//        });
//    }
}
