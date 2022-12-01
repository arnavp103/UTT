package com.example.utt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.utt.database.DatabaseHandler;
import com.example.utt.databinding.FragmentLoginPageBinding;
import com.example.utt.models.Course;
import com.example.utt.models.CourseEventListener;
import com.example.utt.models.Listener;
import com.example.utt.models.Student;
import com.example.utt.models.User;
import com.example.utt.SharedMethods;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class LoginFragment extends Fragment {
    private FragmentLoginPageBinding binding;
    private EditText uEdit;
    private EditText pEdit;
    private Context context;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        context = this.getContext();
        binding = FragmentLoginPageBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void checkUserStatus(User user, View view) {
//        Toast.makeText(getActivity(), "Logged in as " + user.getEmail(), Toast.LENGTH_SHORT).show();
        Snackbar.make(view, "Logged in as " + user.getEmail(), BaseTransientBottomBar.LENGTH_SHORT).show();
        if (user.isStudent()) {
            DatabaseHandler.getStudentData(user.getId(), new Listener<String>() {
                @Override
                public void onSuccess(String data, @Nullable List<String> objectModel) {
                    assert objectModel != null;
                    Student s = new Student(user.getEmail(), user.getPassword());
                    s.setId(user.getId());

                    Student.login(s, objectModel);
//                        s.addCourse(List.of("MATA41",  "CSCB36",  "CSCA08", "BBBC"));
//                        DatabaseHandler.updateStudentData(s);

                    NavHostFragment.findNavController(LoginFragment.this)
                            .navigate(R.id.action_loginFragment_to_FirstFragment);
                }

                @Override
                public void onFailure(String data) {}

                @Override
                public void onComplete(String data) {}
            });



        } else if (user.getIsAdmin()) {
            // Notify other fragments that user is Admin

            NavHostFragment.findNavController(LoginFragment.this)
                    .navigate(R.id.action_LoginFragment_to_adminPlaceholder);
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        uEdit = (EditText) view.findViewById(R.id.editTextUsername);
        pEdit = (EditText) view.findViewById(R.id.editTextTextPassword);

        // Force focus to View
        pEdit.setFocusableInTouchMode(true);
        pEdit.requestFocus();


        pEdit.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                // Perform action on key press
                this.submit(v);
                SharedMethods.collapseKeyboard(context);
//                return true;
            }
            return false;
        });

        // Validate Login Credentials
        binding.buttonLogin.setOnClickListener(this::submit);

        binding.signup.setOnClickListener(view1 -> {
            Intent intent = new Intent(view.getContext(), SignUpPage.class);
            startActivity(intent);
        });
    }

    private void submit(View view) {
        String username = uEdit.getText().toString();
        String password = pEdit.getText().toString();

        Listener<User> authCallback = new Listener<User>() {
            @Override
            public void onSuccess(String data, List<User> user) {
                assert user != null;
                checkUserStatus(user.get(0), view);
            }

            @Override
            public void onFailure(String data) {
//                    Toast.makeText(getActivity(), "Authentication Failed!", Toast.LENGTH_SHORT).show();
                Snackbar.make(view, "Authentication Failed!", BaseTransientBottomBar.LENGTH_SHORT).show();
                Log.d("AUTH FAIL", "-"+data);

            }

            @Override
            public void onComplete(String data) {}
        };
        DatabaseHandler.getUser(username, password, authCallback);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
