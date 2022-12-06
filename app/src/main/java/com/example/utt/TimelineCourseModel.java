package com.example.utt;

/* Template for each course
 */

import com.example.utt.algorithm.model.CourseScheduling;
import com.example.utt.algorithm.model.Session;
import com.example.utt.algorithm.model.Term;
import com.example.utt.algorithm.model.YearlySession;
import com.example.utt.models.Course;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TimelineCourseModel {

    // Images, if any, should be stored as int. Pass in from drawable
    private String courseCode;
    private String courseName;
    private String courseSession;
    private String year;
    private ArrayList<String> termList;
    private ArrayList<CourseScheduling> courses;
    private List<CourseScheduling> winterCourses;
    private List<CourseScheduling> summerCourses;
    private List<CourseScheduling> fallCourses;
    private ArrayList<String> yearList;
    private boolean expanded;
    private static TimelineCourseModel currentExpanded;

    final static List<String> sessionNames = List.of("Winter", "Summer", "Fall");

    // Constructor
    public TimelineCourseModel(Integer year, Map<Term, ArrayList<CourseScheduling>> yearContents) {
        termList = new ArrayList<>();
        courses = new ArrayList<>();
        this.year = year.toString();
        for (Term item : yearContents.keySet()) {
            this.termList.add(String.valueOf(item));
            if (item.equals(Term.WINTER)) {
                this.winterCourses = yearContents.get(item);
            } else if (item.equals(Term.SUMMER)) {
                this.summerCourses = yearContents.get(item);
            } else if (item.equals(Term.FALL)) {
                this.fallCourses = yearContents.get(item);
            }
        }
        expanded = true;
    }

//    public TimelineCourseModel(CourseScheduling model) {
//        this.courseCode = model.getCode();
//        this.courseName = model.getName();
//        ArrayList<String> sessions = new ArrayList<>();
//        ArrayList<Integer> years = new ArrayList<>();
//
//        for (YearlySession session : model.getSessionOffering()) {
//            sessions.add(sessionNames.get(session.getTerm().getTerm()));
//            years.add(model.sessionBeingTaken.year);
//        }
//        setCourseSession(sessions);
//        this.expanded = false;
//    }

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

    public String getYear() {
        return year;
    }

    public String getWinterCourses(){
        if (winterCourses == null) return "";
        StringBuilder result = new StringBuilder();
        for(CourseScheduling i: winterCourses){
            result.append(i.getCode());
            result.append(" - ");
            result.append(i.getName());
            result.append("\n");
        }
        return result.toString();
    }

    public String getSummerCourses(){
        if (summerCourses == null) return "";
        StringBuilder result = new StringBuilder();
        for(CourseScheduling i: summerCourses){
            result.append(i.getCode());
            result.append(" - ");
            result.append(i.getName());
            result.append("\n");
        }
        return result.toString();
    }

    public String getFallCourses(){
        if (fallCourses == null) return "";
        StringBuilder result = new StringBuilder();
        for(CourseScheduling i: fallCourses){
            result.append(i.getCode());
            result.append(" - ");
            result.append(i.getName());
            result.append("\n");
        }
        return result.toString();
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

    // Layout expansion

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean new_state) {
        this.expanded = new_state;
    }

}