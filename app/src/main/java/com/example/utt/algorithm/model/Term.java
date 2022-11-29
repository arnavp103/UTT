package com.example.utt.algorithm.model;

public enum Term {
    WINTER(0),
    SUMMER(1),
    FALL(2),
    NULL(3);

    int term;
    Term(int i) {
        this.term = i;
    }

    public int getTerm() {
        return term;
    }
}

