package com.example.utt;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.utt.algorithm.model.CourseScheduling;
import com.example.utt.algorithm.model.SearchAlgorithm;
import com.example.utt.algorithm.model.Term;
import com.example.utt.algorithm.model.YearlySession;
import com.example.utt.database.DatabaseHandler;
import com.example.utt.databinding.FragmentFirstBinding;
import com.example.utt.models.Course;
import com.example.utt.models.firebase.datamodel.CourseDataModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirstFragment extends Fragment {

    EditText editTextName;
    Button buttonAdd;

    private FragmentFirstBinding binding;
    DatabaseReference databaseCourses;
    ListView listViewCourses;
    List<Course> courseList;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseCourses = FirebaseDatabase.getInstance("https://b07-final-db5c5-default-rtdb.firebaseio.com").getReference("Course");
        editTextName = (EditText)getView().findViewById(R.id.editTextName);
        buttonAdd = (Button)getView().findViewById(R.id.buttonAdd);

        binding.buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
                        .navigate(R.id.action_FirstFragment_to_recyclerListFragment);
            }
        });
        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                addCourse();
            }
        });

        listViewCourses = (ListView)getView().findViewById(R.id.listViewCourses);
        courseList = new ArrayList<>();
    }

    // Admin create function
    private void addCourse() {
        String name = editTextName.getText().toString().trim();
        List<YearlySession> season = null;
        List<Course> prereq = null;
        // Check if search bar is empty
        if(!TextUtils.isEmpty(name)){
            String courseID = databaseCourses.push().getKey();
           //TODO: Shouldn't this be Course course = new Course(name, courseID, season, prereq);
            Course course = new Course(name, "CSCA08", season, prereq);
            // databaseCourses.child(courseID).setValue(course);
            DatabaseHandler.addCourse(course);
            // Output this message if course was successfully added to the database
            Toast.makeText(getActivity(), "Course added", Toast.LENGTH_LONG).show();
        }
        // If no course was entered into the search bar
        else{
            Toast.makeText(getActivity(), "Enter a course code", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        databaseCourses.addValueEventListener(new ValueEventListener() {
            @Override
            // Executed every time we change something in the database
            // sets courseList to the most current version of the database
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courseList.clear();
                for(DataSnapshot courseSnapshot: snapshot.getChildren()){
                    Course course = courseSnapshot.getValue(Course.class);
                    courseList.add(course);
                }

                CourseList adapter = new CourseList(getActivity(), courseList);
                listViewCourses.setAdapter(adapter);
            }
            // Executed if there is some error
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}