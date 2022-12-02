package com.example.utt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignUpPage extends AppCompatActivity {
    Spinner adminOrStudent;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        final EditText email = findViewById(R.id.editTextTextEmailAddress);
        final EditText passwd = findViewById(R.id.editTextTextPassword2);
        final EditText passwdAuth = findViewById(R.id.editTextTextPassword3);
        final Button registerButton = findViewById(R.id.registration_button);
//        final ImageButton backButton = findViewById(R.id.back_button);



        adminOrStudent = (Spinner)findViewById(R.id.adminOrStudent_spinner);
        ArrayList <String> list = new ArrayList<>();
        list.add("Admin");
        list.add("Student");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
        adminOrStudent.setAdapter(adapter);

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                String adminOrStudentChoice = adminOrStudent.getSelectedItem().toString();
                final String emailText = email.getText().toString();
                final String passwordText = passwd.getText().toString();
                final String passwdAuthText = passwdAuth.getText().toString();

                if(emailText.isEmpty() || passwordText.isEmpty() || passwdAuthText.isEmpty()) {
                    Toast.makeText(SignUpPage.this, "Please fill all blanks", Toast.LENGTH_SHORT).show();
                }
                else if(!passwordText.equals(passwdAuthText)){
                    Toast.makeText(SignUpPage.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
                }
                else{
                    User user = new User(emailText, passwordText, adminOrStudentChoice.equals("Admin"));
                    addUser(user);
                    Toast.makeText(SignUpPage.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

//        backButton.setOnClickListener(view -> {
//            public void onClick(View v) {
//                Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_global_LoginFragment);
//            }
//        });
    }
}
