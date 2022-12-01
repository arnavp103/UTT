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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utt.Home;
import com.example.utt.models.Course;
import com.example.utt.models.firebase.datamodel.ExcludedCourseDataModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.example.utt.databinding.FragmentAddFutureBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class addFuture extends Fragment {

    private TextView textView;
    private ArrayList<String> courseList;
    private ArrayList<String> futureList;
    private Dialog dialog;
    private String addCourse;

    private Button addButton, home;
    private ListView courseView;

//    private FirebaseFirestore courseCode = FirebaseFirestore.getInstance();

    private ArrayAdapter<String> viewAdapter;


    public addFuture() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_future_courses);

        textView = (TextView)v.findViewById(R.id.text_view);
        courseList = new ArrayList<>();

        //setting up database retrieval for courses
        courseCode = FirebaseDatabase.getInstance("https://utsc-b07-projcourses-default-rtdb.firebaseio.com/").getReference("courses");

        //arraylist that stores future student courses
        futureList = new ArrayList<>();

        //initialize buttons and search, lists
        courseView =  findViewById(R.id.courseView); //this is the layout that displays selected courses
        addButton = findViewById(R.id.addFutureCourse);
        home = findViewById(R.id.home_button);
        //gen_time = findViewById(R.id.generate_btn);

        addCourse = "";

        //initialize list view which shows the layout of the added courses
        courseView = (ListView) v.findViewById(R.id.courseView);
        viewAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, futureList);

        loadData();


        //goes to home button
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                NavHostFragment.findNavController(addFuture.this)
                        .navigate(R.id.action_addFuture_to_Home);//add action fragment from future courses to home


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
                                futureList.remove(to_remove);
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

                ArrayAdapter<String> course_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, courseList);

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


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (addCourse.equals("")) {
                    Toast.makeText(getContext(), "Select a valid course!", Toast.LENGTH_LONG).show();
                } else if (!(addCourse.isEmpty()) && futureList.contains(addCourse) == false) {
                    futureList.add(addCourse);
                    //viewAdapter.notifyDataSetChanged();
                    //Log.i("RM", futureList.get(futureList.size()));
                    courseView.setAdapter(viewAdapter);


                }


            }
        });


    }

    public void loadCourses() {
        DocumentReference ref = courseCode.collection("courses").document();

        courseCode.collection("courses")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String course = ((document.toObject(Course.class)).getCode() + " ");

                            courseList.add(course);

                            //Log.d(TAG, p.getName() + " " + p.getAge());
                        }

                    }
                });

    }
    private void loadData() {

        courseCode.addValueEventListener(new ValueEventListener() {
            @Override
            // Executed every time we change something in the database
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot courseSnapshot: snapshot.getChildren()){
                    String course = (courseSnapshot.getValue(ExcludedCourseDataModel.class)).getCode() + " ";
                    courseList.add(course);
                    Log.i("RM", course);

                }


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