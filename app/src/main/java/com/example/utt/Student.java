package com.example.utt;
import android.util.Log;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends User {
    private List<String> coursesTaken;

    private static Student currentStudent;

    public Student() {}

    public Student(String email, String password) {
        super(email, password);
    }

    public static void logout() {
        // TODO - implement.
    }

    public static void login(Student user, List<String> coursesTaken) {
        if (currentStudent != null) logout();
        currentStudent = user;
        user.setCoursesTaken(coursesTaken);
    }

    public static Student getInstance() { return currentStudent; }

    public void addCourse(List<String> courses) {
        if (coursesTaken==null) {coursesTaken = courses; return;}
        for (String c : coursesTaken) {
            if (!(courses.contains(c))) addCourse(c);
        }
    }

    public void addCourse(String course) {
        coursesTaken.add(course);
    }

    public void setCoursesTaken(List<String> coursesTaken) {
        this.coursesTaken = coursesTaken;
        if (this.coursesTaken.contains("None")) { coursesTaken.remove("None"); }
    }
//    public Map<String, String> getCoursesTaken() { return Map.of(); }

    @Exclude
    public List<String> getCoursesTaken() {
        return coursesTaken;
    }

    /** USE getCoursesTaken()! Manually called by DatabaseHandler to populate student data
     * @return Arraylist written as a hashmap
     */
    @Exclude
    public Map<String, String> _getCoursesTaken() {
        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < coursesTaken.size(); i++) {
            result.put(Integer.toString(i), coursesTaken.get(i));
        }
        return (result.size() == 0) ? Map.of("0","None") : result;
    }

}