package com.example.utt.database;

import com.example.utt.algorithm.model.Term;
import com.example.utt.algorithm.model.YearlySession;
import com.example.utt.models.Course;
import com.example.utt.models.firebase.datamodel.CourseDataModel;
import com.example.utt.models.Student;
import com.example.utt.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseSamples {
    public static void generate() {
        createSampleCourses();
        createSampleUsers();
    }

    /**
     * Rewrites the given input from an arraylist to the required Map type to work with the datamodel
     * @return The Boolean list after conversion
     */
    private static List<Boolean> mapSessions(List<String> sessions) {
        ArrayList<Boolean> result = new ArrayList<>();
        result.add(false);
        result.add(false);
        result.add(false);

        if (sessions.contains("Winter")) result.set(0, true);
        if (sessions.contains("Summer")) result.set(1, true);
        if (sessions.contains("Fall")) result.set(2, true);
        return result;
    }

    // Test
    private static void createSampleCourses() {
        // public Course(String code, String name, Map<String, boolean> sessions, List<String> prerequisites) {
        // Larger size requires Map.ofEntries( entry(k,v), ... )
        ArrayList<CourseDataModel> courses = new ArrayList<>();

        courses.add(new CourseDataModel(
                "Principles of Programming Languages",
                "CSCC24",
                mapSessions(List.of("Winter", "Summer")),
                List.of("CSCB07", "CSCB09"))
        );

        courses.add(new CourseDataModel(
                "Software Design",
                "CSCB07",
                mapSessions(List.of("Fall", "Summer")),
                List.of("CSCA48"))
        );

        courses.add(new CourseDataModel(
                "Software Tools and Systems Programming",
                "CSCB09",
                mapSessions(List.of("Winter", "Summer")),
                List.of("CSCA48"))
        );

        courses.add(new CourseDataModel(
                "Introduction to Compute Science II",
                "CSCA48",
                mapSessions(List.of("Winter", "Summer")),
                List.of("CSCA08"))
        );

        courses.add(new CourseDataModel(
                "Introduction to Compute Science I",
                "CSCA08",
                mapSessions(List.of("Fall", "Winter")),
                List.of())
        );

        courses.add(new CourseDataModel(
                "Computability and Computational Complexity",
                "CSCC63",
                mapSessions(List.of("Fall", "Winter")),
                List.of("CSCB63", "CSCB36"))

        );

//        courses.add(new CourseDataModel(
//                "Design and Analysis of Data Structures",
//                "CSCB63",
//                mapSessions(List.of("Winter", "Summer")),
//                List.of("CSCB36"))
//        );

//        courses.add(new CourseDataModel(
//                "Introduction to the Theory of Computation",
//                "CSCB36",
//                Map.of(0, false, 1, true, 2, true),
//                List.of("CSCA48", "CSCA67"))
//        );
        // WSF
//
        courses.add(new CourseDataModel( // Problem course as there exists MATA67
                "Discrete Mathematics",
                "CSCA67",
                mapSessions(List.of("Fall", "Winter")),
                List.of())
        );

        for (CourseDataModel course : courses) DatabaseHandler.addCourse(course);

        // Test Adding Course Objects [ From Rachel's AlgorithmTest ]
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
        DatabaseHandler.addCourse(cscb36);
    }

    // Test
    private static void createSampleUsers() {
        ArrayList<User> users = new ArrayList<>();

        Student s = new Student(
                "nobeans@mail.utoronto.ca",
                "0beans"
        );

        s.addCourse(List.of("MATA41",  "CSCB36",  "CSCA08"));
        users.add(s);

        s = new Student(
                "lilwayne@mail.utoronto.ca",
                "wane"
        );

        s.addCourse(List.of());
        users.add(s);

        s = new Student(
                "boba",
                "tea"
        );

        s.addCourse(List.of("CSCA08", "CSCA67"));
        users.add(s);

        users.add(new User(
                "admin@mail.utoronto.ca",
                "imAProfessor",
                true
        ));

        users.add(new User(
                "admin",
                "pass",
                true
        ));

        for (User user : users) DatabaseHandler.addUser(user);
    }
}
