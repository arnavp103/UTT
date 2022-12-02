package com.example.utt;

import static org.junit.Assert.assertEquals;

import com.example.utt.algorithm.model.CourseScheduling;
import com.example.utt.algorithm.model.SearchAlgorithm;
import com.example.utt.algorithm.model.Term;
import com.example.utt.algorithm.model.YearlySession;
import com.example.utt.models.Course;
import com.example.utt.models.firebase.datamodel.CourseDataModel;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmTest {

    @Test
    public void testAlgorithm() {
        List<Course> a08Prerequisites = new ArrayList<>();
        List<YearlySession> a08sessionOfferings = new ArrayList<>();
        YearlySession b = new YearlySession(Term.WINTER);
        YearlySession a = new YearlySession(Term.FALL);
        a08sessionOfferings.add(a);
        a08sessionOfferings.add(b);
        Course csca08 = new Course("Introduction To Computer Science 1", "CSCA08",
                                    a08sessionOfferings, a08Prerequisites);

        List<Course> a48Prerequisites = new ArrayList<>();
        List<YearlySession> a48sessionOfferings = new ArrayList<>();
        YearlySession c = new YearlySession(Term.WINTER);
        YearlySession d = new YearlySession(Term.SUMMER);
        a48sessionOfferings.add(c);
        a48sessionOfferings.add(d);
        a48Prerequisites.add(csca08);
        Course csca48 = new Course("Introduction To Computer Science 2", "CSCA48",
                                    a48sessionOfferings, a48Prerequisites);


        List<Course> a67Prerequisites = new ArrayList<>();
        List<YearlySession> a67sessionOfferings = new ArrayList<>();
        YearlySession e = new YearlySession(Term.WINTER);
        YearlySession f = new YearlySession(Term.SUMMER);
        a67sessionOfferings.add(e);
        a67sessionOfferings.add(f);
        Course csca67 = new Course("Discrete Mathematics", "CSCA67",
                a67sessionOfferings, a67Prerequisites);

        List<Course> b36Prerequisites = new ArrayList<>();
        b36Prerequisites.add(csca48);
        b36Prerequisites.add(csca67);
        List<YearlySession> b36sessionOfferings = new ArrayList<>();
        YearlySession g = new YearlySession(Term.SUMMER);
        YearlySession h = new YearlySession(Term.FALL);
        b36sessionOfferings.add(g);
        b36sessionOfferings.add(h);
        Course cscb36 = new Course("Introduction To The Theory Of Computation", "CSCB36",
                b36sessionOfferings, b36Prerequisites);

        ArrayList<CourseScheduling> coursesTaken = new ArrayList<CourseScheduling>();
        CourseScheduling csca08_scheduling = new CourseScheduling(csca08.getName(), csca08.getCode(), csca08.getSessionOffering(), csca08.getPrerequisites());
        CourseScheduling csca48_scheduling = new CourseScheduling(csca48.getName(), csca48.getCode(), csca48.getSessionOffering(), csca48.getPrerequisites());
        csca08_scheduling.sessionBeingTaken.term = Term.FALL;
        csca08_scheduling.sessionBeingTaken.year = 2021;
        csca48_scheduling.sessionBeingTaken.term = Term.WINTER;
        csca48_scheduling.sessionBeingTaken.year = 2022;
        coursesTaken.add(csca08_scheduling);
        coursesTaken.add(csca48_scheduling);

        List<Course> targets = new ArrayList<>();
        targets.add(cscb36);
        SearchAlgorithm search = new SearchAlgorithm(coursesTaken);
        //search.findBeginningNodes(targets);
        //List<CourseScheduling> result = search.search(Term.FALL, 2022);
        //assertEquals("CSCA67", result.get(0).getCode());
        //assertEquals(Term.WINTER, result.get(0).sessionBeingTaken.term);
        //assertEquals(2023, result.get(0).sessionBeingTaken.year);

        //assertEquals("CSCB36", result.get(1).getCode());
        //assertEquals(Term.SUMMER, result.get(1).sessionBeingTaken.term);
        //assertEquals(2023, result.get(1).sessionBeingTaken.year);

        List<Course>b07Prerequisites = new ArrayList<>();
        b07Prerequisites.add(csca48);
        List<YearlySession> b07sessionOfferings = new ArrayList<>();
        YearlySession i = new YearlySession(Term.SUMMER);
        YearlySession j = new YearlySession(Term.FALL);
        b07sessionOfferings.add(i);
        b07sessionOfferings.add(j);
        Course cscb07 = new Course("Software Design", "CSCB07",
                b07sessionOfferings, b07Prerequisites);

        List<Course>b09Prerequisites = new ArrayList<>();
        b09Prerequisites.add(csca48);
        List<YearlySession> b09sessionOfferings = new ArrayList<>();
        YearlySession l = new YearlySession(Term.WINTER);
        YearlySession k = new YearlySession(Term.SUMMER);
        b09sessionOfferings.add(l);
        b09sessionOfferings.add(k);
        Course cscb09 = new Course("Software Tools and Systems Programming", "CSCB09",
                b09sessionOfferings, b09Prerequisites);

        List<Course>b63Prerequisites = new ArrayList<>();
        b63Prerequisites.add(cscb36);
        List<YearlySession> b63sessionOfferings = new ArrayList<>();
        YearlySession m = new YearlySession(Term.SUMMER);
        YearlySession n = new YearlySession(Term.WINTER);
        b63sessionOfferings.add(n);
        b63sessionOfferings.add(m);

        Course cscb63 = new Course("Design and Analysis of Data Structures", "CSCB63",
                b63sessionOfferings, b63Prerequisites);

        List<Course>c24Prerequisites = new ArrayList<>();
        c24Prerequisites.add(cscb07);
        c24Prerequisites.add(cscb09);
        List<YearlySession> c24sessionOfferings = new ArrayList<>();
        YearlySession p = new YearlySession(Term.WINTER);
        YearlySession o = new YearlySession(Term.SUMMER);
        c24sessionOfferings.add(p);
        c24sessionOfferings.add(o);
        Course cscc24 = new Course("Principles of Programming Languages", "CSCC24",
                c24sessionOfferings, c24Prerequisites);

        List<Course>c63Prerequisites = new ArrayList<>();
        c63Prerequisites.add(cscb63);
        List<YearlySession> c63sessionOfferings = new ArrayList<>();
        YearlySession r = new YearlySession(Term.WINTER);
        YearlySession q = new YearlySession(Term.FALL);
        c63sessionOfferings.add(r);
        c63sessionOfferings.add(q);
        Course cscc63 = new Course("Computability and Computational Complexity", "CSCC63",
                c63sessionOfferings, c63Prerequisites);

        List<Course>a11Prerequisites = new ArrayList<>();
        List<YearlySession> a11sessionOfferings = new ArrayList<>();
        YearlySession s = new YearlySession(Term.FALL);
        a11sessionOfferings.add(s);

        Course phla11 = new Course("Introduction to Ethics", "PHLA11",
                a11sessionOfferings, a11Prerequisites);

        ArrayList<CourseScheduling> coursesTaken2 = new ArrayList<CourseScheduling>();
        coursesTaken2.add(csca08_scheduling);
        coursesTaken2.add(csca48_scheduling);
        List<Course> targets2 = new ArrayList<>();
        targets2.add(cscc24);
        targets2.add(cscc63);
        targets2.add(phla11);
        SearchAlgorithm search2 = new SearchAlgorithm(coursesTaken2);
        search2.findBeginningNodes(targets2);
        List<CourseScheduling> result2 = search2.search(Term.FALL, 2022);

    }
}
