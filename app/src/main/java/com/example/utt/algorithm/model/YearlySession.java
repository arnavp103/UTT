package com.example.utt.algorithm.model;
// For a course that's offered every year which is our default assumption
public class YearlySession {
    Term term;
    public YearlySession(Term term) {
        this.term = term;
    }
    public Term getTerm() { return term; }
    public String toString() {
        return term.name().toUpperCase().charAt(0) +  term.name().toLowerCase().substring(1);
    }
}
