package com.example.pamokafx2.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty author;
    private StringProperty year;

    /**
     * Author constructor
     *
     * @param id - Book ID
     * @param name - Book name
     * @param author - Author
     * @param year - Year of release
     */

    public Book(int id, String name, String author, String year) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.author = new SimpleStringProperty(author);
        this.year = new SimpleStringProperty(year);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAuthor() {
        return author.get();
    }

    public StringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getYear() {
        return year.get();
    }

    public StringProperty yearProperty() {
        return year;
    }

    public void setYear(String year) {
        this.year.set(year);
    }

    @Override
    public String toString() {
        return String.format("Book [Name=%s, Author=%s, Year=%s]", getName(), getAuthor(), getYear());
    }
}
