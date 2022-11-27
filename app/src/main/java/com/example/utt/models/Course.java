package com.example.utt.models;

import androidx.annotation.NonNull;

import com.example.utt.algorithm.model.YearlySession;
import com.example.utt.models.firebase.datamodel.CourseDataModel;

import java.util.List;

public class Course {
//    private CourseDataModel dataModel;

    private String name;
    private String courseCode;
    private List<YearlySession> sessionOffering; //make sure the order is always: winter, summer, fall
    private List<Course> prerequisites;

    public Course() {}

    public Course(String name, String courseCode, List<YearlySession> sessionOffering,
                  List<Course> prerequisites) {
        this.name = name;
        this.courseCode = courseCode;
        this.sessionOffering = sessionOffering;
        this.prerequisites = prerequisites;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return courseCode;
    }

    public List<YearlySession> getSessionOffering() {
        return sessionOffering;
    }

    public void setSessionOffering(List<YearlySession> sessionOffering) {
        this.sessionOffering = sessionOffering;
    }

    public List<Course> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public boolean equals(Object other) {
        if (!(other instanceof Course))
            return false;

        return name.equals(((Course) other).getName());
    }

    @NonNull
    public String toString() {
        return getCode() + ": " + getName();
    }
    public static void addListener(CourseEventListener listener) {CourseDataModel.addListener(listener);}
}