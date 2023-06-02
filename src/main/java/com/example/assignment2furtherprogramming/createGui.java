package com.example.assignment2furtherprogramming;

import com.example.assignment2furtherprogramming.classes.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class createGui extends Application {
    private final Stage Profile = new Stage();
    private Stage mainStage = new Stage();
    private Stage loggedInStage = new Stage();

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));

        Scene scene = new Scene(root);

        mainStage.setTitle("Login");
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    public void showProfile(User user) throws IOException {
        //Finding the current scene to close it :)
        mainStage.close();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Profile.fxml")));

        Scene scene = new Scene(root);

        loggedInStage.setTitle("Account");
        loggedInStage.setScene(scene);
        loggedInStage.show();
    }
}