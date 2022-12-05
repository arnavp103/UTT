package com.example.utt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.utt.database.DatabaseHandler;
import com.example.utt.databinding.FragmentLoginPageBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class LoginFragMVP extends Fragment implements LoginPresenter.LoginView{
    private FragmentLoginPageBinding binding;
    // username and password edit
    private EditText uEdit;
    private EditText pEdit;
    private Context context;
    private LoginPresenter loginPresenter;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        context = this.getContext();
        binding = FragmentLoginPageBinding.inflate(inflater, container, false);
        loginPresenter = new LoginPresenter(this);

        if (loginPresenter.getCookie(context).length() != 0) {
            DatabaseHandler.addOnReadyListener(new DatabaseHandler.OnReadyListener() {
                @Override
                public void onReady() {
                    // if they don't have anything saved to shared pref continue as normal
                    loginPresenter.cookieQuery(CookieLogin.getUserId(context), getView());

                }
            });
        }
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    /// The method to check if the user's credentials are valid
//    public void checkUserStatus(User user, View view) {
//        if (user.isStudent()) {
//            DatabaseHandler.getStudentData(user.getId(), new Listener<String>() {
//                @Override
//                public void onSuccess(String data, @Nullable List<String> objectModel) {
//                    assert objectModel != null;
//                    Student s = new Student(user.getEmail(), user.getPassword());
//                    s.setId(user.getId());
//
//                    Student.login(s, objectModel);
//                    // s.addCourse(List.of("MATA41", "CSCB36", "CSCA08", "BBBC"));
//                    // DatabaseHandler.updateStudentData(s);
//                    goToStudentHome();
//                }
//
//                @Override
//                public void onFailure(String data) {
//                }
//
//                @Override
//                public void onComplete(String data) {
//                }
//            });
//
//        } else if (user.getIsAdmin()) {
//            // Notify other fragments that user is Admin
//            goToAdminHome();
//        }
//    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
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
                // return true;
            }
            return false;
        });

        // Validate Login Credentials
        // binding.buttonLogin.setOnClickListener(this::submit);

        binding.buttonLogin.setOnClickListener(this::submit);

        binding.signup.setOnClickListener(view1 -> goToSignUp());
    }

    private void submit(View view) {
        String username = uEdit.getText().toString();
        String password = pEdit.getText().toString();


        loginPresenter.query(username, password, view);
        // DatabaseHandler.getUser(username, password, authCallback);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void makeSnackbar(String message) {
        View view = this.getView();
        assert view != null;
        Snackbar.make(this.getView(), message, BaseTransientBottomBar.LENGTH_SHORT).show();
    }

    @Override
    public void goToAdminHome() {
        NavHostFragment.findNavController(LoginFragMVP.this)
                .navigate(R.id.action_LoginFragment_to_firstFragment);
    }

    @Override
    public void goToStudentHome() {
        NavHostFragment.findNavController(LoginFragMVP.this)
                .navigate(R.id.action_LoginFragment_to_Home);
    }

    @Override
    public void goToSignUp() {
        Intent intent = new Intent(this.getContext(), SignUpPage.class);
        startActivity(intent);
    }

    @Override
    public void collapseKeyboard() {
        SharedMethods.collapseKeyboard(context);
    }
}
