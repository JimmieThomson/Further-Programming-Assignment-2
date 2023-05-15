package com.example.assignment2furtherprogramming.classes;

/*
 *  Date Made: 15/5/2023
 *
 *  James Thomson
 *
 *  Description: Object class used for storing information regarding User info
 *
 * */

import java.io.Serializable;

public class User implements Serializable {
    private final String username;
    private final String password;
    private String firstName;
    private String lastName;
    private String studentNumber;
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
}
