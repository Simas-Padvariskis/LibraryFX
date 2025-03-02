package com.example.pamokafx2.Controllers;

import com.example.pamokafx2.Models.Model;
import com.example.pamokafx2.Utilities.AlertUtility;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateReaderController implements Initializable {

    public TextField first_name_field;
    public TextField last_name_field;
    public Button create_reader_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create_reader_btn.setOnAction(e -> onReader());
    }

    private void onReader(){
        String firstName = first_name_field.getText();
        String lastName = last_name_field.getText();

        //Create the reader

        if (firstName.isEmpty() || lastName.isEmpty()) {
            AlertUtility.displayInformation("Užpildykite visus laukus");
        }
        else {
            Model.getInstance().createReader(firstName, lastName);

            AlertUtility.displayInformation("Skaitytojas sėkmingai sukurtas");
            emptyFields();
        }
    }

    private void emptyFields(){
        first_name_field.setText("");
        last_name_field.setText("");
    }
}
