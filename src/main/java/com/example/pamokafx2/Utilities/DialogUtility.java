package com.example.pamokafx2.Utilities;

import com.example.pamokafx2.Models.Author;
import com.example.pamokafx2.Models.Book;
import com.example.pamokafx2.Models.Model;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class DialogUtility {

    /**
     * Displays dialog for editing Author information
     * @param author - the Author object for editing
     */

    public static Optional<Author> showEditAuthorDialog(Author author){
        Dialog<Author> dialog = new Dialog();
        dialog.setTitle("Redaguoti autorių");
        dialog.setHeaderText("Redaguokite pasirinkto autoriaus duomenis");

        ButtonType saveButtonType = new ButtonType("Išsaugoti", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField firstNameField = new TextField(author.getFirstName());
        TextField lastNameField = new TextField(author.getLastName());
        TextField emailField = new TextField(author.getEmail());
        TextField cityField = new TextField(author.getCity());

        grid.add(new Label("Vardas: "), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new Label("Pavarde: "), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("El. pastas: "), 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(new Label("Miestas: "), 0, 3);
        grid.add(cityField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton ->{
            if(dialogButton == saveButtonType){
                author.setFirstName(firstNameField.getText().trim());
                author.setLastName(lastNameField.getText().trim());
                author.setEmail(emailField.getText().trim());
                author.setCity(cityField.getText().trim());
            }
            return author;
        });

        return dialog.showAndWait();
    }

    /**
     * Displays dialog for editing Book information
     * @param book - the Book object for editing
     */

    public static Optional<Book> showEditBookDialog(Book book){
        Dialog<Book> dialog = new Dialog();
        dialog.setTitle("Redaguoti knygą");
        dialog.setHeaderText("Redaguokite pasirinktos knygos duomenis");

        ButtonType saveButtonType = new ButtonType("Išsaugoti", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);


        TextField nameField = new TextField(book.getName());
        ChoiceBox<String> authorField = new ChoiceBox<String>();
        authorField.setValue(book.getAuthor());
        authorField.getItems().addAll(Model.getInstance().getAuthorLastNames());
        TextField yearField = new TextField(book.getYear());

        grid.add(new Label("Pavadinimas: "), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Autorius: "), 0, 1);
        grid.add(authorField, 1, 1);
        grid.add(new Label("Metai: "), 0, 2);
        grid.add(yearField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton ->{
            if(dialogButton == saveButtonType){
                book.setName(nameField.getText().trim());
                book.setAuthor(authorField.getValue());
                book.setYear(yearField.getText().trim());
            }
            return book;
        });

        return dialog.showAndWait();
    }
}
