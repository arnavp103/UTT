package com.example.utt;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.utt.database.DatabaseHandler;
import com.example.utt.databinding.FragmentLoginPageBinding;
import com.example.utt.models.Listener;
import com.example.utt.models.User;

import java.util.List;

public class LoginFragment extends Fragment {
    private FragmentLoginPageBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentLoginPageBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void checkUserStatus(User user) {
        Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
        if (user.isStudent()) {
            DatabaseHandler.getStudentData(user.getId(), new Listener<String>() {
                @Override
                public void onSuccess(String data, @Nullable List<String> objectModel) {

                }

                @Override
                public void onFailure(String data) {}

                @Override
                public void onComplete(String data) {}
            });
        } else if (user.getIsAdmin()) {

        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonLogin.setOnClickListener(view1 -> {
            // Validate Login Credentials
            EditText uEdit = (EditText) view.findViewById(R.id.editTextUsername);
            EditText pEdit = (EditText) view.findViewById(R.id.editTextTextPassword);
            String username = uEdit.getText().toString();
            String password = pEdit.getText().toString();

            Listener<User> authCallback = new Listener<User>() {
                @Override
                public void onSuccess(String data, List<User> user) {
                    assert user != null;
                    checkUserStatus(user.get(0));
                    NavHostFragment.findNavController(LoginFragment.this)
                            .navigate(R.id.action_loginFragment_to_FirstFragment);
                }

                @Override
                public void onFailure(String data) {
                    Toast.makeText(getActivity(), "Authentication Failed!", Toast.LENGTH_SHORT).show();
                    Log.d("AUTH FAIL", "-"+data);
                }

                @Override
                public void onComplete(String data) {}
            };

            DatabaseHandler.getUser(username, password, authCallback);

        });

        binding.signup.setOnClickListener(view1 -> {
            // nav to sign up fragment
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
