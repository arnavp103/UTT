package com.example.utt.algorithm.model;


import android.util.Log;

import com.example.utt.models.Course;
import com.example.utt.models.firebase.datamodel.CourseDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchAlgorithm {
    ArrayList<CourseScheduling> coursesTaken = new ArrayList<>();
    ArrayList<Course> needToTake = new ArrayList<>();
    ArrayList<Course> beginningNodes = new ArrayList<>();

    public SearchAlgorithm(ArrayList<CourseScheduling> coursesTaken) {
        this.coursesTaken = coursesTaken; //initialize courses you have already taken
    }

    public void findBeginningNodes(List<Course> targets){
        for (Course target : targets) {
            if (coursesTaken.contains(target)) { //already taken the course
                continue;
            }
            if (!(needToTake.contains(target))) {
                needToTake.add(target);
            }
            if ((target.getPrerequisites().size() == 0)) { //if the course you want to add to the schedule has no prerequisites
                boolean toAdd = true;
                for(CourseScheduling course : coursesTaken) {
                    if (course.getName().equals(target.getName())) {
                        toAdd = false;
                        break;
                    }
                }
                if (toAdd && !(beginningNodes.contains(target))){
                    beginningNodes.add(target);
                }
                //OR if you have recursed to the end of the tree and you have reached the first prerequisite
            } else {

                Log.d("Search Algorithm", target + " has prerequisites " + target.getPrerequisites());
                findBeginningNodes(target.getPrerequisites());
            }
        }
    }
    //now we should have both arrays for the courses we have taken, and the initial prerequisite courses
    public ArrayList<CourseScheduling> search(Term currentTerm, int currentYear) {
        ArrayList<Course> satisfiedPrerequisites = new ArrayList<Course>();
        ArrayList<CourseScheduling> order = new ArrayList<CourseScheduling>();
        ArrayList<Course> canTake = new ArrayList<Course>();

        //initialize order with the courses in beginningNodes
        //once the courses in beginningNodes are taken, move the courses to satisfiedPrerequisites
        //also move the taken courses to satisfiedPrerequisites
        for (CourseScheduling course : coursesTaken) {
            Course C_course = new Course(course.getName(), course.getCode(), course.getSessionOffering(), course.getPrerequisites());
            satisfiedPrerequisites.add(C_course); //satisfiedPrerequisites are not initialized with the courses that student has already taken
        }
        for (Course course : beginningNodes) {
            // for all the courses that have no prerequisites that you have not taken yet, find the soonest time to take them
            int yearToTake = currentYear;
            Term termToTake = currentTerm;
            for (YearlySession term : course.getSessionOffering()) {
                if (term.term.term > termToTake.term) { //find the next most term offered this year
                    termToTake = term.term;
                    break;
                }
            }
            if (termToTake.term == currentTerm.term) {
                yearToTake++;
                termToTake = course.getSessionOffering().get(0).term; //else take the first term this course is offered next year
            }
            int c = 0;
            CourseScheduling addCourse = new CourseScheduling(course.getName(), course.getCode(),
                                                                course.getSessionOffering(), course.getPrerequisites());
            addCourse.sessionBeingTaken.term = termToTake;
            addCourse.sessionBeingTaken.year = yearToTake;

            int d = 0;
            order.add(addCourse); //add course to order
            coursesTaken.add(addCourse);
            satisfiedPrerequisites.add(course); //add course to satisfied prerequisites
            for(Course courseRemove : needToTake) {
                if (Objects.equals(courseRemove.getCode(), addCourse.getCode())) {
                    needToTake.remove(courseRemove);
                    break;
                }
            }
        }

        //at this point satisfiedPrerequisites should be initialized with the courses taken, as well as the courses that have no prerequisites that are required to be taken
        //at this point order should be initialized with the courses that have no prerequisites, and the earliest time to take them
        while(needToTake.size() > 0) {
            //add all the courses you can currently take to canTake
            for (Course course : needToTake) {
                boolean allSatisfied = true;
                for (Course prereq: course.getPrerequisites()) {
                    if (!satisfiedPrerequisites.contains(prereq)) {
                        allSatisfied = false;
                        break;
                    }
                }

                if (allSatisfied)
                    canTake.add(course);
            }



            for(Course course : canTake) {
                int LargestYear = currentYear;
                Term term = Term.WINTER;
                for(CourseScheduling prerequisiteOfCourse : coursesTaken) {
                    for(Course prerequisite : course.getPrerequisites()) {
                        if (Objects.equals(prerequisite.getCode(), prerequisiteOfCourse.getCode())) {
                            if (prerequisiteOfCourse.sessionBeingTaken.year > LargestYear) {
                                LargestYear = prerequisiteOfCourse.sessionBeingTaken.year; //finds the largest year
                            }
                            if ((prerequisiteOfCourse.sessionBeingTaken.year == LargestYear) &&
                                    (prerequisiteOfCourse.sessionBeingTaken.term.term > term.term)) {
                                term = prerequisiteOfCourse.sessionBeingTaken.term; //in the largest year it finds the largest term
                            }
                        }
                    }
                }
                Term termToTake = term;
                int yearToTake = LargestYear;
                for (YearlySession offered : course.getSessionOffering()){
                    if (offered.term.term > term.term) {
                        termToTake = offered.term;
                        break;
                    }
                }

                if ((termToTake.term <= term.term) || ((termToTake.term < currentTerm.term) && (yearToTake == currentYear))) {
                    yearToTake++;
                    termToTake = course.getSessionOffering().get(0).term;
                }
                CourseScheduling scheduledCourse = new CourseScheduling(course.getName(), course.getCode(), course.getSessionOffering(), course.getPrerequisites());
                scheduledCourse.sessionBeingTaken.term = termToTake;
                scheduledCourse.sessionBeingTaken.year = yearToTake;

                order.add(scheduledCourse);
                coursesTaken.add(scheduledCourse);
                needToTake.remove(course);
                satisfiedPrerequisites.add(course);

            }
            canTake.clear();

        }
        return order;
    }
}
