package com.example.pamokafx2.Controllers;

import com.example.pamokafx2.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class RouteController implements Initializable {
    public BorderPane parent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().addListener((observable, oldValue, newValue) -> {
            switch (newValue){
                case AUTHORS:
                    parent.setCenter(Model.getInstance().getViewFactory().getAuthorsView());
                    break;
                case CREATE_AUTHOR:
                    parent.setCenter(Model.getInstance().getViewFactory().getCreateAuthorView());
                    break;
                case BOOKS:
                    parent.setCenter(Model.getInstance().getViewFactory().getBooksView());
                    break;
                case CREATE_BOOK:
                    parent.setCenter(Model.getInstance().getViewFactory().getCreateBookView());
                    break;
                case READERS:
                    parent.setCenter(Model.getInstance().getViewFactory().getReaderView());
                    break;
                case CREATE_READER:
                    parent.setCenter(Model.getInstance().getViewFactory().getCreateReaderView());
                    break;
                case ACCOUNTING:
                    parent.setCenter(Model.getInstance().getViewFactory().getAccountingView());
                    break;
                case HAND_BOOK:
                    parent.setCenter(Model.getInstance().getViewFactory().getHandBookView());
                    break;
                default:
                    parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
            }
        });
    }
}
