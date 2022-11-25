package com.example.utt;

public class Course {
    String name;
    String season;
    String prereq;

    public Course(){}
    public Course(String name, String season, String prereq){
        this.name = name;
        this.season = season;
        this.prereq = prereq;
    }

    public String getName() {
        return name;
    }

    public String getPrereq() {
        return prereq;
    }

    public String getSeason(){
        return season;
    }
}
