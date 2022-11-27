package com.example.utt.models;

import com.example.utt.models.firebase.datamodel.CourseDataModel;

// Persistent Listener modeled after Firebase's Persistent Listeners
public interface CourseEventListener {
    void onCourseAdded(Course course);
    void onCourseChanged(Course course);
    void onCourseRemoved(Course course);
}
