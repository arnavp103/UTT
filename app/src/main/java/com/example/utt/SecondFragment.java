package com.example.utt;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utt.algorithm.model.CourseScheduling;
import com.example.utt.algorithm.model.SearchAlgorithm;
import com.example.utt.algorithm.model.Term;
import com.example.utt.databinding.FragmentSecondBinding;
import com.example.utt.models.Course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    List<CourseScheduling> courseOutputList;
    ArrayList<Course> studentCourses;
    ArrayList<Course> wantedCourses;
    ArrayList<CourseScheduling> courseSchedulingList;
    List<CourseScheduling> result;
    public static ArrayList<String> yearList;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    private void loadAdapter(List<CourseScheduling> result){
        ArrayList<TimelineCourseModel> courseModelArrayList = new ArrayList<>();
        RecyclerView timelineView = getView().findViewById(R.id.timeline_recycler_list);
        Map<String, Course> courses = Course.getCourses();

        ArrayList<CourseScheduling> temp = new ArrayList<>();
        int k = 0;
//        for (Course course : courses.values()) {
//            CourseScheduling tempCourseAdapter = new CourseScheduling(
//                    course.getName(),
//                    course.getCode(), course.getSessionOffering(),
//                    course.getPrerequisites());
//            temp.add(k, tempCourseAdapter);
//            k++;
//        for (int i = 0; i < courses.size(); i++){
//            if (courses.get(i) == null) {
//                continue;
//            }
//            CourseScheduling tempCourseAdapter = new CourseScheduling(courses.get(i).getName(),
//                    courses.get(i).getCode(), courses.get(i).getSessionOffering(),
//                    courses.get(i).getPrerequisites());
//            temp.add(i, tempCourseAdapter);
//        }
        // List<Integer> yearList = new ArrayList<>();
        Map<Integer, Map<Term, ArrayList<CourseScheduling>>> output = new HashMap<>();
        for(CourseScheduling course: result){
            Map<Term, ArrayList<CourseScheduling>> yearContents;
            if(course.sessionBeingTaken.term == Term.NULL){
                continue;
            }
            if(output.containsKey(course.sessionBeingTaken.year)) {
                yearContents = output.get(course.sessionBeingTaken.year);
            }
            else{
                yearContents = new HashMap<>();
            }

            if(!yearContents.containsKey(course.sessionBeingTaken.term)) {
                yearContents.put(course.sessionBeingTaken.term, new ArrayList<>());
            }

            ArrayList<CourseScheduling> courseList = yearContents.get(course.sessionBeingTaken.term);
            courseList.add(course);

            output.put(course.sessionBeingTaken.year, yearContents);

//                courseList = yearContents.get(course.sessionBeingTaken.term);
//               termsList.add(course.sessionBeingTaken.term.toString());

        }
//        Log.d("INFORMATION", String.valueOf(output));

//        for(int i = 0; i<temp.size(); i++){
//            courseModelArrayList.add(new TimelineCourseModel());
//        }

        yearList = new ArrayList<>();
        for (Integer year : output.keySet()) {
            courseModelArrayList.add(new TimelineCourseModel(year, output.get(year)));
//            yearList.add(year, output.keySet().toString());
        }
        // we are initializing our adapter class and passing the course list to it.
        timelineCourseAdapter courseAdapter = new timelineCourseAdapter(getContext(), courseModelArrayList);
        // Create vertical list in recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        timelineView.setLayoutManager(linearLayoutManager);
        timelineView.setAdapter(courseAdapter);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        courseSchedulingList = new ArrayList<>();
        courseOutputList = new ArrayList<>();
        wantedCourses = new ArrayList<>();
        studentCourses = new ArrayList<>();
        result = new ArrayList<>();

        // Testing -----------------------------------
        List<Course> targets = new ArrayList<Course>();
        targets.add(Course.getCourse("CSCB36"));
        SearchAlgorithm search1 = new SearchAlgorithm((ArrayList<CourseScheduling>) courseOutputList);
        search1.findBeginningNodes(targets);
        List<CourseScheduling> result1 = search1.search(Term.FALL, 2022);
        for (CourseScheduling c : result1) Log.d("HELLO", c.getCode() + c.sessionBeingTaken.year);
        // ------------------------------------------



        // Retrieve student's courses that they took already
//        DatabaseHandler.getUser("nobeans@mail.utoronto.ca", "0beans", new Listener<User>() {
//        @Override
//        public void onSuccess(String data, List<User> objectModel) {
//            DatabaseHandler.getStudentData(objectModel.get(0).getId(), new Listener<String>() {
//                @Override
//                public void onSuccess(String data, List<String> objectModel) {
//                    for (String courseCode : objectModel) {
//                        studentCourses.add(Course.getCourse(courseCode));
//                    }
//                    Log.d("COURSES", String.valueOf(studentCourses));
//                }
//
//                @Override
//                public void onFailure(String data) {
//
//                }
//
//                @Override
//                public void onComplete(String data) {
//
//                }
//            });
//        }
//
//        @Override
//        public void onFailure(String data) {
//
//        }
//
//        @Override
//        public void onComplete(String data) {
//
//        }
//        });

        // Use Search algorithm
        // Created a list of courses of type CourseScheduling
        for(int i=0; i<studentCourses.size(); i++){
            CourseScheduling tempCourse = new CourseScheduling(studentCourses.get(i).getName(),
                    studentCourses.get(i).getCode(), studentCourses.get(i).getSessionOffering(),
                    studentCourses.get(i).getPrerequisites());
            courseSchedulingList.add(i, tempCourse);
        }
        SearchAlgorithm search = new SearchAlgorithm(courseSchedulingList);
        wantedCourses.add(Course.getCourse("CSCC63"));
        search.findBeginningNodes(wantedCourses);
        result = search.search(Term.FALL, 2022);
        // Implements Recycler view

        StringBuilder output = new StringBuilder();

        for (CourseScheduling cd : result) {
            output.append(cd.getCode())
                    .append(": ")
                    .append(cd.sessionBeingTaken.term)
                    .append(" ")
                    .append(cd.sessionBeingTaken.year)
                    .append("\n");
        }
        Log.d("INFORMATION", output.toString());
        loadAdapter(result);

        binding.buttonBack.setOnClickListener(view1 -> NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_to_Home));
        binding.buttonAddName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { addCourseName(); }
        });
        listCourseName = (ListView)getView().findViewById(R.id.listCourseName);
        courseList = new ArrayList<>();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}