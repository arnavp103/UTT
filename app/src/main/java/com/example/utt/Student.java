package com.example.utt;


import android.util.Log;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends User {
    private List<String> coursesTaken;

    public Student() {}

    public Student(String email, String password) {
        super(email, password);
    }

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
    }
    public Map<String, String> getCoursesTaken() { return Map.of(); }

    @Exclude
    public Map<String, String> _getCoursesTaken() {
        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < coursesTaken.size(); i++) {
            result.put(Integer.toString(i), coursesTaken.get(i));
        }
        return (result.size() == 0) ? Map.of("0","None") : result;
    }
}
