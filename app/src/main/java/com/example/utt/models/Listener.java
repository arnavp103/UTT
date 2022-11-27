package com.example.utt.models;

import java.util.List;

import javax.annotation.Nullable;

// Callback Interface
public interface Listener<T> {
    void onSuccess(String data, @Nullable List<T> objectModel);
    void onFailure(String data);
    void onComplete(String data);
}
