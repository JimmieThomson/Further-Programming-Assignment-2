package com.example.assignment2furtherprogramming;

import com.example.assignment2furtherprogramming.classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Window;

import java.io.*;
import java.util.ArrayList;

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

    public SignUpController() {
        this.users = new ArrayList<>();
    }

    //Checks for if the user's username does not conflict with any in user.bin
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
                Error.setVisible(true);
                Error.setText("Student ID or Username already exist!");
            }
        } else {
            Error.setText("Username or Password left empty");
            Error.setVisible(true);
        }
    }

    //This is a boolean function which returns if the username or the studentID matches any other in the users file
    public Boolean checkInfoValid(String username, String studentID) {
        updateUsers();
        try {
            if (!users.isEmpty()) {
                //Looping through every user it knows
                for (User user : users) {
                    //True if it matches
                    if (user.getUsername().equalsIgnoreCase(username) || user.getStudentNumber().equalsIgnoreCase(studentID)) {
                        return false;
                    }
                }
            }
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    //Suppresses the user read warning
    @SuppressWarnings("unchecked")
    //Puts all users in the bin file into readable code
    public void updateUsers() {
        try {
            ObjectInputStream users = new ObjectInputStream(new FileInputStream("src/main/java/com/example/assignment2furtherprogramming/users.bin"));
            this.users = (ArrayList<User>) users.readObject();
            users.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.printf("%s:\t%s\n", e, "updateUsers()");
        }
    }

    //Adding and user to the user.bin file
    public void addUser() {
        updateUsers();
        this.users.add(this.newUser);

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/main/java/com/example/assignment2furtherprogramming/users.bin"));
            out.writeObject(this.users);
            out.close();
            eventStage.hide();
        } catch (IOException e) {
            System.err.printf("%s:\t%s\n", e, "addUser()");
        }
    }

    //If the button is clicked it closes the stage completely
    public void closeEvent(ActionEvent close){
        Node source = (Node) close.getSource();
        Window stage = source.getScene().getWindow();
        stage.hide();
    }
}