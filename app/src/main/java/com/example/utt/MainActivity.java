package com.example.utt;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.utt.database.DatabaseHandler;
import com.example.utt.databinding.ActivityMainBinding;
import com.example.utt.models.Course;
import com.example.utt.models.CourseEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
//    Fragment fragmentInstance = getSupportFragmentManager().findFragmentById(R.id.frameLayout);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Integer fragId = fragmentInstance.getId();
        // Log.d("Current Frag", fragId.toString());
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
            // The back_button should be referenced with a better method
            ActionBar actionBar = getSupportActionBar();
            if(destination.getId() == R.id.LoginFragment) {
                binding.toolbar.setVisibility(View.GONE);
            }
            else {
                binding.toolbar.setVisibility(View.VISIBLE);
                assert actionBar != null;
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            if(destination.getId() == R.id.Home || destination.getId() == R.id.firstFragment) {
                assert actionBar != null;
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        });


        DatabaseHandler.initialise();
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

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
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
            CookieLogin.logout(this);
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

    private CharSequence menuIconWithText(Drawable r, String title) {

        r.setBounds(0, 0, r.getIntrinsicWidth(), r.getIntrinsicHeight());
        SpannableString sb = new SpannableString("    " + title);
        ImageSpan imageSpan = new ImageSpan(r, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sb;
    }

    private void addToMenu(Menu menu) {

    }
}