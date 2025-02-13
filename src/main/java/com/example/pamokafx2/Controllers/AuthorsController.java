package com.example.pamokafx2.Controllers;

import com.example.pamokafx2.Models.Author;
import com.example.pamokafx2.Models.Model;
import com.example.pamokafx2.Utilities.AlertUtility;
import com.example.pamokafx2.Utilities.DialogUtility;
import com.example.pamokafx2.Views.MenuItems;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AuthorsController implements Initializable {
    public Button add_author;
    public TableView authors_table;
    public TableColumn col_id;
    public TableColumn col_first_name;
    public TableColumn col_last_name;
    public TableColumn col_email;
    public MenuItem remove_author;
    public TableColumn col_city;
    public TextField filterFirstName;
    public TextField filterLastName;
    public TextField filterCity;
    public Button filterButton;

    private FilteredList<Author> filteredAuthors;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        add_author.setOnAction(e -> onCreateAuthor());
        initTableColumns();
        loadAuthorData();
        remove_author.setOnAction(event -> onRemoveAuthor());
        setRowFactoryForAuthorsTable();

        //Data filtering
        filteredAuthors = new FilteredList<>(Model.getInstance().getAuthors());
        authors_table.setItems(filteredAuthors);
        filterButton.setOnAction(event -> applyFilters());
    }

    /**
     * Open createAuthorWindow
     */

    public void onCreateAuthor(){
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuItems.CREATE_AUTHOR);
    }

    /*
    * Init the table columns with Author model
     */

    private void initTableColumns(){
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_first_name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_last_name.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_city.setCellValueFactory(new PropertyValueFactory<>("city"));
    }

    /**
     * Load authors data into table
     */

    private void loadAuthorData(){
        ObservableList<Author> authors = Model.getInstance().getAuthors();
        authors_table.setItems(authors);
    }

    /**
     * Handle author remove
     */

    private void onRemoveAuthor(){
        Author selectedAuthor = (Author) authors_table.getSelectionModel().getSelectedItem();
        if (selectedAuthor == null) {
            AlertUtility.displayError("Pasirinkite autorių");
        }

        boolean confirmed = AlertUtility.displayConfirmation(
                "Ar tikrai norite pašalinti autorių? "
        );
        if (confirmed) {
            Model.getInstance().deleteAuthor(selectedAuthor.getId());
            ObservableList<Author> authors = authors_table.getItems();
            authors.remove(selectedAuthor);
            AlertUtility.displayInformation("Autorius pašalintas sėkmingai");
        }
    }

    /**
     * Sets row factory for the author table
     */

    private void setRowFactoryForAuthorsTable(){
        authors_table.setRowFactory(tv ->{
            TableRow<Author> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    Author selectedAuthor = row.getItem();
                    editAuthor(selectedAuthor);
                }
            });
            return row;
        });
    }

    /**
     * Opens dialog for editing author
     * @param author
     */

    private void editAuthor(Author author){
        Optional<Author> result = DialogUtility.showEditAuthorDialog(author);
        result.ifPresent(updateAuthor ->{
            Model.getInstance().updateAuthor(updateAuthor);
            loadAuthorData();
        });
    }

    /**
     * Authors data filter
     */

    private void applyFilters(){
        String firstNameFilter = filterFirstName.getText().toLowerCase();
        String lastNameFilter = filterLastName.getText().toLowerCase();
        String cityFilter = filterCity.getText().toLowerCase();

        filteredAuthors.setPredicate(author -> {
            if(!firstNameFilter.isEmpty() && !author.getFirstName().toLowerCase().contains(firstNameFilter)){
                return false;
            }

            if(!lastNameFilter.isEmpty() && !author.getLastName().toLowerCase().contains(lastNameFilter)){
                return false;
            }

            if(!cityFilter.isEmpty() && !author.getCity().toLowerCase().contains(cityFilter)){
                return false;
            }

            return true;
        });
    }
}
