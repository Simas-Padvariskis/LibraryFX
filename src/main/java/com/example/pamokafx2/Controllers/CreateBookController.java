package com.example.pamokafx2.Controllers;

import com.example.pamokafx2.Models.Model;
import com.example.pamokafx2.Utilities.AlertUtility;
import com.example.pamokafx2.Views.ViewFactory;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateBookController implements Initializable {
    public TextField name_field;
    public TextField year_field;
    public Button create_book_btn;
    public ChoiceBox <String> authorChoice;
    public TextField isbn_field;
    public TextField category_field;
    public TextField description_field;
    public TextField page_number_field;
    public TextField price_field;
    public TextField rezervation_field;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        authorChoice.getItems().addAll(Model.getInstance().getAuthorLastNames());
        create_book_btn.setOnAction(e -> onBook());
    }

    private void onBook(){
        String isbn = isbn_field.getText();
        String name = name_field.getText();
        String category = category_field.getText();
        String description = description_field.getText();
        String pageNumber = page_number_field.getText();
        String year = year_field.getText();
        String price = price_field.getText();
        String author = authorChoice.getValue();
        String rezervation = rezervation_field.getText();

        if (authorChoice.getItems().isEmpty()) {
            AlertUtility.displayInformation("Sistemoje nėra autorių");
        }else {
            //Create the book

            if (isbn.isEmpty() || name.isEmpty() || category.isEmpty() || description.isEmpty() || pageNumber.isEmpty() || year.isEmpty() || price.isEmpty() || author == null || rezervation.isEmpty()) {
                AlertUtility.displayInformation("Užpildykite visus laukus");
            }
            else {
                Model.getInstance().createBook(isbn, name, category, description, pageNumber, year, price, author, rezervation);
                AlertUtility.displayInformation("Knyga sėkmingai sukurta");
                emptyFields();
            }
        }
    }


    private void emptyFields(){
        name_field.setText("");
        authorChoice.setValue("");
        year_field.setText("");
    }
}
