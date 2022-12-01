package com.example.utt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.utt.database.DatabaseHandler;
import com.example.utt.models.Student;
import com.example.utt.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
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
        final ImageButton backButton = findViewById(R.id.back_button);



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
                // Make user doublecheck password
                final String passwdAuthText = passwdAuth.getText().toString();

                if(emailText.isEmpty() || passwordText.isEmpty() || passwdAuthText.isEmpty()) {
                    Toast.makeText(SignUpPage.this, "Please fill all blanks", Toast.LENGTH_SHORT).show();
                }
                else if(!passwordText.equals(passwdAuthText)){
                    Toast.makeText(SignUpPage.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.orderByChild("email").
                            equalTo(emailText).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                @Override
                                public void onSuccess(DataSnapshot dataSnapshot) {
                                    Log.d("VLL", String.valueOf(dataSnapshot.exists()));
                                    if (dataSnapshot.exists()) {
                                        Toast.makeText(SignUpPage.this, "Email already used", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (adminOrStudentChoice.equals("Admin")) {
                                            User admin = new User(emailText, passwordText, true);
                                            DatabaseHandler.addUser(admin);
//                                            databaseReference.child("users").child(admin.getId()).setValue(admin);
                                            Toast.makeText(SignUpPage.this, "Admin registered successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            User student = new Student(emailText, passwordText);
                                            DatabaseHandler.addUser(student);
//                                            databaseReference.child("students").child(student.getId()).setValue(student);
                                            Toast.makeText(SignUpPage.this, "Student registered successfully", Toast.LENGTH_SHORT).show();
                                        }
                                        finish();
                                    }
                                }}).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });



                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });
    }
}