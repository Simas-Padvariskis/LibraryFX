package com.example.pamokafx2.Controllers;

import com.example.pamokafx2.Models.Model;
import com.example.pamokafx2.Utilities.AlertUtility;
import com.example.pamokafx2.Views.MenuItems;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateAuthorController implements Initializable {
    public TextField first_name;
    public TextField last_name;
    public TextField email;
    public TextField city;
    public Button create_author_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create_author_btn.setOnAction(e -> onAuthor());
    }

    private void onAuthor(){
        String firstName = first_name.getText();
        String lastName = last_name.getText();
        String Email = email.getText();
        String City = city.getText();

        //Create the author

        if (firstName.isEmpty() || lastName.isEmpty() || Email.isEmpty() ||City.isEmpty()) {
            AlertUtility.displayInformation("Užpildykite visus laukus");
        }
        else {
            Model.getInstance().createAuthor(firstName, lastName, Email, City);

            AlertUtility.displayInformation("Autorius sėkmingai sukurtas");
            emptyFields();
        }
    }

    private void emptyFields(){
        first_name.setText("");
        last_name.setText("");
        email.setText("");
        city.setText("");
    }
}
