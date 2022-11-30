package com.example.utt;

import com.example.utt.models.Course;

// Persistent Listener modeled after Firebase's Persistent Listeners
public interface CourseEventListener {
    public void onCourseAdded(Course course);
    public void onCourseChanged(Course course);
    public void onCourseRemoved(Course course);
}
