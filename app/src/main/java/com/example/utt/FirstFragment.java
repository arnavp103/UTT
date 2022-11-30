package com.example.utt;

import android.os.Bundle;
import android.text.TextUtils;
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

import com.example.utt.databinding.FragmentFirstBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.utt.algorithm.model.*;
import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    EditText editTextName;
    // EditText editCourseName;
    Button buttonAdd;

    private FragmentFirstBinding binding;
    DatabaseReference databaseCourseCode;
    ListView listViewCourses;
    List<CourseTest> courseList;

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

        databaseCourseCode = FirebaseDatabase.getInstance("https://b07-final-db5c5-default-rtdb.firebaseio.com").getReference("Course Code");
        editTextName = (EditText)getView().findViewById(R.id.editCourseCode);
        // editCourseName = (EditText)getView().findViewById(R.id.courseName);

        buttonAdd = (Button)getView().findViewById(R.id.buttonAdd);

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                addCourseCode();
            }
        });
        listViewCourses = (ListView)getView().findViewById(R.id.listViewCourses);
        courseList = new ArrayList<>();
    }

    public void addCourseCode() {
        String code = editTextName.getText().toString().trim();
        String name = null;
        List<YearlySession> season = null;
        List<Course> prerequisites = null;
        // Check if search bar is empty
        if(!TextUtils.isEmpty(code)){
            String courseID = databaseCourseCode.push().getKey();
            // String courseName = databaseCourseCode.push().getKey();
            CourseTest course = new CourseTest(name, code, season, prerequisites);
            databaseCourseCode.child(courseID).setValue(course);
            //databaseCourseCode.child(courseName).setValue(course);

            // Output this message if course was successfully added to the database
            Toast.makeText(getActivity(), "Course code added", Toast.LENGTH_LONG).show();
        }
        // If no course was entered into the search bar
        else{
            Toast.makeText(getActivity(), "Enter a course code", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        databaseCourseCode.addValueEventListener(new ValueEventListener() {
            @Override
            // Executed every time we change something in the database
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courseList.clear();
                for(DataSnapshot courseSnapshot: snapshot.getChildren()){
                    CourseTest course = courseSnapshot.getValue(CourseTest.class);
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