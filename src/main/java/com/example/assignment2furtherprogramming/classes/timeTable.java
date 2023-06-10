package com.example.assignment2furtherprogramming.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class timeTable {
    private HashMap<String, Double> Monday;
    private HashMap<String, Double> Tuesday;
    private HashMap<String, Double> Wednesday;
    private HashMap<String, Double> Thursday;
    private HashMap<String, Double> Friday;

    public timeTable(){
        this.Monday = new HashMap<>();
        this.Tuesday = new HashMap<>();
        this.Wednesday = new HashMap<>();
        this.Thursday = new HashMap<>();
        this.Friday = new HashMap<>();
    }

    public void clearAll(){
        Monday.clear();
        Tuesday.clear();
        Wednesday.clear();
        Thursday.clear();
        Friday.clear();
    }

    public String[] getMondayKey() {
        return Monday.keySet().toArray(new String[0]);
    }
    public String[] getTuesdayKey() {
        return Tuesday.keySet().toArray(new String[0]);
    }
    public String[] getWednesdayKey() {
        return Wednesday.keySet().toArray(new String[0]);
    }
    public String[] getThursdayKey() {
        return Thursday.keySet().toArray(new String[0]);
    }
    public String[] getFridayKey() {
        return Friday.keySet().toArray(new String[0]);
    }

    public void updateTimes(HashMap<String, String> enrolledCourses){
        for (Map.Entry<String, String> enrollments : enrolledCourses.entrySet()){
            String[] details = enrollments.getValue().split(",");
            String[] times = details[4].split(":");
            double time = Double.parseDouble(times[1]) + (Double.parseDouble(times[1]) / 60);
            if (details[3].equalsIgnoreCase("Monday")) {
                this.Monday.put(enrollments.getKey(),time);
            } else if (details[3].equalsIgnoreCase("Tuesday")) {
                this.Tuesday.put(enrollments.getKey(), time);
            } else if (details[3].equalsIgnoreCase("Wednesday")) {
                this.Wednesday.put(enrollments.getKey(), time);
            } else if (details[3].equalsIgnoreCase("Thursday")) {
                this.Thursday.put(enrollments.getKey(), time);
            } else if (details[3].equalsIgnoreCase("Friday")) {
                this.Friday.put(enrollments.getKey(), time);
            }
        }
    }
}
