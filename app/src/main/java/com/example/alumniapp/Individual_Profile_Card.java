package com.example.alumniapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class Individual_Profile_Card extends AppCompatActivity {
    TextView name,mid_name,sur_name,email,mobile,year;
    CircleImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_profile_card);
        name = findViewById(R.id.et1);
        mid_name = findViewById(R.id.et2);
        sur_name = findViewById(R.id.et3);
        email = findViewById(R.id.rg_email);
        mobile = findViewById(R.id.mobile);
        year = findViewById(R.id.grades);
        imageView = findViewById(R.id.profile_image2);

        Glide.with(imageView.getContext()).load(getIntent().getStringExtra("imageUrl")).apply(new RequestOptions().error(R.drawable.alumni).fitCenter()).into(imageView);
        name.setText(getIntent().getStringExtra("name"));
        mid_name.setText(getIntent().getStringExtra("mid_name"));
        sur_name.setText(getIntent().getStringExtra("last_name"));
        email.setText(getIntent().getStringExtra("email"));
        mobile.setText(getIntent().getStringExtra("mobile"));
        year.setText(getIntent().getStringExtra("class"));



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),All_Student_Admin.class);
        startActivity(intent);
        finish();
    }
}