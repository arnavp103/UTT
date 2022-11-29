package com.example.utt.models;

import com.example.utt.models.firebase.datamodel.CourseDataModel;

public class ExcludableCourse extends CourseDataModel {
    @Override
    public void setKey(String key) { this.key = key; }
}
