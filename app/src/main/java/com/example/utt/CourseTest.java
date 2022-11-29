package com.example.utt;

import com.example.utt.algorithm.model.Course;
import com.example.utt.algorithm.model.YearlySession;

import java.util.List;

public class CourseTest {
    String code;
    String name;
    List<YearlySession> season; //make sure the order is always: winter, summer, fall
    public List<Course> prerequisites;

    public CourseTest(){}
    public CourseTest(String name, String code,  List<YearlySession> season,
                      List<Course> prerequisites){
        this.name = name;
        this.code = code;
        this.season = season;
        this.prerequisites = prerequisites;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public List<Course> getPrerequisites() {
        return prerequisites;
    }

    public List<YearlySession> getSeason(){
        return season;
    }
}
