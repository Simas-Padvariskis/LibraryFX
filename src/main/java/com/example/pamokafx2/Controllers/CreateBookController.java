package com.example.pamokafx2.Controllers;

import com.example.pamokafx2.Models.Model;
import com.example.pamokafx2.Utilities.AlertUtility;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        authorChoice.getItems().addAll(Model.getInstance().getAuthorLastNames());
        create_book_btn.setOnAction(e -> onBook());
    }

    private void onBook(){
        String name = name_field.getText();
        String author = authorChoice.getValue();
        String year = year_field.getText();

        //Create the book

        Model.getInstance().createBook(name, author, year);

        AlertUtility.displayInformation("Knyga sėkmingai sukurta");
        emptyFields();
    }

    private void emptyFields(){
        name_field.setText("");
        authorChoice.setValue("");
        year_field.setText("");
    }
}
