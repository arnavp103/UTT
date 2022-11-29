package com.example.utt.algorithm.model;


import com.example.utt.models.Course;

import java.util.List;

/**
 *
 */
public class CourseOfferings extends Course {
    // basically what the admin inputs
    List<Course> unlockedCourses;

    CourseOfferings(String name, String courseCode,
                    List<YearlySession> sessionOffering, List<Course> prerequisites) {
            // name, course code, session offering
        super(name, courseCode, sessionOffering, prerequisites);
    }

    /**
     * Gets the courses that course is a prerequisite of.
     * @param course course that is being offered
     * @param allCourses all course that are offered
     * @return all courses that contain the given course as a prerequisite
     */
    public List<Course> UnlockedCourses(Course course, List<CourseOfferings> allCourses) {
        for (CourseOfferings courseOffer : allCourses) {
            if (courseOffer.getPrerequisites().contains(course)) {
                unlockedCourses.add(courseOffer);
            }
        }
        return unlockedCourses;
    }
}
