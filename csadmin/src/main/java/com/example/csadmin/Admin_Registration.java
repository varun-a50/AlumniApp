package com.example.csadmin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class Admin_Registration extends AppCompatActivity {
    private CircleImageView profileImageView;
    EditText first,mid,last,email,mobile,pass,checkpass;
    Button register;
    ProgressBar progressBar;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);



        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();



        first = findViewById(R.id.et1);
        mid = findViewById(R.id.et2);
        last = findViewById(R.id.et3);
        email = findViewById(R.id.rg_email);
        mobile = findViewById(R.id.mobile);
        pass = findViewById(R.id.pass);
        checkpass = findViewById(R.id.checkpass);
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
        String password = pass.getText().toString();
        String Checkpass = checkpass.getText().toString();
        String randomkey = "";
        String profileUrl = "";

        if (first_name.isEmpty()) {
            first.requestFocus();
            return;
        }
        if (mid_name.isEmpty()) {
            mid.requestFocus();
            return;
        }
        if (last_name.isEmpty()) {
            last.requestFocus();
            return;
        }
        if (email_ID.isEmpty()) {
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email_ID).matches()) {
            email.setError("Please provide valid email");
            email.requestFocus();
            return;
        }
        if (mobile_no.isEmpty()) {
            mobile.requestFocus();
            return;
        }
        if (mobile_no.length() < 10) {
            mobile.setError("Mobile number must be 10 digits");
            mobile.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            pass.requestFocus();
            return;
        }
        if (!password.equals(Checkpass)) {
            pass.setError("no matching pass");
            pass.requestFocus();
            return;
        }
        if (password.length() < 6) {
            pass.setError("Password must be 6 character long");
            pass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email_ID, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Admin admin = new Admin(first_name, mid_name, last_name, email_ID, mobile_no, password,randomkey,profileUrl);

                            FirebaseDatabase.getInstance().getReference("Admins")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(admin).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Admin_Registration.this, "Admin registered successfully", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(),Admin_Profile_update.class);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(Admin_Registration.this, "Registration Failed", Toast.LENGTH_LONG).show();
                                    }
                                    progressBar.setVisibility(View.GONE);
                                }
                            });

                        } else {
                            Toast.makeText(Admin_Registration.this, "Registration Failed,try again!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}