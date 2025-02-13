package com.example.pamokafx2.Controllers;

import com.example.pamokafx2.Models.Model;
import com.example.pamokafx2.Utilities.AlertUtility;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField password_field;
    @FXML
    public Hyperlink registerLink;
    @FXML
    public Button login_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        registerLink.setOnAction(actionEvent -> onRegister());
        login_btn.setOnAction(actionEvent -> onLogin());
    }
    /**
     * Handle login action
     */
    public void onLogin(){
        Stage stage = (Stage) registerLink.getScene().getWindow();
//        Model.getInstance().getViewFactory().showMainWindow();
        //Check cred
        Model.getInstance().checkCredentials(usernameField.getText(), password_field.getText());

        //If login success, open dashboard
        if(Model.getInstance().getLoginSuccessFlag()){
            Model.getInstance().getViewFactory().showMainWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
        }else{
            usernameField.setText("");
            password_field.setText("");
            AlertUtility.displayError("Neteisingi prisijungimo duomenys");
        }

        Model.getInstance().getViewFactory().closeStage(stage);
    }

    /**
    * Handle register action
    */
    public void onRegister(){
        Stage stage = (Stage) registerLink.getScene().getWindow();
        Model.getInstance().getViewFactory().showRegisterWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
    }
}
