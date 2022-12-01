package com.example.utt;

/* Template for each course
   Other course info should be included (11/25)
*/

import android.util.Log;

import com.example.utt.algorithm.model.YearlySession;
import com.example.utt.models.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseModel {

    // Images, if any, should be stored as int. Pass in from drawable
    private String courseCode;
    private String courseName;
    private String courseSession;
    private String coursePrereqs;
    private ArrayList<String> sessionList;
    private ArrayList<String> prereqsList;
    private boolean expanded;
    private static CourseModel currentExpanded;

    final static List<String> sessionNames = List.of("Winter", "Summer", "Fall");

    // Constructor
    public CourseModel(Course model) {
        this.courseCode = model.getCode();
        this.courseName = model.getName();
        ArrayList<String> sessions = new ArrayList<>();
        ArrayList<String> prereqs = new ArrayList<>();

        for (YearlySession session : model.getSessionOffering()) {
            sessions.add( sessionNames.get(session.getTerm().getTerm()));
        }

        for (Course course : model.getPrerequisites()) {
            prereqs.add(course.getCode());
        }
        setCourseSession(sessions);
        setCoursePrereqs(prereqs);
        this.expanded = false;
    }

    public CourseModel(String courseCode, String courseName, ArrayList<String> sessionList,
                       ArrayList<String> prereqsList) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.sessionList = sessionList;
        this.prereqsList = prereqsList;
    }

    // For courses with no prereqs
    public CourseModel(String courseCode, String courseName, ArrayList<String> sessionList) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.sessionList = sessionList;
    }

    // Getter and Setter
    public String getCourseCode() {
        return courseCode;
    }

    // Setter should probably be removed in student view
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    // Setter should probably be removed in student view
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseSession() {
        return courseSession;
    }

    // Setter should probably be removed in student view
    public void setCourseSession(ArrayList<String> sessionArr) {
        StringBuilder courseSessionBuilder = new StringBuilder();
        for (int j = 0; j < sessionArr.size(); j++) {
            courseSessionBuilder.append(sessionArr.get(j));
            if (j != sessionArr.size() - 1) {
                courseSessionBuilder.append(", ").append(((j + 1) % 3 == 0) ? "\n" : ""); // Adds comma when there's stuff left
            }
        }
        this.courseSession = courseSessionBuilder.toString();
    }

    public String getCoursePrereqs() {
        return coursePrereqs;
    }

    // Setter should probably be removed in student view
    public void setCoursePrereqs(ArrayList<String> prereqsArr) {
        StringBuilder coursePrereqsBuilder = new StringBuilder();
        for (int j = 0; j < prereqsArr.size(); j++) {
            coursePrereqsBuilder.append(prereqsArr.get(j));
            if (j != prereqsArr.size() - 1) {
                // Adds comma when there's stuff left. New line every three courses
                coursePrereqsBuilder.append(", ").append(((j + 1) % 3 == 0) ? "\n" : "");
            }
        }
        this.coursePrereqs = coursePrereqsBuilder.toString();
    }

    // Layout expansion

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean new_state) {
        this.expanded = new_state;
    }

}