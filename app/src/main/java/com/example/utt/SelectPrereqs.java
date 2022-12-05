package com.example.utt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceFragmentCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utt.database.DatabaseHandler;
import com.example.utt.databinding.FragmentFirstBinding;
import com.example.utt.databinding.FragmentRecyclerListBinding;
import com.example.utt.databinding.SelectPrereqsBinding;
import com.example.utt.models.Course;
import com.example.utt.models.CourseEventListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectPrereqs extends Fragment {
    private SelectPrereqsBinding binding;
    private RecyclerView prereqRecyclerView;
    private PrereqAdapter prereqAdapter;
    private ArrayList<CourseModel> courseList;
    public static ArrayList<Course> prereqList;
    private FloatingActionButton fab;
    private CheckBox checkBox;
    public static FirstFragment context;

    DatabaseReference databaseCourseCode;

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

        prereqRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        prereqAdapter = new PrereqAdapter(SelectPrereqs.this);
        prereqRecyclerView.setAdapter(prereqAdapter);
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

                NavHostFragment.findNavController(SelectPrereqs.this)
                        .navigate(R.id.action_selectPrereqs2_to_firstFragment);

            }
        });

        // TESTING TASKS ---------------------------------
        Course course = new Course();
        CourseModel task = new CourseModel(course);
//        task.setCourseCode(task.getCourseCode());
        task.setCourseCode("Testing");
        task.setStatus(0);

        courseList.add(task);
        courseList.add(task);

        prereqAdapter.setList(courseList);
        // -----------------------------------------------------

        // Retrieve data from database
        CourseEventListener listener = new CourseEventListener() {
            @Override
            public void onCourseAdded(Course course) {
                courseList.clear();

                for (Course courseObject : Course.getCourses().values()) {
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
            CourseModel tempC = new CourseModel(courseObject);
            courseList.add(0, tempC);
        }
    }
}