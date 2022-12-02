package com.example.utt.models;
import android.util.Log;

import android.util.Log;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends User {
    private Map<String, String> coursesTaken;

    private static Student currentStudent;

    public Student() {}

    public Student(String email, String password) {
        super(email, password);
    }

    public static void logout() {
        // TODO - implement.
    }

    public static void login(Student user, Map<String, String> coursesTaken) {
        if (currentStudent != null) logout();
        currentStudent = user;
        user.setCoursesTaken(coursesTaken);
    }

    public static Student getInstance() { return currentStudent; }

    public void addCourse(Map<String, String> courses) {
        if (coursesTaken==null) {coursesTaken = courses; return;}
        for (Map.Entry<String, String> entry : coursesTaken.entrySet()) {
            String courseFK = entry.getKey().toString();
            String courseCode = entry.getValue().toString();
            if (!(courses.containsKey(courseFK))) addCourse(courseFK, courseCode);
        }
    }

    public void addCourse(String courseID, String courseCode) {
        coursesTaken.put(courseID, courseCode);
    }

    public void setCoursesTaken(Map<String, String> coursesTaken) {
        this.coursesTaken = coursesTaken;
        if (this.coursesTaken.containsKey("None")) { coursesTaken.remove("None"); }
    }
//    public Map<String, String> getCoursesTaken() { return Map.of(); }

    @Exclude
    public Map<String, String> getCoursesTaken() {
        return coursesTaken;
    }

    /** USE getCoursesTaken()! Manually called by DatabaseHandler to populate student data
     * @return Arraylist written as a hashmap
     */
    @Exclude
    public Map<String, String> _getCoursesTaken() {
        Map<String, String> result = new HashMap<>();
        if (coursesTaken != null) {
            for (int i = 0; i < coursesTaken.size(); i++) {
                result.put(Integer.toString(i), coursesTaken.get(i));
            }
        }
        return (result.size() == 0) ? Map.of("None","None") : result;
    }

}