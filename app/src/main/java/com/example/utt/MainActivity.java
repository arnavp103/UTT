package com.example.utt;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.utt.algorithm.model.CourseScheduling;
import com.example.utt.algorithm.model.SearchAlgorithm;
import com.example.utt.algorithm.model.Term;
import com.example.utt.database.DatabaseHandler;
import com.example.utt.databinding.ActivityMainBinding;
import com.example.utt.models.Course;
import com.example.utt.models.CourseEventListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
//    Fragment fragmentInstance = getSupportFragmentManager().findFragmentById(R.id.frameLayout);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Integer fragId = fragmentInstance.getId();
//        Log.d("Current Frag", fragId.toString());
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DatabaseHandler.initialise();
        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
        navController.addOnDestinationChangedListener((firstArg, destination, thirdArg) -> {
            if(destination.getId() == R.id.LoginFragment) {
                binding.toolbar.setVisibility(View.GONE);
            } else {
                binding.toolbar.setVisibility(View.VISIBLE);
            }
        });

        /// TESTING STUFF

        CourseEventListener render = new CourseEventListener() {
            @Override
            public void onCourseAdded(Course course) {
                Log.d("Render", "Course Added: " + course);
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
        if (id == R.id.action_logout) {
            Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_global_LoginFragment);
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