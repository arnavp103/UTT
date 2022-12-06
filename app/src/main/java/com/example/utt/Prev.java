package com.example.utt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.utt.databinding.FragmentAddFutureBinding;
import com.example.utt.models.Course;
import com.example.utt.models.firebase.datamodel.ExcludedCourseDataModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Prev extends Fragment {

    private TextView textView;
    private ArrayList<String> allCourses;
    private ArrayList<String> pastList;
    //private ArrayList<String> pastListTotal;
    private Dialog dialog;

    //these fields are to store the code, session, and year of the course previously taken
    private String addCourse;
    private String addCourseKey;
    private ImageButton info;


    private Button addButton, home;
    private ListView courseView;


    private String student_id;


    private DatabaseReference courseCode;
    private DatabaseReference studentCode;
    private FragmentAddFutureBinding binding;


    private ArrayAdapter<String> viewAdapter; //for the spinner options
    private ArrayAdapter<String> course_adapter; //for the layout
    private Toast myToast;


    public Prev() {
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
       // pastListTotal = new ArrayList<>();


        //initialize buttons and search, lists
        addButton = (Button)v.findViewById(R.id.addPrevCourse);
        home = (Button)v.findViewById(R.id.home_button);
        info = v.findViewById(R.id.infoButton);

        //initialize our strings
        addCourse = "";
        addCourseKey = "";



        student_id = CookieLogin.getInstance().getUserId(requireContext());
        //student_id = "-NIK8NoD7yfgucDNOVs7";



        //initialize list view
        courseView = (ListView) v.findViewById(R.id.courseView);

        course_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, allCourses);
        viewAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, pastList);


        loadPreviousCourses();
        //pastList.add("hi");


        //updates what's in the view for past courses

        //viewAdapter.notifyDataSetChanged();
        //viewAdapter.notifyDataSetChanged();


        loadData();

        courseView.setAdapter(viewAdapter);





        //goes to home button
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                NavHostFragment.findNavController(Prev.this)
                        .navigate(R.id.action_addPrev_to_Home);//add action fragment from future courses to home


            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {

                Toast.makeText(getContext(), "Click on the item to remove it!", Toast.LENGTH_LONG).show();

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
                                String code = pastList.get(to_remove);
                                //pastList.get(to_remove);

                                //pastList.remove(to_remove);
                                //must remove from database
//                                DatabaseHandler.removeCourse(com.example.utt.models.Student.getCourse(code));

                                addCourseKey = Course.getCourse(code.trim()).getKey();
                                deleteCourse(addCourseKey);



                            }
                        })

                        .setNegativeButton("No", null)
                        .show();

                //return true;
            }
        });


        //spinner filter + functionality
        CourseEventListener lis = new CourseEventListener() {
            @Override
            public void onCourseAdded(Course course) {
                loadData();

            }

            @Override
            public void onCourseChanged(Course course) {
                loadData();

            }

            @Override
            public void onCourseRemoved(Course course) {
                //loadData();

                //if the course that was removed is in the list
                //loadData again,
                //remove from pastList and or pastListTotal
                if (pastList.contains(course.getCode())){
                    loadData();
                    Toast.makeText(getContext(), "We removed a course you added as it is no longer available!", Toast.LENGTH_LONG).show();
                    String courseKeyDelete = course.getKey();
                    deleteCourse(courseKeyDelete);


                }

            }
        };

        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                loadData();

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
                course_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, allCourses);

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
                        textView.setTextColor(Color.WHITE);

                        listView.setAdapter(course_adapter);
                        course_adapter.notifyDataSetChanged();
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
                } else if (pastList.contains(addCourse)){
                    Toast.makeText(getContext(), "Select a course you haven't taken!", Toast.LENGTH_LONG).show();
                }
                else if (pastList.contains(addCourse)){
                    Toast.makeText(getContext(), "Select a course that you have not selected!", Toast.LENGTH_LONG).show();
                }else if (!(addCourse.isEmpty()) && !(pastList.contains(addCourse))) {
                    pastList.add(addCourse);

                    addCourseKey = Course.getCourse(addCourse.trim()).getKey();
                    //courseView.notifyDataSetChanged();
                    //viewAdapter.notifyDataSetChanged();


                    studentCode.child(student_id).child(addCourseKey).setValue(addCourse);
                    courseView.setAdapter(viewAdapter);

                    //DatabaseHandler.addCourse(com.example.utt.models.Course.getCourse(addCourse));


                }


            }
        });



        return v;

    }

    private void loadData() {

        courseCode.addValueEventListener(new ValueEventListener() {
            @Override

            // Executed every time we change something in the database
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allCourses.clear();
                for(DataSnapshot courseSnapshot: snapshot.getChildren()){
                    String course = (courseSnapshot.getValue(ExcludedCourseDataModel.class)).getCode() + " ";
                    allCourses.add(course);


                    //Log.i("RM", course);

                }
                course_adapter.notifyDataSetChanged();



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
                pastList.clear();


                for(DataSnapshot courseSnapshot: snapshot.getChildren()){
                    String id = courseSnapshot.getKey();
                    //System.out.println(student_id);
                    System.out.println(id);



                    if (id.equals(student_id)) {
                        for (DataSnapshot past: courseSnapshot.getChildren()){
                            if (!past.getKey().equals("None")) {
                                pastList.add((String) past.getValue());
                            }

                        }
                        System.out.println(pastList);
                        viewAdapter.notifyDataSetChanged();
                        break;
                    }


                }
                //System.out.println(pastListTotal);



            }
            // Executed if there is some error
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//add in some toast notification
            }
        });
    }

    private void deleteCourse(String courseKey){
        //student_id = CookieLogin.getInstance().getUserId(requireContext());
        studentCode.child(student_id).child(courseKey).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {}
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("RM", "Failure: " + e);
                    }
                });

        viewAdapter.notifyDataSetChanged();
    }







    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
