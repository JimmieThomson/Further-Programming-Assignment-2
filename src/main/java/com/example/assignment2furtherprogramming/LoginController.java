package com.example.assignment2furtherprogramming;

import com.example.assignment2furtherprogramming.classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/*
 *  Author: James Thomson
 *
 *  Description: This class controls all the FXML Login.fxml files functions
 *  It has two stages that it can call, the Profile.fxml stage and the SignUp.fxml stage
 *  This stage will close once the user has signed in
 *
 * */

public class LoginController {

    @FXML
    private Text Error;
    @FXML
    private TextField password;
    @FXML
    private TextField username;

    private ArrayList<User> users = new ArrayList<>();

    //When the stage is first called it will update the users inside the user.bin file
    public LoginController(){
        updateUsers();
    }

    //When the signup button is pressed on the main stage it'll call the SignUp.fxml here and allow the user to sign up
    @FXML
    public void Signup() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignUp.fxml")));

        Scene scene = new Scene(root);

        stage.setTitle("Sign up");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    //When the user has pressed the Login button the stage will verify that the user exists and the passwords match
    //Then it'll call the Profile.fxml stage and pass over the given user information using the stage controller
    @FXML
    public void Login(ActionEvent actionEvent) throws IOException {
        Error.setVisible(false);
        updateUsers();
        //Looping to see if the user exists and match the given parameters
        for (User user : users) {
            if(username.getText().equalsIgnoreCase(user.getUsername()) && password.getText().equals(user.getPassword())){
                //Error is a short a term for debugging and letting the users know what is going on
                Error.setText("Information Correct!");
                Error.setVisible(true);
                Node source = (Node) actionEvent.getSource();
                Window theStage = source.getScene().getWindow();

                //Hiding this stage
                theStage.hide();

                //Loading the next stage
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));

                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setScene(new Scene(loader.load()));
                stage.setResizable(false);

                ProfileController controller = loader.getController();
                //Passing the user parameters to the next controller class named ProfileController
                controller.Start(user);

                stage.show();
                return;
            }
        }
        //else statement if the information is incorrect
        Error.setText("Username or Password not Correct");
        Error.setVisible(true);
    }

    //This is to update all users in the given users.bin file
    public void updateUsers() {
        try {
            ObjectInputStream users = new ObjectInputStream(new FileInputStream("src/main/java/com/example/assignment2furtherprogramming/users.bin"));
            this.users = (ArrayList<User>) users.readObject();
            users.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.printf("%s:\t%s\n", e , "updateUsers()");
        }
    }
}
