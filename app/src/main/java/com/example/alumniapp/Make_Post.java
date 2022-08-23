package com.example.alumniapp;

import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Make_Post extends AppCompatActivity {
    Button button;
    TextView dateView, timeView;
    EditText post,name,last1;
    int hour, minute;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    ProgressBar progressBar;
    private FirebaseUser user;
    private String alumniID,StudentId;
    FirebaseStorage firebaseStorage,firebaseStorages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_post);
        name = findViewById(R.id.et1);
        last1 = findViewById(R.id.et3);

        progressBar = findViewById(R.id.progressBar);
        post = findViewById(R.id.post_text);
        button = findViewById(R.id.post_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerPost();
                registerPosts();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        alumniID = user.getUid();
        StudentId = user.getUid();

        firebaseStorage = FirebaseStorage.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();

        progressBar.setVisibility(View.VISIBLE);
        firebaseDatabase.getReference("Alumnis").child(alumniID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Alumni Alumni_details = snapshot.getValue(Alumni.class);

                if (Alumni_details != null) {

                    String first = Alumni_details.first_name;
                    String last = Alumni_details.last_name;

                    name.setText(first);
                    last1.setText(last);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Make_Post.this,"Something happened wrong!",Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);

            }
        });
        progressBar.setVisibility(View.VISIBLE);
        firebaseDatabase.getReference("Students").child(StudentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student Student_details = snapshot.getValue(Student.class);

                if (Student_details != null) {

                    String first = Student_details.first_name;
                    String last = Student_details.last_name;

                    name.setText(first);
                    last1.setText(last);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Make_Post.this,"Something happened wrong!",Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);

            }
        });

    }
    private void registerPost() {



        StorageReference storage = firebaseStorage.getReference().child("Alumnis/Profiles/*"+ alumniID);
        storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                String imageUrl = uri.toString();
                String uid = alumniID;
                String First_Name = name.getText().toString();
                String Last_Name = last1.getText().toString();
                String Post = post.getText().toString();
                String randomkey = firebaseDatabase.getReference().push().getKey();



                if (uid.isEmpty()){
                    Toast.makeText(getApplicationContext(),"NO UID found",Toast.LENGTH_LONG).show();
                    return;
                }
                if (Post.isEmpty()){
                    post.requestFocus();
                    post.setText("you can't make a post without writing anything");
                    return;
                }




                progressBar.setVisibility(View.VISIBLE);

                Post post = new Post(imageUrl,uid,First_Name,Last_Name,Post,randomkey);


                FirebaseDatabase.getInstance().getReference("Posts")
                        .child(randomkey)
                        .setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Make_Post.this, "Post posted", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            startActivity(intent);
                            progressBar.setVisibility(View.GONE);

                        } else {
                            Toast.makeText(Make_Post.this, "Post posting Failed", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });




            }
        });


    }
    private void registerPosts() {



        StorageReference storage = firebaseStorage.getReference().child("Students/Profiles/*"+ alumniID);
        storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                String imageUrl = uri.toString();
                String uid = StudentId;
                String First_Name = name.getText().toString();
                String Last_Name = last1.getText().toString();
                String Post = post.getText().toString();
                String randomkey = firebaseDatabase.getReference().push().getKey();



                if (uid.isEmpty()){
                    Toast.makeText(getApplicationContext(),"NO UID found",Toast.LENGTH_LONG).show();
                    return;
                }
                if (Post.isEmpty()){
                    post.requestFocus();
                    post.setText("you can't make a post without writing anything");
                    return;
                }




                progressBar.setVisibility(View.VISIBLE);

                Post post = new Post(imageUrl,uid,First_Name,Last_Name,Post,randomkey);


                FirebaseDatabase.getInstance().getReference("Posts")
                        .child(randomkey)
                        .setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Make_Post.this, "Post posted", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            startActivity(intent);
                            progressBar.setVisibility(View.GONE);

                        } else {
                            Toast.makeText(Make_Post.this, "Post posting Failed", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });




            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),Home.class));
    }
}