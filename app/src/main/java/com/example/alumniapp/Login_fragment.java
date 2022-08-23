package com.example.alumniapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login_fragment extends Fragment  implements View.OnClickListener {

    View view;
    Button btn_sign;
    EditText editTextemail_ID,editTextpassword;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView forgot;
    String email_ID,password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        editTextemail_ID = (EditText)view.findViewById(R.id.email);
        editTextpassword = (EditText)view.findViewById(R.id.pass);
        btn_sign = view.findViewById(R.id.signin);
        btn_sign.setOnClickListener(this);

        forgot = view.findViewById(R.id.fgpass);
        forgot.setOnClickListener(this);
        return view;


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signin:
                userLogin();
                break;
            case R.id.fgpass:

                break;
        }
    }

    private void userLogin() {

       email_ID = editTextemail_ID.getText().toString().trim();
       password = editTextpassword.getText().toString().trim();

        if(email_ID.isEmpty()){
            editTextemail_ID.setError("Email is required!");
            editTextemail_ID.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextpassword.setError("Password is required!");
            editTextpassword.requestFocus();
            return;
        }
        if(password.isEmpty()) {
            editTextpassword.setError("Password must be at least 6 character");
            editTextpassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email_ID,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            if(user.isEmailVerified()){

                                Intent intent = new Intent(getContext(),ChecktheUser.class);
                                startActivity(intent);
                                getActivity().finish();


                            }else{
                                user.sendEmailVerification();
                                Toast.makeText(getContext(),"Check your mail for Verification",Toast.LENGTH_LONG).show();
                            }

                        }else {
                            Toast.makeText(getContext(),"Failed to login! Please check your credentials",Toast.LENGTH_LONG).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });

    }
}