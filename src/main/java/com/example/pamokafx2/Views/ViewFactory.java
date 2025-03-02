package com.example.pamokafx2.Views;

import com.example.pamokafx2.Controllers.RouteController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {
    private final ObjectProperty<MenuItems> userSelectedMenuItem;
    private AnchorPane dashboard;
    private AnchorPane authorsView;
    private AnchorPane createAuthorView;
    private AnchorPane booksView;
    private AnchorPane createBookView;
    private AnchorPane readerView;
    private AnchorPane createReaderView;
    private AnchorPane accountingView;
    private AnchorPane handBookView;

    public ViewFactory() {
        this.userSelectedMenuItem = new SimpleObjectProperty<>();
    }

    /*
    * Getter for user selected menu item
    * @return the Object property
     */

    public ObjectProperty<MenuItems> getUserSelectedMenuItem() {
        return userSelectedMenuItem;
    }

    /**
     * Show dashboard
     */
    public AnchorPane getDashboardView() {
        if(dashboard == null) {
            try {
                dashboard = new FXMLLoader(getClass().getResource("/Fxml/Dashboard.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return dashboard;
    }

    /**
     * Show login window
     */
    public void showLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }

    /*
     * Show register window
     */
    public void showRegisterWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Resgister.fxml"));
        createStage(loader);
    }

    /*
     * Show main window
     */
    public void showMainWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Main.fxml"));
        RouteController controller = new RouteController();
        loader.setController(controller);
        createStage(loader);

    }

    /**
     * Load and return authors view
     *
     * @return authorView
     */

    public AnchorPane getAuthorsView(){
        if(authorsView == null) {
            try {
                authorsView = new FXMLLoader(getClass().getResource("/Fxml/Authors.fxml")).load();
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return authorsView;
    }

    /**
     * Load and return createAuthor view
     *
     * @return createAuthorView
     */

    public AnchorPane getCreateAuthorView(){
        if(createAuthorView == null) {
            try {
                createAuthorView = new FXMLLoader(getClass().getResource("/Fxml/CreateAuthor.fxml")).load();
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return createAuthorView;
    }

    /**
     * Load and return books view
     *
     * @return booksView
     */

    public AnchorPane getBooksView(){
        if(booksView == null) {
            try {
                booksView = new FXMLLoader(getClass().getResource("/Fxml/Books.fxml")).load();
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return booksView;
    }

    /**
     * Load and return createBook view
     *
     * @return createBookView
     */

    public AnchorPane getCreateBookView(){
        if(createBookView == null) {
            try {
                createBookView = new FXMLLoader(getClass().getResource("/Fxml/CreateBook.fxml")).load();
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return createBookView;
    }

    /**
     * Load and return readers view
     *
     * @return readerView
     */

    public AnchorPane getReaderView(){
        if(readerView == null) {
            try {
                readerView = new FXMLLoader(getClass().getResource("/Fxml/Readers.fxml")).load();
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return readerView;
    }

    /**
     * Load and return createReader view
     *
     * @return createReaderView
     */

    public AnchorPane getCreateReaderView(){
        if(createReaderView == null) {
            try {
                createReaderView = new FXMLLoader(getClass().getResource("/Fxml/CreateReader.fxml")).load();
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return createReaderView;
    }

    /**
     * Load and return accounting view
     *
     * @return accountingView
     */

    public AnchorPane getAccountingView(){
        if(accountingView == null) {
            try {
                accountingView = new FXMLLoader(getClass().getResource("/Fxml/Accounting.fxml")).load();
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return accountingView;
    }

    /**
     * Load and return handBook view
     *
     * @return handBookView
     */

    public AnchorPane getHandBookView(){
        if(handBookView == null) {
            try {
                handBookView = new FXMLLoader(getClass().getResource("/Fxml/HandBook.fxml")).load();
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return handBookView;
    }

    /*
     * Create and display new stage
     * @param loader the FXML loader instance. Load fxml file and create scene
     */
    public void createStage(FXMLLoader loader){
        Scene scene = null;
        try{
            scene = new Scene(loader.load());
        }catch(Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("KITM knygynas");
        stage.show();
    }

    /*
     * Close provided stage
     * @param stage to close
     */
    public void closeStage(Stage stage){
        stage.close();
    }
}

