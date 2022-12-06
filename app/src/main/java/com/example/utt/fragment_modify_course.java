package com.example.utt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.utt.algorithm.model.Term;
import com.example.utt.algorithm.model.YearlySession;
import com.example.utt.database.DatabaseHandler;
import com.example.utt.databinding.FragmentModifyCourseBinding;
import com.example.utt.models.Course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_modify_course#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_modify_course extends Fragment {
    private static String courseKey = "";
    private static String newCourseCode = "";
    private static String newCourseName = "";
    private static boolean[] selectedSession;
    private static final ArrayList<Integer> sessionList = new ArrayList<>();
    EditText editTextName;
    EditText editCourseName;
    Button prereqText;
    Button buttonModify;
    List<Course> courseList = new ArrayList<>();
    TextView sessionOffering;

    FragmentModifyCourseBinding binding;

    String[] sessionArray = {"Winter", "Summer", "Fall"};

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_COURSECODE = "CourseCode";

    // TODO: Rename and change types of parameters
    private static String courseCode;

    public fragment_modify_course() {
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
    public static fragment_modify_course newInstance(String courseCode) {
        fragment_modify_course fragment = new fragment_modify_course();
        Bundle args = new Bundle();
        args.putString(ARG_COURSECODE, courseCode);

        // Assume that the hidden course is the current course so yeah.


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            courseCode = getArguments().getString(ARG_COURSECODE);
            if (selectedSession == null) selectedSession = new boolean[sessionArray.length];
            loadCourseData();
//            Log.d("Setting course code: ", courseCode + " <- ");
        }
    }

    private void loadCourseData() {
        desiredCourse = Course.getCourse(courseCode);
        Log.d("Loading Course Data", desiredCourse.toString());
        newCourseName = desiredCourse.getName();
        newCourseCode = desiredCourse.getCode();
        courseKey = desiredCourse.getKey();
        AdminHome.courses.clear();
        sessionList.clear();
        for (Course prereq : desiredCourse.getPrerequisites()) AdminHome.courses.add(prereq);
        for (YearlySession session : desiredCourse.getSessionOffering()) {
            selectedSession[session.getTerm().getTerm()] = true;
            sessionList.add(session.getTerm().getTerm());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentModifyCourseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextName = (EditText)getView().findViewById(R.id.editCourseCode);
        editCourseName = (EditText)getView().findViewById(R.id.editCourseName);

        // Create multiple select option for admin to select the session offerings
        sessionOffering = getView().findViewById(R.id.sessionoffering);
        //Initialize selected session array
        if (selectedSession == null) selectedSession = new boolean[sessionArray.length];
        prereqText = (Button)getView().findViewById((R.id.prereq));

        populateFields();
//        listViewCourses = (ListView)getView().findViewById(R.id.listViewCourses);
        sessionOffering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create new alert dialog to allow admin to select session offerings
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select session offering(s)");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(sessionArray, selectedSession, new DialogInterface.OnMultiChoiceClickListener() {
                    int j = 0;
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        int counter = 0;
                        counter++;
                        if (b && (j % counter == 0)) {
                            // Check if checkbox is selected
                            sessionList.add(i);
                            Collections.sort(sessionList);
                        } else {
                            // If checkbox is unselected, remove position from sessionList
                            if (i < 0) i = 0;
                            if (i >= sessionList.size()) i = sessionList.size() - 1;
                            sessionList.remove(i);
                        }
                        j++;
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        displaySessions();
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
                        for (int j = 0; j < selectedSession.length; j++) {
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

        binding.prereq.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("HIDDEN_COURSE", desiredCourse.getCode());
                for (Course c : desiredCourse.getPrerequisites()) {
                    if(c.getCode() == "Missing"){
                        AdminHome.courses.remove(c);
                    }
                }

                NavHostFragment.findNavController(fragment_modify_course.this)
                        .navigate(R.id.action_fragment_modify_course_to_selectPrereqs2, bundle);
            }
        });

        binding.buttonModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                modifyCourseCode(desiredCourse.getKey());
                NavHostFragment.findNavController(fragment_modify_course.this)
                        // .navigate(R.id.action_FirstFragment_to_SecondFragment);
                        .navigate(R.id.action_FragmentModifyCourse_to_adminHome);

            }
        });
    }

    public void modifyCourseCode(String key) {
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

        List<Course> prerequisites = AdminHome.courses;
        // Check if search bar is empty
        if(!TextUtils.isEmpty(code) && !TextUtils.isEmpty(name) && !sessionList.isEmpty()){
            // String courseID = databaseCourseCode.push().getKey();
            Course course = new Course(name, code, season, prerequisites);
            course.setKey(key);
            Boolean exist = false;
            // Check if there are duplicates of a course created
            // Modification: Ok if ID is the same
//            Toast.makeText(getActivity(), course.getCode(),Toast.LENGTH_LONG).show();
            Course existingCourse = Course.getCourse(code);
            if(existingCourse != null && !existingCourse.getKey().equals(key)){
                Toast.makeText(getActivity(), "Course already created",Toast.LENGTH_LONG).show();
                exist = true;
            }
            if(exist == false) {
                // Add the course to the database
                DatabaseHandler.updateCourse(course);
                Toast.makeText(getActivity(), "Course added", Toast.LENGTH_LONG).show();
            }
        }
        // If no course was entered into the search bar
        else{
            Toast.makeText(getActivity(), "Enter course information", Toast.LENGTH_LONG).show();
        }
    }


    private void clearFields() {
        newCourseCode = "";
        newCourseName = "";
        selectedSession = new boolean[sessionArray.length];
        sessionList.clear();
        AdminHome.courses.clear();
        populateFields();
        prereqText.setText(R.string.select_prerequisites);
    }

    private void populateFields() {
        editTextName.setText(newCourseCode);
        editCourseName.setText(newCourseName);
        displaySessions();
        if (AdminHome.courses == null) AdminHome.courses = new ArrayList<>();
        prereqText.setText(R.string.select_prerequisites);
        prereqText.setText(prereqText.getText() + " (" + AdminHome.courses.size() + ")");
    }

    private void displaySessions() {
        // Output selected session offerings
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


    @Override
    public void onDestroy() {
        AdminHome.courses.clear();
        super.onDestroy();
    }
}