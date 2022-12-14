package com.example.utt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.utt.databinding.FragmentHomeBinding;
import com.example.utt.models.Student;

import java.util.Objects;

//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;


public class Home extends Fragment {

//    EditText searchTextName;
//    Button searchButton;
//    Button gotoAddCourses;

    private FragmentHomeBinding binding;
    // DatabaseReference databaseCourses;
    //private FirebaseFirestore courseCode = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        String email = Student.getInstance().getEmail();
        Objects.requireNonNull(((AppCompatActivity) requireContext()).getSupportActionBar()).setTitle("Welcome " + email);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //searchTextName = getView().findViewById(R.id.searchTextNameHome);
        //searchButton = getView().findViewById(R.id.searchButtonHome);
        //gotoAddCourses = getView().findViewById(R.id.gen_addButton);

//search button

        view.findViewById(R.id.gen_addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              Toast myToast = Toast.makeText(getActivity(), "Hello toast!", Toast.LENGTH_SHORT);
//              myToast.show();
                NavHostFragment.findNavController(Home.this)
                        .navigate(R.id.action_home_to_addFuture);//add action fragment from future courses to home


            }
        });

        view.findViewById(R.id.view_my_courses).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Home.this)
                        .navigate(R.id.action_Home_to_addPrev);

                    // User Data Testing
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//            public void HomeToGenerate() {
//        Intent in = new Intent(getActivity(), AddFutureCourses.class);
//        startActivity(in);
//            Toast myToast = Toast.makeText(getActivity(), "We will connect this today!", Toast.LENGTH_SHORT);
//            myToast.show();
//
//                NavHostFragment.findNavController(Home.this)
//                        .navigate(R.id.action_FirstFragment_to_secondFragment);
//            }

}
