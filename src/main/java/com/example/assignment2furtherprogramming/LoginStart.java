package com.example.assignment2furtherprogramming;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
/*
 *
 *  Author: James Thomson
 *
 *  Description: Calling the first Stage class named login
 *
 *  Please note that all information regarding this assignment is published on GitHub
 *  https://github.com/JimmieThomson/Further-Programming-Assignment-2
 *
 * */
public class LoginStart extends Application {

    //Starting Function overrides the Application class given by java FX to call the Login.fxml
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));

        Scene scene = new Scene(root);

        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}