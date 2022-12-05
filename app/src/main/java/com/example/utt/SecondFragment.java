////package com.example.utt;
////
////import android.os.Bundle;
////import android.text.TextUtils;
////import android.view.LayoutInflater;
////import android.view.View;
////import android.view.ViewGroup;
////import android.widget.EditText;
////import android.widget.ListView;
////import android.widget.Toast;
////
////import androidx.annotation.NonNull;
////import androidx.fragment.app.Fragment;
////import androidx.navigation.fragment.NavHostFragment;
////
////import com.example.utt.algorithm.model.YearlySession;
////import com.example.utt.databinding.FragmentSecondBinding;
////import com.example.utt.models.Course;
////import com.google.firebase.database.DatabaseReference;
////
////import java.util.ArrayList;
////import java.util.List;
////
////public class SecondFragment extends Fragment {
////
////    private FragmentSecondBinding binding;
////    DatabaseReference databaseCourseName;
////    ListView listCourseName;
////    List<CourseTest> courseList;
////    EditText editCourseName;
////
////    @Override
////    public View onCreateView(
////            LayoutInflater inflater, ViewGroup container,
////            Bundle savedInstanceState
////    ) {
////
////        binding = FragmentSecondBinding.inflate(inflater, container, false);
////        return binding.getRoot();
////
////    }
////
////    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
////        super.onViewCreated(view, savedInstanceState);
////
////        binding.buttonBack.setOnClickListener(view1 -> NavHostFragment.findNavController(SecondFragment.this)
////                .navigate(R.id.action_SecondFragment_to_Home));
////        binding.buttonAddName.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) { addCourseName(); }
////        });
////        listCourseName = (ListView)getView().findViewById(R.id.listCourseName);
////        courseList = new ArrayList<>();
////    }
////
////    public void addCourseName() {
////        String code = null;
////        String name = editCourseName.getText().toString().trim();
////        List<YearlySession> season = null;
////        List<Course> prerequisites = null;
////        // Check if search bar is empty
////        if(!TextUtils.isEmpty(code)){
////            // String courseID = databaseCourseName.push().getKey();
////            String courseName = databaseCourseName.push().getKey();
////            CourseTest course = new CourseTest(name, code, season, prerequisites);
////            // databaseCourseName.child(courseID).setValue(course);
////            databaseCourseName.child(courseName).setValue(course);
////
////            // Output this message if course was successfully added to the database
////            Toast.makeText(getActivity(), "Course name added", Toast.LENGTH_LONG).show();
////        }
////        // If no course was entered into the search bar
////        else{
////            Toast.makeText(getActivity(), "Enter a course name", Toast.LENGTH_LONG).show();
////        }
////    }
////
////    @Override
////    public void onDestroyView() {
////        super.onDestroyView();
////        binding = null;
////    }
////
////}
//
//        yearList = new ArrayList<>();
//        for (Integer year : output.keySet()) {
//            courseModelArrayList.add(new TimelineCourseModel(year, output.get(year)));
////            yearList.add(year, output.keySet().toString());
//        }
//        // we are initializing our adapter class and passing the course list to it.
//        timelineCourseAdapter courseAdapter = new timelineCourseAdapter(getContext(), courseModelArrayList);
//        // Create vertical list in recycler view
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        timelineView.setLayoutManager(linearLayoutManager);
//        timelineView.setAdapter(courseAdapter);
//    }
//
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        courseSchedulingList = new ArrayList<>();
//        courseOutputList = new ArrayList<>();
//        wantedCourses = new ArrayList<>();
//        studentCourses = new ArrayList<>();
//        result = new ArrayList<>();
//
//        // Testing -----------------------------------
//        List<Course> targets = new ArrayList<Course>();
//        targets.add(Course.getCourse("CSCB36"));
//        SearchAlgorithm search1 = new SearchAlgorithm((ArrayList<CourseScheduling>) courseOutputList);
//        search1.findBeginningNodes(targets);
//        List<CourseScheduling> result1 = search1.search(Term.FALL, 2022);
//        for (CourseScheduling c : result1) Log.d("HELLO", c.getCode() + c.sessionBeingTaken.year);
//        // ------------------------------------------
//
//
//
//        // Retrieve student's courses that they took already
//        DatabaseHandler.getUser("nobeans@mail.utoronto.ca", "0beans", new Listener<User>() {
//        @Override
//        public void onSuccess(String data, List<User> objectModel) {
//            DatabaseHandler.getStudentData(objectModel.get(0).getId(), new Listener<String>() {
//                @Override
//                public void onSuccess(String data, List<String> objectModel) {
//                    for (String courseCode : objectModel) {
//                        studentCourses.add(Course.getCourse(courseCode));
//                    }
//                    Log.d("COURSES", String.valueOf(studentCourses));
//                }
//
//                @Override
//                public void onFailure(String data) {
//
//                }
//
//                @Override
//                public void onComplete(String data) {
//
//                }
//            });
//        }
//
//        @Override
//        public void onFailure(String data) {
//
//        }
//
//        @Override
//        public void onComplete(String data) {
//
//        }
//        });
//
//        // Use Search algorithm
//        // Created a list of courses of type CourseScheduling
//        for(int i=0; i<studentCourses.size(); i++){
//            CourseScheduling tempCourse = new CourseScheduling(studentCourses.get(i).getName(),
//                    studentCourses.get(i).getCode(), studentCourses.get(i).getSessionOffering(),
//                    studentCourses.get(i).getPrerequisites());
//            courseSchedulingList.add(i, tempCourse);
//        }
//        SearchAlgorithm search = new SearchAlgorithm(courseSchedulingList);
//        wantedCourses.add(Course.getCourse("CSCC63"));
//        search.findBeginningNodes(wantedCourses);
//        result = search.search(Term.FALL, 2022);
//        // Implements Recycler view
//
//        StringBuilder output = new StringBuilder();
//
//        for (CourseScheduling cd : result) {
//            output.append(cd.getCode())
//                    .append(": ")
//                    .append(cd.sessionBeingTaken.term)
//                    .append(" ")
//                    .append(cd.sessionBeingTaken.year)
//                    .append("\n");
//        }
//        Log.d("INFORMATION", output.toString());
//        loadAdapter(result);
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//
//}