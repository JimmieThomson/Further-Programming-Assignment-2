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
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    private String username;
    private final String password;
    private String firstName;
    private String lastName;
    private String studentNumber;
    private final HashMap<String, String> enrolledCourses;
    public User(String username, String password, String studentNumber, String firstName, String lastName){
        this.username = username;
        this.password = password;
        this.enrolledCourses = new HashMap<>();
        this.studentNumber = studentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }
    public HashMap<String,String> getEnrolledCourses(){return enrolledCourses;}

    public String getPassword() {
        return password;
    }
    public String getFirstName(){return firstName;}

    public String getLastName() {
        return lastName;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    //When a course is added it is added here
    public void addCourse(String courseName, String courseDetails){
        try {
            enrolledCourses.get(courseName).isEmpty();
        }catch (NullPointerException ignored){
            if(checkClashes(courseDetails)) {
                this.enrolledCourses.put(courseName, courseDetails);
            }
        }
    }
    public void removeCourse(String courseName){
        this.enrolledCourses.remove(courseName);
    }

    //Checks to see if the course selected will clash with any others by converting them into doubles and checking the time
    public boolean checkClashes(String courseDetails){
        //Splitting the data into arrays
        String[] currentClass = courseDetails.split(",");
        String[] currentClassTime = currentClass[4].split(":");

        //Looping through the HashMaps, splitting the hash maps and checking to see if there is a clash
        for(Map.Entry<String, String> classes : enrolledCourses.entrySet()){
            String[] otherClass = classes.getValue().split(",");
            String[] otherClassClassTime = currentClass[4].split(":");

            double classEnds = Double.parseDouble(currentClassTime[0]) + (Double.parseDouble(currentClassTime[1]) / 60) + Double.parseDouble(currentClass[5]);

            double nextClassStarts = Double.parseDouble(otherClassClassTime[0]) + (Double.parseDouble(otherClassClassTime[1]) / 60);

            //The only part of the code that actually checks to see if there is a clash :')
            if(currentClass[3].equalsIgnoreCase(otherClass[3]) && classEnds <= nextClassStarts){
                return false;
            }
        }
        return true;
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
