package com.example.assignment2furtherprogramming;

import com.example.assignment2furtherprogramming.classes.User;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.jar.Attributes;

public class ProfileController extends createGui{
    @FXML
    private TextField Username;
    @FXML
    private TextField Password;
    @FXML
    private TextField StudentID;
    @FXML
    private TextField FirstName;
    @FXML
    private TextField LastName;
    @FXML
    private Button SaveButton;
    @FXML
    private Label nameField;
    @FXML
    private ImageView Avatar;
    private User currentUser;
    public ProfileController(User currentUser){
        this.currentUser = currentUser;
    }
    public void setUpNames(){
        nameField.setText(currentUser.getFirstName() );
    }
    public void setUserProfile(User currentUser){
        this.currentUser = currentUser;
    }
    public void startProfile(){
        Username.setText(currentUser.getUsername());
        Password.setText(currentUser.getPassword());
        StudentID.setText(currentUser.getStudentNumber());
        FirstName.setText(currentUser.getFirstName());
        LastName.setText(currentUser.getLastName());
    }
}
