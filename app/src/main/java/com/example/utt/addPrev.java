package com.example.utt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.utt.databinding.FragmentAddFutureBinding;
import com.example.utt.models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class addPrev extends Fragment {

    private TextView textView;
    private ArrayList<String> allCourses;
    private ArrayList<String> pastList;
    private Dialog dialog;

    //these fields are to store the code, session, and year of the course previously taken
    private String addCourse;
//    private String addSession;
//    private String addYear;


    private Button addButton, home, save;
    private ListView courseView;

//    private RadioButton fall;
//    private RadioButton winter;
//    private RadioButton summer;
    private String student_id;


    private DatabaseReference courseCode;
    private DatabaseReference studentCode;
    private FragmentAddFutureBinding binding;


    private ArrayAdapter<String> viewAdapter; //for the spinner options
    private ArrayAdapter<String> course_adapter; //for the layout
    private Toast myToast;


    public addPrev() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        View v = inflater.inflate(R.layout.fragment_add_prev, container, false);

        textView = (TextView) v.findViewById(R.id.text_view); //this is the layout of our past courses
        allCourses = new ArrayList<>();

        //setting up database retrieval for courses
        courseCode = FirebaseDatabase.getInstance("https://utsc-b07-projcourses-default-rtdb.firebaseio.com/").getReference("courses");
        studentCode = FirebaseDatabase.getInstance("https://utsc-b07-projcourses-default-rtdb.firebaseio.com/").getReference("students");


        //arraylist that stores past student courses
        pastList = new ArrayList<>();
        //pastList.add("CSCA48");


        //initialize buttons and search, lists
        addButton = (Button)v.findViewById(R.id.addFutureCourse);
        home = (Button)v.findViewById(R.id.home_button);
        save = (Button) v.findViewById(R.id.save_button);

        //initialize our strings
        addCourse = "";


        //Eric passes the student id
        //Student student = new Student();
        student_id = "";
//        addSession = "";
//        addYear = "";


        //initialize list view
        courseView = (ListView) v.findViewById(R.id.courseView);

        course_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, allCourses);
        viewAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, pastList);

        loadPreviousCourses();

        courseView.setAdapter(viewAdapter);
        //viewAdapter.notifyDataSetChanged();


        loadData();




        //goes to home button
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                NavHostFragment.findNavController(addPrev.this)
                        .navigate(R.id.action_addPrev_to_Home);//add action fragment from future courses to home


            }
        });



        //remove item function
        courseView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                final int to_remove = i;

                new AlertDialog.Builder(getContext())
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure you want to delete course from list?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                pastList.remove(to_remove);
                                viewAdapter.notifyDataSetChanged();

                            }
                        })

                        .setNegativeButton("No", null)
                        .show();

                //return true;
            }
        });


        //spinner filter + functionality

        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                //Initialize dialog
                dialog = new Dialog(getContext());

                //set custom dialog
                dialog.setContentView((R.layout.dialog_searchable_spinner));

                //set custom height and width
                dialog.getWindow().setLayout(650, 800);

                //set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();


                //Initialize and assign variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                //Initialize array adaptor
                //viewAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, pastList);


                listView.setAdapter(course_adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//if changed, filter out non-similar results
                        //Filter array list
                        course_adapter.getFilter().filter(charSequence);


                    }

                    @Override
                    public void afterTextChanged(Editable editable) {


                    }
                });


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //when item selected from list
                        //set selected item on text view
                        addCourse = (String) course_adapter.getItem(i);


                        textView.setText(course_adapter.getItem(i));

                        dialog.dismiss();
                    }
                });
            }

        });



        //adds course
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (addCourse.equals("")) {
                    Toast.makeText(getContext(), "Select a valid course!", Toast.LENGTH_LONG).show();

                }else if (!(addCourse.isEmpty()) && !(pastList.contains(addCourse))) {
                    pastList.add(addCourse);
                    //courseView.notifyDataSetChanged();
                    courseView.setAdapter(viewAdapter);


                }


            }
        });

        //save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Student get ID
                //save to database
                //save to field


            }
        });



        return v;

    }
    private void loadData() {

        courseCode.addValueEventListener(new ValueEventListener() {
            @Override
            // Executed every time we change something in the database
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot courseSnapshot: snapshot.getChildren()){
                    String course = (courseSnapshot.getValue(Course.class)).code + " ";
                    allCourses.add(course);
                    //Log.i("RM", course);

                }


            }
            // Executed if there is some error
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //still need to work on this, have to get student id
    //I think I need to have the field coursesTaken in student equal to pastList
    //the same goes for future list
    private void loadPreviousCourses() {

        studentCode.addValueEventListener(new ValueEventListener() {
            @Override
            // Executed every time we change something in the database
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //pastList.clear();
                Map<String, String> result = new HashMap<>();

                for(DataSnapshot courseSnapshot: snapshot.getChildren()){
                    //String id = courseSnapshot.getKey();
                    System.out.println(student_id);


                    //if (id.equals(student_id)) {
                    for (DataSnapshot past: courseSnapshot.getChildren()){
                        pastList.add((String) past.getValue());
                    }
                    viewAdapter.notifyDataSetChanged();
                    break;
                    //}
//                    if (id.equals(student_id)){
//                        pastList = (ArrayList<String>) snapshot.getValue();
//text view for headers
//                    }

                }
                //System.out.println(pastList);






            }
            // Executed if there is some error
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//add in some toast notification
            }
        });
    }







    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}