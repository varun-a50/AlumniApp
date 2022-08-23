package com.example.csadmin;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Timer timer;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        timer = new Timer();
    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null){
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intento = new Intent(getApplicationContext(), Home_admin.class);
                    startActivity(intento);
                    finish();


                }
            },1500) ;

        }else{
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(),Admin_Login.class);
                    startActivity(intent);
                    finish();


                }
            },1500) ;

        }
    }
}