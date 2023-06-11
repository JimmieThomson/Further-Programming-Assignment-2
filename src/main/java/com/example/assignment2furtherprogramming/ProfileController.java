package com.example.assignment2furtherprogramming;

import com.example.assignment2furtherprogramming.classes.User;
import com.example.assignment2furtherprogramming.classes.extractClasses;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Window;

import java.io.*;
import java.util.*;

public class ProfileController{
    @FXML
    private GridPane timeTables;
    @FXML
    private Text Errors;
    @FXML
    private Text InfoWelcome;
    @FXML
    private Button downloadButton;
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchBox;
    @FXML
    private Button EnrolButton;
    @FXML
    private Button RemoveCourses;
    @FXML
    private ListView<String> CoursesList;
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
    private User currentUser;
    private ArrayList<User> users;
    private HashMap<String, String> csvContent;

    /*
     *  Author: James Thomson
     *
     *  Description: This stage has the main bulk of functions that allow the user to update information
     *  regarding their username, passwords and other things. It also shows information like their profiles
     *  and allows the user to enroll in courses, drop courses and see their courses
     *
     * */

    //Initializes the variables
    public ProfileController(){
        this.users = new ArrayList<>();
        this.csvContent = new HashMap<>();
        extractClasses csv = new extractClasses();
        csvContent = csv.getCsvContent();
        csvContent.remove("Course name");
    }

    //This is the passing function seen in LoginController when starting this stage
    //passes the User over
    public void Start(User currentUser){
        this.currentUser = currentUser;
        startProfile();
        Errors.setVisible(false);
    }

    @FXML
    //extractClasses function is a function used to extract the course.csv classes and place them in a selectable list named CoursesList
    public void extractClasses(){
        //Settings visibility on the buttons and search boxes
        CoursesList.getItems().clear();
        RemoveCourses.setVisible(false);
        searchBox.setVisible(true);
        searchButton.setVisible(true);
        EnrolButton.setVisible(true);
        downloadButton.setVisible(false);
        ArrayList<String> classes = new ArrayList<>();

        //Extracts the hash map and places every one in the list here
        for(Map.Entry<String, String> items: csvContent.entrySet()){
            String[] slicedItems = items.getValue().split(",");
            if(!slicedItems[0].equalsIgnoreCase("N/A")) {
                classes.add(String.format("%s  %s %s %s %s %s %s", items.getKey(), slicedItems[0], slicedItems[1], slicedItems[2], slicedItems[3], slicedItems[4], slicedItems[5]));
            }
        }
        CoursesList.getItems().addAll(classes);
    }

    @FXML
    //The searchBox item / button starts this function and searches uses assignment 1's search algorithm
    public void SearchClasses(){
        CoursesList.getItems().clear();
        HashMap<String, String> phrases = new HashMap<>();
        //Looping through each Hash
        for (String CourseNames : csvContent.keySet()) {
            //Separating words from the phrase using \\s splitter and placing it into a separate array
            String[] words = CourseNames.split("\\s+"); // splits by whitespace
            for (String word : words) {
                //Checking if words match them placing them into a separate dynamic array Phrases
                if (word.equalsIgnoreCase(searchBox.getText())) {
                    phrases.put(CourseNames, csvContent.get(CourseNames));
                    break;
                }
            }
        }

        //Replaces the CourseList to the new searched items
        ArrayList<String> classes = new ArrayList<>();
        for(Map.Entry<String, String> items: phrases.entrySet()){
            String[] slicedItems = items.getValue().split(",");
            classes.add(String.format("%s  %s %s %s %s %s %s", items.getKey(), slicedItems[0],slicedItems[1],slicedItems[2],slicedItems[3],slicedItems[4],slicedItems[5]));
        }
        //Calls the original function extract classes if the searchBox is empty
        if(!classes.isEmpty()) {
            CoursesList.getItems().addAll(classes);
        }else{
            extractClasses();
        }
    }

    @FXML
    //All enrolled courses get put into a csv folder called CoursesDownloaded.csv
    public void downloadCourses() throws IOException {
        File courses = new File("CoursesDownloaded.csv");
        courses.createNewFile();
        FileWriter myWriter = new FileWriter("CoursesDownloaded.csv");
        myWriter.write("Course name,Capacity,Year,Delivery mode,Day of lecture,Time of lecture,Duration of lecture (hour)");
        //Using the currentUser enrolled courses it loops through and places them into the file :)
        for(Map.Entry<String, String> items: currentUser.getEnrolledCourses().entrySet()) {
            myWriter.write(String.format("%s, $%s\n", items.getKey(), items.getValue()));
        }
        myWriter.close();
    }


    @FXML
    //Extracts all enrolled courses chosen by the Current User
    public void extractEnrol(){
        //Setting visability
        CoursesList.getItems().clear();
        RemoveCourses.setVisible(true);
        searchBox.setVisible(false);
        searchButton.setVisible(false);
        EnrolButton.setVisible(false);
        downloadButton.setVisible(true);

        //Loops through and adds to an array named enrolledClasses and one completed
        //the array gets places into the CourseList
        ArrayList<String> enrolledClasses = new ArrayList<>();
        try {
            for (Map.Entry<String, String> items : currentUser.getEnrolledCourses().entrySet()) {
                String[] slicedItems = items.getValue().split(",");
                enrolledClasses.add(String.format("%s  %s %s %s %s %s %s", items.getKey(), slicedItems[0], slicedItems[1], slicedItems[2], slicedItems[3], slicedItems[4], slicedItems[5]));
            }
            CoursesList.getItems().addAll(enrolledClasses);
            setUpTableView();
        }catch(NullPointerException ignored){
        }
    }

    @FXML
    //If a user selects and course using the list and follows up by pressing the enroll button
    //This function is called which adds the relevant information to the User class currentUser
    public void addEnrolSelected(){
        //Grabs the selected it
        System.out.println(CoursesList.getSelectionModel().getSelectedItem());
        int hashIndex = 0;

        //Using the hashIndex to find the index of the Hash table
        for(String course : csvContent.keySet()){
            //Adds it to the table if the Hash index and the selected item in the list match
            if (hashIndex == CoursesList.getSelectionModel().getSelectedIndex()){
                currentUser.addCourse(course, csvContent.get(course));
                updateBin(currentUser.getUsername());
                setUpTableView();
                break;
            }
            hashIndex++;
        }
    }

    //Sets up all the information when the function is called into the edit text boxes
    private void startProfile(){
        Username.setText(currentUser.getUsername());
        Password.setText(currentUser.getPassword());
        StudentID.setText(currentUser.getStudentNumber());
        FirstName.setText(currentUser.getFirstName());
        LastName.setText(currentUser.getLastName());
        //Welcome Screen
        InfoWelcome.setText(String.format("%s %s: %s", FirstName.getText(), LastName.getText(), StudentID.getText()));
        extractClasses();
        setUpTableView();
    }

    @FXML
    //When items have changed by the user this function is called which updates all of it to the bin file
    private void saveDetails(){
        String oldUsername = currentUser.getUsername();

        //Checks to see if the username is unique
        if(checkInfoValid(Username.getText()) && !Username.getText().isEmpty()){
            currentUser.setFirstName(FirstName.getText());
            currentUser.setLastName(LastName.getText());
            currentUser.setUsername(Username.getText());
            currentUser.setStudentNumber(StudentID.getText());
            System.out.println(currentUser.getUsername() + "\t" + currentUser.getStudentNumber());
            startProfile();

            //Update the bin file
            updateBin(oldUsername);
        }else{
            //Sets the visibility to true, changes the colour to red and displays error message
            Errors.setVisible(true); Errors.setFill(Paint.valueOf("#ff0000")); Errors.setText("Student ID or Username Already exists or Username Empty");
        }
    }

    @FXML
    //Exists the stage
    private void exit(ActionEvent event){
        Node source = (Node) event.getSource();
        Window eventStage = source.getScene().getWindow();
        eventStage.hide();
    }


    //Updates the bin of all the users inside the bin file by removing the old one
    //and adding the new!
    private void updateBin(String oldUserName){
        updateUsers();

        int userCount = 0;

        //For loop to add the new user and remove the old one using the users array list
        for(User user: this.users){
            if(oldUserName.equalsIgnoreCase(user.getUsername())){
                this.users.remove(userCount);

                this.users.add(currentUser);
                break;
            }
            userCount++;
        }

        //Places the array list into the bin file by using an ObjectOutputStream
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/main/java/com/example/assignment2furtherprogramming/users.bin"));
            out.writeObject(this.users);
            out.close();
            Errors.setVisible(true); Errors.setFill(Paint.valueOf("#00ff0d")); Errors.setText("Success");
        } catch(IOException e){
            System.err.printf("%s:\t%s\n", e , "addUser()");
        }
    }

    //Suppresses the user read warning
    @SuppressWarnings("unchecked")
    //function used to update the users from the bin into readable computer code that is stores in users array list
    private void updateUsers() {
        try {
            ObjectInputStream users = new ObjectInputStream(new FileInputStream("src/main/java/com/example/assignment2furtherprogramming/users.bin"));
            this.users = (ArrayList<User>) users.readObject();
            users.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.printf("%s:\t%s\n", e , "updateUsers()");
        }
    }

    //Function being used to find if the information changed by the user does not conflict with other user's usernames
    private Boolean checkInfoValid(String username) {
        updateUsers();
        try{
            if(!currentUser.getUsername().equalsIgnoreCase(username)) {
                //Looping through every username to see if they match the given username
                for (User user : users) {
                    if (user.getUsername().equalsIgnoreCase(username)) {
                        return false;
                    }
                }
            }
        }catch(NullPointerException e) {
            return false;
        }
        return true;
    }

    @FXML
    //If a user chooses to remove a course this function will update the users interest and re-display it's new courses
    public void removeEnrolSelected() {
        System.out.println(CoursesList.getSelectionModel().getSelectedItem());
        int hashIndex = 0;

        //removing the list and updating the new list
        for(String course : currentUser.getEnrolledCourses().keySet()){
            if (hashIndex == CoursesList.getSelectionModel().getSelectedIndex()){
                currentUser.removeCourse(course);
                updateBin(currentUser.getUsername());
                extractEnrol();
                break;
            }
            hashIndex++;
        }
    }
    //Sets up the timetable per class and updates each change in the enrollment
    private void setUpTableView(){
        //Clears the grid
        timeTables.getChildren().clear();
        //Each class that is in the enrollment wil be looped and added here
        for (Map.Entry<String, String> course : currentUser.getEnrolledCourses().entrySet()) {
            //Formatting the data into doubles and integers for start time and duration
            String[] details = course.getValue().split(",");
            String[] time = details[4].split(":");
            int timeStarts = Integer.parseInt(time[0]) - 8;
            double durationDouble = Double.parseDouble(details[5]);
            int duration = (int) Math.round(durationDouble - 0.5);

            //Using labels to show the class name
            Label label = new Label();
            label.setText(course.getKey());
            //This is for the css class, it puts the padding greater if the class is longer than 2 hours
            if(duration >= 2) {
                label.getStyleClass().add("gridPanecolourDouble");
            }else{
                label.getStyleClass().add("gridPanecolourSingle");
            }

            //Assigns a grid pane depending on the day and time - 8
            if(details[3].equalsIgnoreCase("monday")){
                timeTables.add(label, 0, timeStarts, 1, duration);
            }else if(details[3].equalsIgnoreCase("tuesday")){
                timeTables.add(label, 1, timeStarts, 1, duration);
            }else if(details[3].equalsIgnoreCase("wednesday")){
                timeTables.add(label, 2, timeStarts, 1, duration);
            }else if(details[3].equalsIgnoreCase("thursday")){
                timeTables.add(label, 3, timeStarts, 1, duration);
            }else if(details[3].equalsIgnoreCase("friday")){
                timeTables.add(label, 4, timeStarts, 1, duration);
            }
        }
    }
}
