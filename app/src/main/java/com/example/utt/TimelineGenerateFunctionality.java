package com.example.utt;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utt.algorithm.model.CourseScheduling;
import com.example.utt.algorithm.model.SearchAlgorithm;
import com.example.utt.algorithm.model.Term;
import com.example.utt.database.DatabaseHandler;
import com.example.utt.databinding.FragmentSecondBinding;
import com.example.utt.databinding.FragmentTimelineBinding;
import com.example.utt.models.Course;
import com.example.utt.models.Listener;
import com.example.utt.models.Student;
import com.example.utt.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TimelineGenerateFunctionality extends Fragment {

    private FragmentTimelineBinding binding;
    List<CourseScheduling> courseOutputList;
    ArrayList<Course> studentCourses;
    ArrayList<Course> wantedCourses;
    ArrayList<CourseScheduling> courseSchedulingList;
    List<CourseScheduling> result;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_FUTURE = "FUTURE";

    // TODO: Rename and change types of parameters
    private static ArrayList<String> futureCourses;

    public TimelineGenerateFunctionality() {
        // Required empty public constructor
    }

    private static Course desiredCourse;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param courseCode The course code to modify
     * @return A new instance of fragment fragment_modify_course.
     */
    // TODO: Rename and change types and number of parameters
    public static TimelineGenerateFunctionality newInstance(String futureCourses) {
        TimelineGenerateFunctionality fragment = new TimelineGenerateFunctionality();
        Bundle args = new Bundle();
        args.putString(ARG_FUTURE, futureCourses);

        // Assume that the hidden course is the current course so yeah.


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            futureCourses = getArguments().getStringArrayList(ARG_FUTURE);
//            Log.d("Setting course code: ", courseCode + " <- ");
        }
    }

    RecyclerView timelineView;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentTimelineBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }


    private void loadAdapter(List<CourseScheduling> result) {
        ArrayList<TimelineCourseModel> courseModelArrayList = new ArrayList<>();
        timelineView = getView().findViewById(R.id.timeline_recycler_list);

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
//          List<Integer> yearList = new ArrayList<>();
        Map<Integer, Map<Term, ArrayList<CourseScheduling>>> output = new HashMap<>();
        for (CourseScheduling course : result) {
            Map<Term, ArrayList<CourseScheduling>> yearContents;
            if (course.sessionBeingTaken.term == Term.NULL) {
                continue;
            }
            if (output.containsKey(course.sessionBeingTaken.year)) {
                yearContents = output.get(course.sessionBeingTaken.year);
            } else {
                yearContents = new HashMap<>();
            }

            if (!yearContents.containsKey(course.sessionBeingTaken.term)) {
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

        for (Integer year : output.keySet()) {
            courseModelArrayList.add(new TimelineCourseModel(year, output.get(year)));
        }
        // we are initializing our adapter class and passing the course list to it.
        timelineCourseAdapter courseAdapter = new timelineCourseAdapter(getContext(), courseModelArrayList);
        // Create vertical list in recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        timelineView.setLayoutManager(linearLayoutManager);
        timelineView.setAdapter(courseAdapter);
    }

    private timelineCourseAdapter courseAdapter;

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        courseSchedulingList = new ArrayList<>();
        courseOutputList = new ArrayList<>();
        wantedCourses = new ArrayList<>();
        studentCourses = new ArrayList<>();
        result = new ArrayList<>();

//        timelineView = (RecyclerView) requireView().findViewById(R.id.timeline_recycler_list);
        // Retrieve student's courses that they took already
        DatabaseHandler.getStudentData(Student.getInstance().getId(), new Listener<String>() {
            @Override
            public void onSuccess(String data, List<String> objectModel) {
                for (String courseCode : objectModel) {
                    studentCourses.add(Course.getCourse(courseCode));
                }
//                    Log.d("COURSES", String.valueOf(studentCourses));
            }

            @Override
            public void onFailure(String data) {

            }

            @Override
            public void onComplete(String data) {

            }
        });

        // Use Search algorithm
        // Created a list of courses of type CourseScheduling
        for(int i=0; i<studentCourses.size(); i++){
            CourseScheduling tempCourse = new CourseScheduling(studentCourses.get(i).getName(),
                    studentCourses.get(i).getCode(), studentCourses.get(i).getSessionOffering(),
                    studentCourses.get(i).getPrerequisites());
            courseSchedulingList.add(i, tempCourse);
        }
        SearchAlgorithm search = new SearchAlgorithm(courseSchedulingList);

//            wantedCourses.add(Course.getCourse("CSCC63"));
        wantedCourses.clear();
        for (String courseCode : futureCourses) {
            wantedCourses.add(Course.getCourse(courseCode.trim()));
            Log.d("Checking Course: ", "___" + courseCode);
            Log.d("Adding Course", "-> " + Course.getCourse(courseCode.trim()) + " : " + Course.getCourse(courseCode.trim()).getPrerequisites());
        }
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
        // Testing -----------------------------------
//            List<Course> targets = new ArrayList<Course>();
//            targets.add(Course.getCourse("CSCB36"));
//            SearchAlgorithm search1 = new SearchAlgorithm((ArrayList<CourseScheduling>) courseOutputList);
//            search1.findBeginningNodes(targets);
//            List<CourseScheduling> result1 = search1.search(Term.FALL, 2022);
//            for (CourseScheduling c : result1) Log.d("HELLO", c.getCode() + c.sessionBeingTaken.year);
        // ------------------------------------------

    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }

}
