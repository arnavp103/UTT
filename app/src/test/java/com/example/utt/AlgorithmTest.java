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
        coursesTaken.add(csca08_scheduling);
        coursesTaken.add(csca48_scheduling);

        List<Course> targets = new ArrayList<>();
        targets.add(cscb36);
        SearchAlgorithm search = new SearchAlgorithm(coursesTaken);
        search.findBeginningNodes(targets);
        
        List<CourseScheduling> result = search.search(Term.FALL, 2022);
        assertEquals("CSCA67", result.get(0).getCode());
        assertEquals(Term.WINTER, result.get(0).sessionBeingTaken.term);
        assertEquals(2023, result.get(0).sessionBeingTaken.year);

        assertEquals("CSCB36", result.get(1).getCode());
        assertEquals(Term.SUMMER, result.get(1).sessionBeingTaken.term);
        assertEquals(2023, result.get(1).sessionBeingTaken.year);
    }
}
