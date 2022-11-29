package com.example.utt.algorithm.model;

import java.util.List;

public class Course {
    public String name;
    public String courseCode;
    public List<YearlySession> sessionOffering; //make sure the order is always: winter, summer, fall
    public List<Course> prerequisites;

    public Course(String name, String courseCode, List<YearlySession> sessionOffering,
                  List<Course> prerequisites) {
        this.name = name;
        this.courseCode = courseCode;
        this.sessionOffering = sessionOffering;
        this.prerequisites = prerequisites;
    }

    public boolean equals(Object other) {
        if (!(other instanceof Course))
            return false;

        return name.equals(((Course)other).name);
    }

}
