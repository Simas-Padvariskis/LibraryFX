package com.example.pamokafx2.Controllers;

import com.example.pamokafx2.Models.Book;
import com.example.pamokafx2.Models.Model;
import com.example.pamokafx2.Models.Reader;
import com.example.pamokafx2.Utilities.AlertUtility;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class HandBookController implements Initializable {
    @FXML
    public DatePicker dateChoice;
    public ChoiceBox <Reader> readerChoice;
    public ChoiceBox <Book> bookChoice;
    public Button hand_book_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        readerChoice.getItems().addAll(Model.getInstance().getReaders());
        bookChoice.getItems().addAll(Model.getInstance().getBooks());

        hand_book_btn.setOnAction(e -> onHandBook());

        // Get today's date
        LocalDate today = LocalDate.now();

        // Limit the date picker to only allow dates from today onwards
        dateChoice.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        // Disable past dates
                        if (date.isBefore(today)) {
                            setDisable(true);
                            setStyle("-fx-background-color: lightgray;");
                        }
                    }
                };
            }
        });
    }

    private void onHandBook(){
        Reader reader = readerChoice.getValue();
        Book book = bookChoice.getValue();
        String date = dateChoice.getValue().toString();

        if (readerChoice.getItems().isEmpty()) {
            AlertUtility.displayInformation("Sistemoje nėra skaitytojų");
        }else if (bookChoice.getItems().isEmpty()) {
            AlertUtility.displayInformation("Sistemoje nėra knygų");
        } else{
            //Hand out the book

            if (readerChoice == null || bookChoice == null || dateChoice == null) {
                AlertUtility.displayInformation("Užpildykite visus laukus");
            }
            else {
                Model.getInstance().createHandedBook(book, reader, date);
                emptyFields();
            }
        }
    }

    private void emptyFields(){
        dateChoice.setValue(null);
        readerChoice.setValue(null);
        bookChoice.setValue(null);
    }
}


