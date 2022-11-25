package com.example.utt.algorithm.model;

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
            needToTake.add(target);
            if ((target.prerequisites.size() == 0)) { //if the course you want to add to the schedule has no prerequisites
                boolean toAdd = true;
                for(CourseScheduling course : coursesTaken) {
                    if (course.name.equals(target.name)) {
                        toAdd = false;
                        break;
                    }
                }
                if (toAdd){
                    beginningNodes.add(target);
                }
                //OR if you have recursed to the end of the tree and you have reached the first prerequisite
            } else {
                findBeginningNodes(target.prerequisites);
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
            Course C_course = new Course(course.name, course.courseCode, course.sessionOffering, course.prerequisites);
            satisfiedPrerequisites.add(C_course); //satisfiedPrerequisites are not initialized with the courses that student has already taken
        }
    int a = 0;
        for (Course course : beginningNodes) {
            // for all the courses that have no prerequisites that you have not taken yet, find the soonest time to take them
            int yearToTake = currentYear;
            int termToTake = currentTerm.term;
            for (YearlySession term : course.sessionOffering) {
                if (term.term.term > termToTake) { //find the next most term offered this year
                    termToTake = term.term.term;
                    break;
                }
            }
            if (termToTake == currentTerm.term) {
                yearToTake++;
                termToTake = course.sessionOffering.get(0).term.term; //else take the first term this course is offered next year
            }
            int c = 0;
            CourseScheduling addCourse = new CourseScheduling(course.name, course.courseCode,
                                                                course.sessionOffering, course.prerequisites);
            addCourse.sessionBeingTaken.term.term = termToTake;
            addCourse.sessionBeingTaken.year = yearToTake;

            int d = 0;
            order.add(addCourse); //add course to order
            coursesTaken.add(addCourse);
            satisfiedPrerequisites.add(course); //add course to satisfied prerequisites
            for(Course courseRemove : needToTake) {
                if (courseRemove.courseCode == addCourse.courseCode) {
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
                for (Course prereq: course.prerequisites) {
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
                    for(Course prerequisite : course.prerequisites) {
                        if (Objects.equals(prerequisite.courseCode, prerequisiteOfCourse.courseCode)) {
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
                for (YearlySession offered : course.sessionOffering){
                    if (offered.term.term > term.term) {
                        termToTake = offered.term;
                        break;
                    }
                }
                if (termToTake == term) {
                    yearToTake ++;
                    termToTake = course.sessionOffering.get(0).term;
                }
                CourseScheduling scheduledCourse = new CourseScheduling(course.name, course.courseCode, course.sessionOffering, course.prerequisites);
                scheduledCourse.sessionBeingTaken.term = termToTake;
                scheduledCourse.sessionBeingTaken.year = yearToTake;

                order.add(scheduledCourse);
                needToTake.remove(course);
                satisfiedPrerequisites.add(course);
            }

        }
        return order;
    }
}
