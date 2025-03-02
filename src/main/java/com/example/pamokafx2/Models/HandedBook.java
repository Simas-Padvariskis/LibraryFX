package com.example.pamokafx2.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HandedBook {
    private IntegerProperty id;
    private StringProperty isbn;
    private StringProperty name;
    private StringProperty category;
    private StringProperty author;
    private StringProperty reserved;
    private StringProperty returnDate;

    /**
     * handedBook constructor
     *
     * @param id - handedBook ID
     * @param isbn - handedBook ISBN
     * @param name - handedBook name
     * @param category - handedBook category
     * @param author - handedBook author
     * @param reserved - handedBook reservation status
     * @param returnDate - handedBook return date
     */

    public HandedBook(int id, String isbn, String name, String category, String author, String reserved, String returnDate) {
        this.id = new SimpleIntegerProperty(id);
        this.isbn = new SimpleStringProperty(isbn);
        this.name = new SimpleStringProperty(name);
        this.category = new SimpleStringProperty(category);
        this.author = new SimpleStringProperty(author);
        this.reserved = new SimpleStringProperty(reserved);
        this.returnDate = new SimpleStringProperty(returnDate);
    }

    public String getIsbn() {
        return isbn.get();
    }

    public StringProperty isbnProperty() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn.set(isbn);
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

    public String getCategory() {
        return category.get();
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
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

    public String getReserved() {
        return reserved.get();
    }

    public StringProperty reservedProperty() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved.set(reserved);
    }

    public String getReturnDate() {
        return returnDate.get();
    }

    public StringProperty returnDateProperty() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate.set(returnDate);
    }

    @Override
    public String toString() {
        return "HandedBook{" +
                "id=" + id +
                ", isbn=" + isbn +
                ", name=" + name +
                ", category=" + category +
                ", author=" + author +
                ", reserved=" + reserved +
                ", returnDate=" + returnDate +
                '}';
    }
}
