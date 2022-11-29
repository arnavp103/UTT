package com.example.utt.algorithm.model;

import com.example.utt.models.Course;

import java.util.List;

public class CourseScheduling extends Course {
    public Session sessionBeingTaken = new Session(0,Term.WINTER);
    public CourseScheduling(String name, String courseCode, List<YearlySession> sessionOffering,
                            List<Course> prerequisites) {
        super(name, courseCode, sessionOffering, prerequisites);
    }
    // CourseOfferings course = new CourseOfferings(this.name, this.courseCode, this.sessionOffering,this.prerequisites);
}
