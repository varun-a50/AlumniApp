package com.example.alumniapp;

import android.content.Intent;
import android.os.Bundle;
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

public class Registration2 extends AppCompatActivity{


    EditText company,work,something1,first,mid,last,email,mobile,grades,gyear,pass;
    Button register;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    TextView t1;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);

        mAuth = FirebaseAuth.getInstance();

        company = findViewById(R.id.et21);
        work = findViewById(R.id.et22);
        something1 = findViewById(R.id.et23);
        register = findViewById(R.id.btn_register);
        firebaseDatabase = FirebaseDatabase.getInstance();




        first = findViewById(R.id.et1);
        mid = findViewById(R.id.et2);
        last = findViewById(R.id.et3);
        email = findViewById(R.id.rg_email);
        mobile = findViewById(R.id.mobile);
        grades = findViewById(R.id.grades);
        gyear = findViewById(R.id.spinner4);
        pass = findViewById(R.id.pass);
        progressBar = findViewById(R.id.progressBar);





        Bundle bundle = getIntent().getExtras();

        String f = bundle.getString("first_name");
        String m = bundle.getString("mid_name");
        String l = bundle.getString("last_name");
        String e = bundle.getString("email_ID");
        String mo = bundle.getString("mobile_no");
        String g = bundle.getString("grade");
        String y = bundle.getString("year");
        String p = bundle.getString("password");

        first.setText(f);
        mid.setText(m);
        last.setText(l);
        email.setText(e);
        mobile.setText(mo);
        grades.setText(g);
        gyear.setText(y);
        pass.setText(p);





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
        String year = gyear.getText().toString();
        String password = pass.getText().toString();
        String company_name = company.getText().toString();
        String work_experience = work.getText().toString();
        String something = something1.getText().toString();
        String randomkey = "";
        String profileUrl = "";



        if (first_name.isEmpty()){
            first.requestFocus();
            first.setError("required");
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

        if (mobile_no.isEmpty()){
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

        if (company_name.isEmpty()){
            company.requestFocus();
            company.setError("It is required");
            return;
        }
        if (work_experience.isEmpty()){
            work.requestFocus();
            return;
        }
        if (something.isEmpty()){
            something1.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email_ID,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Alumni alumni = new Alumni(first_name,mid_name,last_name,email_ID,mobile_no,grade,year,password,company_name,work_experience,something,randomkey,profileUrl);

                            FirebaseDatabase.getInstance().getReference("Alumnis")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(alumni).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Registration2.this,"User has been registered",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(),Profile_upload.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(Registration2.this,"Registration Failed",Toast.LENGTH_LONG).show();
                                    }
                                    progressBar.setVisibility(View.GONE);
                                }
                            });

                        }else {
                            Toast.makeText(Registration2.this,"Registration Failed,try again!",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}