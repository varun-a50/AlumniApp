package com.example.alumniapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class Registration extends AppCompatActivity {
    EditText first,mid,last,email,mobile,grades,pass,checkpass;
    Spinner gyear;
    Button register;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    TextView t1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        mAuth = FirebaseAuth.getInstance();


        t1 = findViewById(R.id.sign_in);
        first = findViewById(R.id.et1);
        mid = findViewById(R.id.et2);
        last = findViewById(R.id.et3);
        email = findViewById(R.id.rg_email);
        mobile = findViewById(R.id.mobile);
        grades = findViewById(R.id.grades);
        gyear = findViewById(R.id.spinner4);
        pass = findViewById(R.id.pass);
        checkpass = findViewById(R.id.checkpass);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });

        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1990; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);


        gyear.setAdapter(adapter);


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
        String grade = grades.getText().toString();
        String year = gyear.getSelectedItem().toString();
        String password = pass.getText().toString();
        String Checkpass = checkpass.getText().toString();

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
        if (grade.isEmpty()){
            grades.requestFocus();
            return;
        }
        if (year.isEmpty()){
            gyear.requestFocus();
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
                            User user = new User(first_name,mid_name,last_name,email_ID,mobile_no,grade,year,password);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Registration.this,"User has been registered",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(),Profile_upload.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(Registration.this,"Registration Failed",Toast.LENGTH_LONG).show();
                                    }
                                    progressBar.setVisibility(View.GONE);
                                }
                            });

                        }else {
                            Toast.makeText(Registration.this,"Registration Failed,try again!",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}