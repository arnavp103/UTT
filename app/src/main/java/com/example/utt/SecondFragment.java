package com.example.utt;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.utt.algorithm.model.YearlySession;
import com.example.utt.databinding.FragmentSecondBinding;
import com.example.utt.models.Course;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    DatabaseReference databaseCourseName;
    ListView listCourseName;
    List<CourseTest> courseList;
    EditText editCourseName;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonBack.setOnClickListener(view1 -> NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment));
        binding.buttonAddName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { addCourseName(); }
        });
        listCourseName = (ListView)getView().findViewById(R.id.listCourseName);
        courseList = new ArrayList<>();
    }

    public void addCourseName() {
        String code = null;
        String name = editCourseName.getText().toString().trim();
        List<YearlySession> season = null;
        List<Course> prerequisites = null;
        // Check if search bar is empty
        if(!TextUtils.isEmpty(code)){
            // String courseID = databaseCourseName.push().getKey();
            String courseName = databaseCourseName.push().getKey();
            CourseTest course = new CourseTest(name, code, season, prerequisites);
            // databaseCourseName.child(courseID).setValue(course);
            databaseCourseName.child(courseName).setValue(course);

            // Output this message if course was successfully added to the database
            Toast.makeText(getActivity(), "Course name added", Toast.LENGTH_LONG).show();
        }
        // If no course was entered into the search bar
        else{
            Toast.makeText(getActivity(), "Enter a course name", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}