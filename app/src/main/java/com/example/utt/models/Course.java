package com.example.utt.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.utt.algorithm.model.YearlySession;
import com.example.utt.models.firebase.datamodel.CourseDataModel;

import java.util.List;
import java.util.Map;

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
        StringBuilder session = new StringBuilder();
        if (getSessionOffering() != null) {
            for (YearlySession y : getSessionOffering()) {
                session.append(y.getTerm()).append(" ");
            }
        }

        Log.d("TOSTRING", getCode() + " was called: " + session);
        return getCode() + ": " + getName() + "\n" + "[ " + session.toString() + "]";
    }

    // TODO - Properly implement this
    public static void addListener(CourseEventListener listener) {CourseDataModel.addListener(listener);}
    public static void removeListener(CourseEventListener listener) {CourseDataModel.removeListener(listener);}

    public static Map<String, Course> getCourses() { return CourseDataModel.getCourses(); }

    public static Course getCourse(String courseCode) {
        return CourseDataModel.getCourse(courseCode);
    }

    public void addCourseListener(CourseEventListener listener) {
        CourseDataModel.getCourseDataModel(courseCode).addCourseListener(listener);
    }

    public void removeCourseListener(CourseEventListener listener) {
        CourseDataModel.getCourseDataModel(courseCode).removeCourseListener(listener);
    }

}