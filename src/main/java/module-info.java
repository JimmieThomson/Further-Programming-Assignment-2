module com.example.assignment2furtherprogramming {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.example.assignment2furtherprogramming.classes;
    opens com.example.assignment2furtherprogramming to javafx.fxml;
    exports com.example.assignment2furtherprogramming;
}