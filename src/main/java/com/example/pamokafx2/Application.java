package com.example.pamokafx2;

import com.example.pamokafx2.Models.Model;
import com.example.pamokafx2.Utilities.AlertUtility;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) {
        if(Model.getInstance().hasRegisteredUsers()){
            Model.getInstance().getViewFactory().showLoginWindow();
        }else{
            AlertUtility.displayInformation("Prieš pradedant darbą su sitema turite registruoti vartotoją");
            Model.getInstance().getViewFactory().showRegisterWindow();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}