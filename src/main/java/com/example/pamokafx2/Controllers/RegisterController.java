package com.example.pamokafx2.Controllers;

import com.example.pamokafx2.Models.Model;
import com.example.pamokafx2.Utilities.AlertUtility;
import com.example.pamokafx2.Utilities.UserUtility;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    public TextField usernameField;
    public PasswordField repeatPasswordField;
    public Button register_btn;
    public PasswordField password_field;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        register_btn.setOnAction(actionEvent -> onRegister());
    }

    public void onRegister(){
        Stage stage = (Stage) register_btn.getScene().getWindow();

        //Check if passwords match
        if (Model.getInstance().isUserExist(usernameField.getText())){
            AlertUtility.displayError("Vartotojas tokiu vardu sistemoje jau registruotas");
            emptyFields();
        }
        else if (!UserUtility.doPasswordsMatch(password_field.getText(), repeatPasswordField.getText())){
            AlertUtility.displayError("Nesutampa slaptažodžiai");
        } else {
            Model.getInstance().createUser(usernameField.getText(), password_field.getText());

            AlertUtility.displayInformation("Vartotojas sėkmingai sukurtas. Prašome prisijungti");

            Model.getInstance().getViewFactory().showLoginWindow();;

            Model.getInstance().getViewFactory().closeStage(stage);
        }
    }

    /**
     * Empty register fields
     */

    public void emptyFields(){
        usernameField.setText("");
        password_field.setText("");
        repeatPasswordField.setText("");
    }
}
