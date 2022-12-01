//package com.example.utt;
//
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import androidx.fragment.app.Fragment;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.navigation.fragment.NavHostFragment;
//
//import com.example.utt.algorithm.model.YearlySession;
//import com.example.utt.databinding.FragmentSecondBinding;
//import com.example.utt.databinding.FragmentTimelineBinding;
//import com.example.utt.models.Course;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//class Timeline extends Fragment {
//
//    private FragmentTimelineBinding binding;
//    private DatabaseReference databaseCourse;
//
//    @Override
//    public View onCreateView(
//            LayoutInflater inflater, ViewGroup container,
//            Bundle savedInstanceState
//    ) {
//
//        binding = FragmentTimelineBinding.inflate(inflater, container, false);
//        return binding.getRoot();
//
//    }
//
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//
////        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                NavHostFragment.findNavController(SecondFragment.this)
////                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
////            }
////        });
//    }
//
//    // Retrieve data from database
//        CourseEventListener listener = new CourseEventListener() {
//            @Override
//            public void onCourseAdded(Course course) {
//                courseList.clear();
//
//                for (Course courseObject : Course.getCourses().values()) {
//                    courseList.add(courseObject);
//                }
//                CourseList adapter = new CourseList(getActivity(), courseList);
//                listViewCourses.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCourseChanged(Course course) {
//
//            }
//
//            @Override
//            public void onCourseRemoved(Course course) {
//
//            }
//        };
//
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//
//}