package com.example.utt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.utt.database.DatabaseHandler;
import com.example.utt.databinding.FragmentFirstBinding;
import com.example.utt.models.Course;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.utt.algorithm.model.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FirstFragment extends Fragment {

    EditText editTextName;
    EditText editCourseName;
    Button buttonAdd;

    private FragmentFirstBinding binding;
    DatabaseReference databaseCourseCode;
    ListView listViewCourses;
    List<Course> courseList;
    TextView sessionOffering;
    boolean[] selectedSession;
    ArrayList<Integer> sessionList = new ArrayList<>();
    String[] sessionArray = {"Winter", "Summer", "Fall"};

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

        databaseCourseCode = FirebaseDatabase.getInstance("https://b07-final-db5c5-default-rtdb.firebaseio.com").getReference("Course");
        editTextName = (EditText)getView().findViewById(R.id.editCourseCode);
        editCourseName = (EditText)getView().findViewById(R.id.editCourseName);

        // Create multiple select option for admin to select the session offerings
        sessionOffering = getView().findViewById(R.id.sessionoffering);
        //Initialize selected session array
        selectedSession = new boolean[sessionArray.length];
        sessionOffering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                // Create new alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select session offering(s)");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(sessionArray, selectedSession, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b){
                            // Check if checkbox is selected
                            sessionList.add(i);
                            Collections.sort(sessionList);
                        }
                        else{
                            // If checkbox is unselected, remove position from sessionList
                            sessionList.remove(i);
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder sessionOutput = new StringBuilder();
                        for(int j = 0; j < sessionList.size(); j++) {
                            sessionOutput.append(sessionArray[sessionList.get(j)]);
                            // When j value is not equal to the sessionList size-1, add comma
                            if (j != sessionList.size() - 1) {
                                sessionOutput.append(", ");
                            }
                        }
                        sessionOffering.setText(sessionOutput.toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j = 0; j < selectedSession.length; j++){
                            // Remove all selections
                            selectedSession[j] = false;
                            // Clear session list
                            sessionList.clear();
                            // Clear text view value
                            sessionOffering.setText("");
                        }
                    }
                });
                // Show dialog
                builder.show();
            }
        });

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
        String name = editCourseName.getText().toString().trim();
        List<YearlySession> season = new ArrayList<>();
        for(int i = 0; i < sessionList.size(); i++){
            YearlySession tempSeasons = null;
            if(sessionList.get(i) == 0){
                tempSeasons = new YearlySession(Term.WINTER);
            }
            else if(sessionList.get(i) == 1){
                tempSeasons = new YearlySession(Term.SUMMER);
            }
            else if(sessionList.get(i) == 2) {
                tempSeasons = new YearlySession(Term.FALL);
            }
            season.add(tempSeasons);
        }

        List<Course> prerequisites = null;
        // Check if search bar is empty
        if(!TextUtils.isEmpty(code) && !TextUtils.isEmpty(name)){
            String courseID = databaseCourseCode.push().getKey();
            Course course = new Course(name, code, season, prerequisites);
            Boolean exist = false;
            // Check if there are duplicates of a course created
            for(int i = 0; i < courseList.size(); i++){
                if(course.equals(courseList.get(i))){
                    Toast.makeText(getActivity(), "Course already created", Toast.LENGTH_LONG).show();
                    exist = true;
                    break;
                }
            }
            if(exist == false) {
                DatabaseHandler.addCourse(course);
//                databaseCourseCode.child(courseID).setValue(course);
//                for(YearlySession session : season){
//                    databaseCourseCode.child(courseID).child(session.toString()).setValue(true);
//                }
                // Output this message if course was successfully added to the database
                Toast.makeText(getActivity(), "Course added", Toast.LENGTH_LONG).show();
            }
        }
        // If no course was entered into the search bar
        else{
            Toast.makeText(getActivity(), "Enter course information", Toast.LENGTH_LONG).show();
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