package com.example.alumniapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Student_registration extends AppCompatActivity {
    EditText first,mid,last,email,mobile,class_year1,pass,checkpass;

    Button register;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    TextView t1;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        t1 = findViewById(R.id.sign_in);
        first = findViewById(R.id.et1);
        mid = findViewById(R.id.et2);
        last = findViewById(R.id.et3);
        email = findViewById(R.id.rg_email);
        mobile = findViewById(R.id.mobile);
        class_year1 = findViewById(R.id.grades);
        pass = findViewById(R.id.pass);
        checkpass = findViewById(R.id.checkpass);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });



        progressBar = findViewById(R.id.progressBar);

        register = findViewById(R.id.btn_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }
    private void registerUser() {
        String first_name = first.getText().toString();
        String mid_name = mid.getText().toString();
        String last_name = last.getText().toString();
        String email_ID = email.getText().toString();
        String mobile_no = mobile.getText().toString();
        String class_year = class_year1.getText().toString();

        String password = pass.getText().toString();
        String Checkpass = checkpass.getText().toString();
        String randomkey = "";
        String profileUrl = "";

        if (first_name.isEmpty()){
            first.requestFocus();
            return;
        }
        if (mid_name.isEmpty()){
            mid.requestFocus();
            return;
        }
        if (last_name.isEmpty()){
            last.requestFocus();
            return;
        }
        if (email_ID.isEmpty()){
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email_ID).matches()){
            email.setError("Please provide valid email");
            email.requestFocus();
            return;
        }
        if (mobile_no.isEmpty()){
            mobile.requestFocus();
            return;
        }
        if(mobile_no.length() < 10){
            mobile.setError("Mobile number must be 10 digits");
            mobile.requestFocus();
            return;
        }
        if (class_year.isEmpty()){
            class_year1.requestFocus();
            return;
        }
        if (password.isEmpty()){
            pass.requestFocus();
            return;
        }
        if(!password.equals(Checkpass)){
            pass.setError("no matching pass");
            pass.requestFocus();
            return;
        }
        if(password.length() < 6) {
            pass.setError("Password must be 6 character long");
            pass.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email_ID,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Student student = new Student(first_name,mid_name,last_name,email_ID,mobile_no,class_year,password,randomkey,profileUrl);

                            FirebaseDatabase.getInstance().getReference("Students")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Student_registration.this,"User has been registered",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(),Profile_upload_students.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(Student_registration.this,"Registration Failed",Toast.LENGTH_LONG).show();
                                    }
                                    progressBar.setVisibility(View.GONE);
                                }
                            });

                        }else {
                            Toast.makeText(Student_registration.this,"Registration Failed,try again!",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}