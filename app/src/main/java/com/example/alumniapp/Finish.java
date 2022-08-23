package com.example.alumniapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Finish extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
    }

    @Override
    protected void onStart() {
        super.onStart();
        finish();
    }
}