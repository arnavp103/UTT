package com.example.utt.models.firebase.datamodel;

public class ExcludedCourseDataModel extends CourseDataModel {
    @Override
    public void setKey(String key) {
        this.key = key;
        setCourseObject(generateCourseObject());}
}
