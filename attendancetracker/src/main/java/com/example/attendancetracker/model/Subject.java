package com.example.attendancetracker.model;

import lombok.Data;

@Data // Lombok annotation for getters, setters, equals, hashCode, and toString
public class Subject {
    
    private String name;
    private int totalHours; // Total classes that can be held
    private int classesTaken; // Total classes attended (for simplicity, initially totalHours - classesLeft)
    private int classesLeft; // Classes a student has taken leave for

    public Subject(String name, int totalHours) {
        this.name = name;
        this.totalHours = totalHours;
        this.classesLeft = 0; // Starts at 0 leave taken
        this.classesTaken = totalHours; // Assume all classes are attended initially
    }

    /**
     * Calculates the current attendance percentage.
     * Attendance % = ((Total Hours - Classes Left) / Total Hours) * 100
     */
    public double getAttendancePercentage() {
        if (totalHours == 0) return 0.0;
        return ((double) (totalHours - classesLeft) / totalHours) * 100;
    }

    /**
     * Generates the attendance message based on the percentage.
     */
    public String getAttendanceMessage() {
        double percentage = getAttendancePercentage();
        
        if (percentage >= 85.0) {
            return "great attendance";
        } else if (percentage >= 75.0) {
            return "be safe";
        } else if (percentage >= 65.0) {
            return "time to get mc or od (medical certificate or official duty)";
        } else {
            return "lets meet next sem";
        }
    }
}