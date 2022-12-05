package com.example.utt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utt.databinding.FragmentRecyclerListBinding;
import com.example.utt.models.Course;

import java.util.ArrayList;
import java.util.Map;


public class  RecyclerListFragment extends Fragment {

    private FragmentRecyclerListBinding binding;

    private void loadAdapter() {
        RecyclerView courseRV = getView().findViewById(R.id.recycler_list);

        // Here, we have created new array list and added data to it
        ArrayList<CourseModel> courseModelArrayList = new ArrayList<>();
//        ArrayList<String> sessionList = new ArrayList<>();
//        ArrayList<String> prereqsList = new ArrayList<>();
//        sessionList.add("Fall");
//        sessionList.add("Winter");
//        courseModelArrayList.add(new CourseModel("CSCA08",
//                "CSCA08 Name", sessionList));
//        sessionList = new ArrayList<>();
//        prereqsList = new ArrayList<>();
//        sessionList.add("Winter");
//        sessionList.add("Summer");
//        prereqsList.add("CSCA08");
//        courseModelArrayList.add(new CourseModel("CSCA48",
//                "Yummy Yummy Paco Tacos", sessionList, prereqsList));
//        sessionList = new ArrayList<>();
//        sessionList.add("Fall");
//        sessionList.add("Winter");
//        courseModelArrayList.add(new CourseModel("CSCA67", "Counting",
//                sessionList));
//        sessionList = new ArrayList<>();
//        prereqsList = new ArrayList<>();
//        sessionList.add("Fall");
//        sessionList.add("Summer");
//        prereqsList.add("CSCA48");
//        courseModelArrayList.add(new CourseModel("CSCB07", "orz",
//                sessionList, prereqsList));
//        sessionList = new ArrayList<>();
//        prereqsList = new ArrayList<>();
//        sessionList.add("Fall");
//        sessionList.add("Summer");
//        prereqsList.add("CSCA48");
//        prereqsList.add("CSCA67");
//        courseModelArrayList.add(new CourseModel("CSCB36", "BIG Result",
//                sessionList, prereqsList));
//        sessionList = new ArrayList<>();
//        prereqsList = new ArrayList<>();
//        sessionList.add("Winter");
//        sessionList.add("Summer");
//        prereqsList.add("CSCB36");
//        courseModelArrayList.add(new CourseModel("CSCB63", "Idk",
//                sessionList, prereqsList));
        Map<String, Course> courses = Course.getCourses();

        for (Course course : courses.values()) courseModelArrayList.add(new CourseModel(course));

        // we are initializing our adapter class and passing our arraylist to it.
        CourseAdapter courseAdapter = new CourseAdapter(getContext(), courseModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        courseRV.setLayoutManager(linearLayoutManager);
        courseRV.setAdapter(courseAdapter);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentRecyclerListBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadAdapter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}