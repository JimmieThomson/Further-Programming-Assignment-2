package com.example.assignment2furtherprogramming;

import com.example.assignment2furtherprogramming.classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;

public class createGUIController {

    @FXML
    private Text Error;
    @FXML
    private TextField password;
    @FXML
    private TextField username;

    private ArrayList<User> users = new ArrayList<>();

    public createGUIController(){
        updateUsers();
    }

    @FXML
    public void Signup() {
        Error.setVisible(false);
        //Checks to see if the user has typed anything within the boxes
        if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
            if (verifyUserNameUnique(username.getText())) {
                //Verifies if a username is or is not unique
                User newUser = new User(username.getText(), password.getText());
                addUser(newUser);
            } else {
                Error.setText("Username Exists");
                Error.setVisible(true);
            }
        } else {
            Error.setText("Username or Password left empty");
            Error.setVisible(true);
        }
    }

    @FXML
    public void Login() {
        Error.setVisible(false);
        updateUsers();
        for (User user : users) {
            if(username.getText().equalsIgnoreCase(user.getUsername()) && password.getText().equals(user.getPassword())){
                Error.setText("Information Correct!");
                Error.setVisible(true);
                return;
            }
        }
        Error.setText("Username or Password not Correct");
        Error.setVisible(true);
    }

    public Boolean verifyUserNameUnique(String username) {
        updateUsers();
        for (User user : users) {
            if (username.equalsIgnoreCase(user.getUsername())) {
                return false;
            }
        }
        return true;
    }

    public void updateUsers() {
        try {
            ObjectInputStream users = new ObjectInputStream(new FileInputStream("/Users/jamesthomson/IdeaProjects/Assignment-2-Further-Programming/src/main/java/com/example/assignment2furtherprogramming/users.bin"));
            this.users = (ArrayList<User>) users.readObject();
            users.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.printf("%s:\t%s\n", e , "updateUsers()");
        }
    }

    public void addUser(User user){
        updateUsers();
        this.users.add(user);

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("/Users/jamesthomson/IdeaProjects/Assignment-2-Further-Programming/src/main/java/com/example/assignment2furtherprogramming/users.bin"));
            out.writeObject(this.users);
            out.close();
        } catch(IOException e){
            System.err.printf("%s:\t%s\n", e , "addUser()");
        }
    }
}
