package com.example.pamokafx2.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private int id;
    private StringProperty userName;

    /**
     * Create user
     * @param id
     * @param userName
     */
    public User(int id, String userName){
        this.id = id;
        this.userName = new SimpleStringProperty(userName);
    }

    /**
     * Return userName
     * @return userName
     */
    public String userNameProperty(){
        return userName.get();
    }

    /**
     * Return id
     * @return id
     */
    public int getId(){
        return id;
    }
}
