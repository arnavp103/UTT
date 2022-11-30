package com.example.utt;

// Persistent Listener modeled after Firebase's Persistent Listeners
public interface CourseEventListener {
    public void onCourseAdded(com.example.utt.Course course);
    public void onCourseChanged(com.example.utt.Course course);
    public void onCourseRemoved(com.example.utt.Course course);
}
