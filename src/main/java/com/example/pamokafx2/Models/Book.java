package com.example.pamokafx2.Models;

import javafx.beans.property.*;

public class Book {
    private IntegerProperty id;
    private StringProperty isbn;
    private StringProperty name;
    private StringProperty category;
    private StringProperty description;
    private StringProperty page_number;
    private StringProperty year;
    private StringProperty price;
    private StringProperty author;

    /**
     * Book constructor
     *
     * @param id - Book ID
     * @param isbn - Book ISBN
     * @param name - Book name
     * @param category - Book category
     * @param description - Book description
     * @param pageNumber - Book page_number
     * @param year - Book release year
     * @param price - Book price
     * @param author - Book author
     */

    public Book(int id, String isbn, String name, String category, String description, String pageNumber, String year, String price, String author) {
        this.id = new SimpleIntegerProperty(id);
        this.isbn = new SimpleStringProperty(isbn);
        this.name = new SimpleStringProperty(name);
        this.category = new SimpleStringProperty(category);
        this.description = new SimpleStringProperty(description);
        this.page_number = new SimpleStringProperty(pageNumber);
        this.year = new SimpleStringProperty(year);
        this.price = new SimpleStringProperty(price);
        this.author = new SimpleStringProperty(author);
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

    public String getIsbn() {
        return isbn.get();
    }

    public StringProperty isbnProperty() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn.set(isbn);
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

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getPage_number() {
        return page_number.get();
    }

    public StringProperty page_numberProperty() {
        return page_number;
    }

    public void setPage_number(String page_number) {
        this.page_number.set(page_number);
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

    public String getPrice() {
        return price.get();
    }

    public StringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
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

    @Override
    public String toString() {
        return String.format(getName() + " - " + getAuthor());
    }
}
