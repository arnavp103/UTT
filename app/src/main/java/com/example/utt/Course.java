package com.example.utt;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Course  { // implements Serializable
    private static List<CourseEventListener> listeners;
    private static Map<String, Course> courses;
    private static Map<String, String> coursesID_CODE;

    @Exclude
    private String key;

    protected String code;
    private String name;
    private Map<Integer, Boolean> sessionOfferings;
    private List<String> prerequisites;

    // Static Methods
    public static Map<String, Course> getCourses() { return courses; }

    public static void updateCourse(Course course, String key) {
        // Fetch the course with the given key:

        Log.i("COURSE", "Modified Information: " + course.toString());
        String keyRef = coursesID_CODE.get(key);
        if (courses.containsKey(keyRef)) {
            courses.put(keyRef, course);
        } else {
            Log.d("COURSE", "Failed to find key: " + course.code);
        }
        if (listeners != null) for (CourseEventListener obj : listeners) obj.onCourseChanged(course);
    }

    public static void removeCourse(Course course) {
        Log.i("COURSE", "Removed Course: " + course.toString());
        courses.remove(course.code);
        coursesID_CODE.remove(course.key);
        if (listeners != null) for (CourseEventListener obj : listeners) obj.onCourseRemoved(course);
    }

    private static void addCourse(Course course) {
        Log.i("COURSE", "Added Course: " + course.toString());
        courses.put(course.code, course);
        Log.i("ADDED ", course.key + " -> " + course.code);
        if (listeners != null) for (CourseEventListener obj : listeners) obj.onCourseAdded(course);
    }

    public static void addListener(CourseEventListener listener) {
        if (listeners == null) listeners = new ArrayList<>();
        listeners.add(listener);
    }

    public static void removeListener(CourseEventListener listener) {
        Log.d("COURSE", "Detached Listener.");
        listeners.remove(listener);
    }

    // Attach a listener
    public Course() {
        if (courses == null) {coursesID_CODE = new HashMap<>(); courses = new HashMap<>();}
    }

    public Course(String code, String name, Map<Integer, Boolean> sessions, List<String> prerequisites) {
        this();
        setCode(code);
        this.name = name;
        this.sessionOfferings = sessions;
        this.prerequisites = prerequisites;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
        coursesID_CODE.put(this.key, this.code);
        Log.i("COURSE", "-> Referencing Key: " + key + " " + coursesID_CODE.get(key));
    }

    @Exclude
    public String getKey() { return key; }
    public String getCode() { return code; }

    public String getName() { return name; }

    public void setCode(String code) {
        this.code = code;
        Course.addCourse(this);
    }

    public void setPrerequisites(List<String> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public Map<String, String> getPrerequisites() {
        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < prerequisites.size(); i++)
            result.put(Integer.toString(i), prerequisites.get(i));
        return result;
    }

    public Map<String, Boolean> getSessionOfferings() {
        Map<String, Boolean> result = new HashMap<>();
        for (Integer c : sessionOfferings.keySet())
            result.put(c.toString(), sessionOfferings.get(c));
        return result;
    }

    public void setSessionOfferings(ArrayList<Boolean> session) {// Map<Integer, Boolean> session) {
        Map<Integer, Boolean> result = new HashMap<>();
        for (int i = 0 ; i < session.size(); i++)
            result.put(i, session.get(i));
        sessionOfferings = result;
    }

    @NonNull
    public String toString() {
        return code + ": " + name;
    }


}
