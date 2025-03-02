package com.example.pamokafx2.Controllers;

import com.example.pamokafx2.Models.Model;
import com.example.pamokafx2.Views.MenuItems;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    public Button logout_btn;
    @FXML
    public Text current_user_text;
    @FXML
    public Button authors_btn;
    @FXML
    public Button books_btn;
    @FXML
    public Button readers_btn;
    @FXML
    public Button accounting_btn;

    @Override
    public void initialize (URL location, ResourceBundle resources) {
        //Show logged user name in menu
        current_user_text.setText(Model.getInstance().getLoggedUserName());
        //Add listeners to the menu buttons
        addListeners();
    }

    private void addListeners(){
        logout_btn.setOnAction(event -> onLogout());
        authors_btn.setOnAction(event -> onAuthors());
        books_btn.setOnAction(event -> onBooks());
        readers_btn.setOnAction(event -> onReaders());
        accounting_btn.setOnAction(event -> onAccounting());
    }

    /**
     * Handle authors window
     */

    public void onAuthors(){
        //Navigate the author window
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuItems.AUTHORS);
    }

    /**
     * Handle books window
     */

    public void onBooks(){
        //Navigate the books window
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuItems.BOOKS);
    }

    /**
     * Handle readers window
     */

    public void onReaders(){
        //Navigate the readers window
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuItems.READERS);
    }

    /**
     * Handle accounting window
     */

    public void onAccounting(){
        //Navigate the readers window
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuItems.ACCOUNTING);
    }

    /**
     * Handle logout event
     */

    public void onLogout (){
        //Create stage
        Stage stage = (Stage) logout_btn.getScene().getWindow();
        //Close stage
        Model.getInstance().getViewFactory().closeStage(stage);
        //Show login window
        Model.getInstance().getViewFactory().showLoginWindow();
        //Set loginSuccessFlag to false
        Model.getInstance().setLoginSuccessFlag(false);
    }
}
