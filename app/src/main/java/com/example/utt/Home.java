package com.example.utt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;


import com.example.utt.databinding.FragmentHomeBinding;

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


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //searchTextName = getView().findViewById(R.id.searchTextNameHome);
        //searchButton = getView().findViewById(R.id.searchButtonHome);
        //gotoAddCourses = getView().findViewById(R.id.gen_addButton);

        view.findViewById(R.id.searchTextNameHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Home.this)
                        .navigate(R.id.action_SecondFragment_to_secondFragment);

                //start activity for searchbar

            }
        });
        view.findViewById(R.id.searchButtonHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Home.this)
                        .navigate(R.id.action_FirstFragment_to_recyclerListFragment);

                //start activity for searchbar

            }
        });

        view.findViewById(R.id.gen_addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast myToast = Toast.makeText(getActivity(), "Hello toast!", Toast.LENGTH_SHORT);
//                myToast.show();

                    // User Data Testing

                    HomeToGenerate();

            }
        });






    }
    public void HomeToGenerate()
    {
//        Intent in = new Intent(getActivity(), AddFutureCourses.class);
//        startActivity(in);
//            Toast myToast = Toast.makeText(getActivity(), "We will connect this today!", Toast.LENGTH_SHORT);
//            myToast.show();

        NavHostFragment.findNavController(Home.this)
                .navigate(R.id.action_FirstFragment_to_secondFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}