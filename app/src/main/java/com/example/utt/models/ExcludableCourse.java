package com.example.utt.models;

import com.example.utt.models.firebase.datamodel.CourseDataModel;

public class ExcludableCourse extends CourseDataModel {
    @Override // TODO - Remove this override later
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void setKey(String key) { this.key = key; }
}
