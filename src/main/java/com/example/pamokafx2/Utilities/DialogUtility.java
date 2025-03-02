package com.example.pamokafx2.Utilities;

import com.example.pamokafx2.Models.Author;
import com.example.pamokafx2.Models.Book;
import com.example.pamokafx2.Models.Model;
import com.example.pamokafx2.Models.Reader;
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

        TextField isbnField = new TextField(book.getIsbn());
        TextField nameField = new TextField(book.getName());
        TextField categoryField = new TextField(book.getCategory());
        TextField descriptionField = new TextField(book.getDescription());
        TextField pageNumberField = new TextField(book.getPage_number());
        TextField yearField = new TextField(book.getYear());
        TextField priceField = new TextField(book.getPrice());
        ChoiceBox<String> authorField = new ChoiceBox<String>();
        authorField.setValue(book.getAuthor());
        authorField.getItems().addAll(Model.getInstance().getAuthorLastNames());
        TextField reservedField = new TextField(book.getReserved());

        grid.add(new Label("ISBN: "), 0, 0);
        grid.add(isbnField, 1, 0);
        grid.add(new Label("Pavadinimas: "), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Kategorija: "), 0, 2);
        grid.add(categoryField, 1, 2);
        grid.add(new Label("Aprašymas: "), 0, 3);
        grid.add(descriptionField, 1, 3);
        grid.add(new Label("Puslapiai: "), 0, 4);
        grid.add(pageNumberField, 1, 4);
        grid.add(new Label("Metai: "), 0, 5);
        grid.add(yearField, 1, 5);
        grid.add(new Label("Kaina: "), 0, 6);
        grid.add(priceField, 1, 6);
        grid.add(new Label("Autorius: "), 0, 7);
        grid.add(authorField, 1, 7);
        grid.add(new Label("Rezervazija: "), 0, 8);
        grid.add(reservedField, 1, 8);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton ->{
            if(dialogButton == saveButtonType){
                book.setIsbn(isbnField.getText().trim());
                book.setName(nameField.getText().trim());
                book.setCategory(categoryField.getText().trim());
                book.setDescription(descriptionField.getText().trim());
                book.setPage_number(pageNumberField.getText().trim());
                book.setYear(yearField.getText().trim());
                book.setPrice(priceField.getText().trim());
                book.setAuthor(authorField.getValue());
                book.setReserved(reservedField.getText());
            }
            return book;
        });

        return dialog.showAndWait();
    }

    /**
     * Displays dialog for editing reader information
     * @param reader - the Reader object for editing
     */

    public static Optional<Reader> showEditReaderDialog(Reader reader){
        Dialog<Reader> dialog = new Dialog();
        dialog.setTitle("Redaguoti skaitytoją");
        dialog.setHeaderText("Redaguokite pasirinkto skaitytojo duomenis");

        ButtonType saveButtonType = new ButtonType("Išsaugoti", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField firstNameField = new TextField(reader.getFirstName());
        TextField lastNameField = new TextField(reader.getLastName());

        grid.add(new Label("Vardas: "), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new Label("Pavarde: "), 0, 1);
        grid.add(lastNameField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton ->{
            if(dialogButton == saveButtonType){
                reader.setFirstName(firstNameField.getText().trim());
                reader.setLastName(lastNameField.getText().trim());
            }
            return reader;
        });

        return dialog.showAndWait();
    }
}
