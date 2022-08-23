package com.example.alumniapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SearchforStudentOrAlumni extends AppCompatActivity implements View.OnClickListener {

    TextView stu,al,ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchfor_student_or_alumni);
        stu = findViewById(R.id.s);
        al = findViewById(R.id.a);
        ad = findViewById(R.id.ad);
        stu.setOnClickListener(this);
        al.setOnClickListener(this);
        ad.setOnClickListener(this);



    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.s:
                startActivity(new Intent(getApplicationContext(),SearchforStudent.class));
                break;
            case R.id.a:
                startActivity(new Intent(getApplicationContext(),Search.class));
                break;
            case R.id.ad:
                startActivity(new Intent(getApplicationContext(),SearchforAdmin.class));
                break;



        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),BackpressCheck.class));
    }
}