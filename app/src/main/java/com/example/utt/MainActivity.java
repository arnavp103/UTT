package com.example.utt;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.utt.algorithm.model.CourseScheduling;
import com.example.utt.algorithm.model.SearchAlgorithm;
import com.example.utt.algorithm.model.Term;
import com.example.utt.database.DatabaseHandler;
import com.example.utt.databinding.ActivityMainBinding;
import com.example.utt.models.Course;
import com.example.utt.models.CourseEventListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DatabaseHandler.initialise();
        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /// TESTING STUFF

        CourseEventListener render = new CourseEventListener() {
            @Override
            public void onCourseAdded(Course course) {
                Log.d("Render", "Course Added: " + course);
                if (Course.getCourse("CSCA08") != null) {
                    Log.d("NOOOOO", "FAILrrrrURE");
                    CourseEventListener single = new CourseEventListener() {
                        @Override
                        public void onCourseAdded(Course course) {

                        }

                        @Override
                        public void onCourseChanged(Course course) {
                            Log.d("OK", course.getCode());
                        }

                        @Override
                        public void onCourseRemoved(Course course) {

                        }
                    };
                     //Attach a test listener to CSCA08
                    Course.getCourse("CSCA08").addCourseListener(single);
                } else {
                    Log.d("DNE", "Failed to find CSCA08");
                }
            }

            @Override
            public void onCourseChanged(Course course) {
                Log.d("Render", "Course Changed" + course.toString());
            }

            @Override
            public void onCourseRemoved(Course course) {
                Log.d("Render", "Course Removed" + course.toString());
            }
        };

         Course.addListener(render);
         // Course.getCourse("CSCA08");

//        ArrayList<CourseScheduling> coursesTaken = new ArrayList<CourseScheduling>();
//        CourseScheduling csca08_scheduling = new CourseScheduling(csca08.getName(), csca08.getCode(), csca08.getSessionOffering(), csca08.getPrerequisites());
//        CourseScheduling csca48_scheduling = new CourseScheduling(csca48.getName(), csca48.getCode(), csca48.getSessionOffering(), csca48.getPrerequisites());
//        coursesTaken.add(csca08_scheduling);
//        coursesTaken.add(csca48_scheduling);
//
//        List<Course> targets = new ArrayList<>();
//        targets.add(cscb36);
//        SearchAlgorithm search = new SearchAlgorithm(coursesTaken);
//        search.findBeginningNodes(targets);
//
//        List<CourseScheduling> result = search.search(Term.FALL, 2022);
//        assertEquals("CSCA67", result.get(0).getCode());
//        assertEquals(Term.WINTER, result.get(0).sessionBeingTaken.term);
//        assertEquals(2023, result.get(0).sessionBeingTaken.year);
//
//        assertEquals("CSCB36", result.get(1).getCode());
//        assertEquals(Term.SUMMER, result.get(1).sessionBeingTaken.term);
//        assertEquals(2023, result.get(1).sessionBeingTaken.year);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}