package com.example.pamokafx2.Controllers;

import com.example.pamokafx2.Models.Model;
import com.example.pamokafx2.Models.Reader;
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

public class ReadersController implements Initializable {
    public Button add_reader;
    public TableView readers_table;
    public TableColumn col_id;
    public TableColumn col_lastname;
    public MenuItem remove_reader;
    public TableColumn col_firstname;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        add_reader.setOnAction(e -> onCreateReader());
        initTableColumns();
        loadReaderData();
        remove_reader.setOnAction(event -> onRemoveReader());
        setRowFactoryForReadersTable();
    }

    /**
     * Open createReaderWindow
     */

    public void onCreateReader(){
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuItems.CREATE_READER);
    }

    /*
     * Init the table columns with Reader model
     */

    private void initTableColumns(){
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_firstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_lastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    }

    /**
     * Load Readers data into table
     */

    private void loadReaderData(){
        ObservableList<Reader> readers = Model.getInstance().getReaders();
        readers_table.setItems(readers);
    }

    /**
     * Handle reader remove
     */

    private void onRemoveReader(){
        Reader selectedReader = (Reader) readers_table.getSelectionModel().getSelectedItem();
        if (selectedReader == null) {
            AlertUtility.displayError("Pasirinkite skaitytoją");
        }

        boolean confirmed = AlertUtility.displayConfirmation(
                "Ar tikrai norite pašalinti skaitytoją? "
        );
        if (confirmed) {
            Model.getInstance().deleteReader(selectedReader.getId());
            ObservableList<Reader> readers = readers_table.getItems();
            readers.remove(selectedReader);
            AlertUtility.displayInformation("Skaitytojas pašalintas sėkmingai");
        }
    }

    /**
     * Opens dialog for editing reader
     * @param reader
     */

    private void editReader(Reader reader){
        Optional<Reader> result = DialogUtility.showEditReaderDialog(reader);
        result.ifPresent(updateReader ->{
            Model.getInstance().updateReader(updateReader);
            loadReaderData();
        });
    }

    /**
     * Sets row factory for the reader table
     */

    private void setRowFactoryForReadersTable(){
        readers_table.setRowFactory(tv ->{
            TableRow<Reader> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    Reader selectedReader = row.getItem();
                    editReader(selectedReader);
                }
            });
            return row;
        });
    }

}
