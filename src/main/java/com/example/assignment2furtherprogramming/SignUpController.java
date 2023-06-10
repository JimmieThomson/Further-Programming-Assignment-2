package com.example.assignment2furtherprogramming;

import com.example.assignment2furtherprogramming.classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class SignUpController {
    @FXML
    private Text Error;
    @FXML
    private TextField Lname;
    @FXML
    private TextField fName;
    @FXML
    private TextField studentID;
    @FXML
    private PasswordField password;
    @FXML
    private TextField username;
    private ArrayList<User> users;
    private User newUser;

    private Window eventStage;
    public SignUpController(){
        this.users = new ArrayList<>();
    }

    @FXML
    public void Signup(ActionEvent event) {
        //Checks to see if the user has typed anything within the boxes
        Node source = (Node) event.getSource();
        this.eventStage = source.getScene().getWindow();

        if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
            if (checkInfoValid(username.getText(), studentID.getText())) {
                //Verifies if a username is or is not unique
                this.newUser = new User(username.getText(), password.getText(), studentID.getText(), fName.getText(), Lname.getText());
                addUser();
            } else {
                Error.setVisible(true); Error.setText("Student ID or Username already exist!");
            }
        } else {
            Error.setText("Username or Password left empty");
            Error.setVisible(true);
        }
    }
    public Boolean checkInfoValid(String username, String studentID) {
        updateUsers();
        try{
            if(!users.isEmpty()) {
                for (User user : users) {
                    if(user.getUsername().equalsIgnoreCase(username) || user.getStudentNumber().equalsIgnoreCase(studentID)){
                        return false;
                    }
                }
            }
        }catch(NullPointerException e) {
            return false;
        }
        return true;
    }

    public void updateUsers() {
        try {
            ObjectInputStream users = new ObjectInputStream(new FileInputStream("src/main/java/com/example/assignment2furtherprogramming/users.bin"));
            this.users = (ArrayList<User>) users.readObject();
            users.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.printf("%s:\t%s\n", e , "updateUsers()");
        }
    }

    public void addUser(){
        updateUsers();
        this.users.add(this.newUser);

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/main/java/com/example/assignment2furtherprogramming/users.bin"));
            out.writeObject(this.users);
            out.close();
            close();
        } catch(IOException e){
            System.err.printf("%s:\t%s\n", e , "addUser()");
        }
    }

    public void close(){
        eventStage.hide();
    }
}
