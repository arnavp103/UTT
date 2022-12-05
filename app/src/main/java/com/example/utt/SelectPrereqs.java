package com.example.utt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utt.databinding.SelectPrereqsBinding;
import com.example.utt.models.Course;
import com.example.utt.models.CourseEventListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class SelectPrereqs extends Fragment {
    private SelectPrereqsBinding binding;
    private RecyclerView prereqRecyclerView;
    private PrereqAdapter prereqAdapter;
    private ArrayList<CourseModel> courseList;
    public static ArrayList<Course> prereqList;
    private FloatingActionButton fab;
    private CheckBox checkBox;
    public static AdminHome context;
    private SearchView prereqFilter;
    public TextView emptyResultView;

    DatabaseReference databaseCourseCode;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_HIDDEN_COURSE = "HIDDEN_COURSE";

    private String hiddenCourse;

    // TODO: Rename and change types and number of parameters
    public static SelectPrereqs newInstance(String courseCode) {
        SelectPrereqs fragment = new SelectPrereqs();
        Bundle args = new Bundle();
        args.putString(ARG_HIDDEN_COURSE, courseCode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            hiddenCourse = getArguments().getString(ARG_HIDDEN_COURSE);
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = SelectPrereqsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        courseList = new ArrayList<>();
        databaseCourseCode = FirebaseDatabase.getInstance("https://b07-final-db5c5-default-rtdb.firebaseio.com")
                .getReference("courses");
        prereqRecyclerView = getView().findViewById(R.id.prereqRecyclerView);
        checkBox = getView().findViewById(R.id.prereqCheckBox);

        emptyResultView = (TextView)getView().findViewById(R.id.prereqEmptyResultText);
        prereqRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        prereqAdapter = new PrereqAdapter(SelectPrereqs.this);
        prereqRecyclerView.setAdapter(prereqAdapter);

        prereqFilter = (SearchView)getView().findViewById(R.id.prereqFilter);
        prereqFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prereqFilter.setIconified(false);
            }
        });

        prereqList = new ArrayList<>();

        fab = getView().findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bundle b = new Bundle();
//                b.putStringArrayList("Prereqs", prereqList);
//                SelectPrereqs select=
////                prereqList = prereqAdapter.getSelectedCourses();
//                context.donePrereq(prereqAdapter.getSelectedCourses());

                NavHostFragment.findNavController(SelectPrereqs.this).navigateUp();
            }
        });

        // TESTING TASKS ---------------------------------
        Course course = new Course();
        CourseModel task = new CourseModel(course);
//        task.setCourseCode(task.getCourseCode());
//        task.setCourseCode("Testing");
//        task.setStatus(0);
//
//        courseList.add(task);
//        courseList.add(task);

        prereqAdapter.setList(courseList);
        // -----------------------------------------------------

        // Retrieve data from database
        CourseEventListener listener = new CourseEventListener() {
            @Override
            public void onCourseAdded(Course course) {
                courseList.clear();

                for (Course courseObject : Course.getCourses().values()) {
                    if (Objects.equals(courseObject.getCode(), hiddenCourse)) continue;
                    CourseModel tempC = new CourseModel(courseObject);
                    courseList.add(0, tempC);
                }
            }

            @Override
            public void onCourseChanged(Course course) {

            }

            @Override
            public void onCourseRemoved(Course course) {

            }
        };

        Course.addListener(listener);

        courseList.clear();

        for (Course courseObject : Course.getCourses().values()) {
            if (Objects.equals(courseObject.getCode(), hiddenCourse)) continue;
            CourseModel tempC = new CourseModel(courseObject);
            courseList.add(0, tempC);
        }

        prereqFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return false;
            }
        });
    }

    private void filter(String text) {
        ArrayList<CourseModel> filteredList = new ArrayList<CourseModel>();

        for (CourseModel item : courseList) {
            if (item.getCourseName().toLowerCase().contains(text.toLowerCase()) || item.getCourseCode().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()) {
            prereqAdapter.emptyList();
        } else {
            prereqAdapter.filterList(filteredList);
        }
    }
}