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

public class Make_Post_Admin extends AppCompatActivity {
    Button button;
    TextView dateView, timeView;
    EditText post,name,last1;
    int hour, minute;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    ProgressBar progressBar;
    private FirebaseUser user;
    private String adminID;
    FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_post_admin);
        name = findViewById(R.id.et1);
        last1 = findViewById(R.id.et3);

        progressBar = findViewById(R.id.progressBar);
        post = findViewById(R.id.post_text);
        button = findViewById(R.id.post_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerPost();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        adminID = user.getUid();
        firebaseStorage = FirebaseStorage.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();

        progressBar.setVisibility(View.VISIBLE);
        firebaseDatabase.getReference("Admins").child(adminID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Admin admin_details = snapshot.getValue(Admin.class);

                if (admin_details != null) {

                    String first = admin_details.first_name;
                    String last = admin_details.last_name;

                    name.setText(first);
                    last1.setText(last);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Make_Post_Admin.this,"Something happened wrong!",Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);

            }
        });

    }
    private void registerPost() {



        StorageReference storage = firebaseStorage.getReference().child("Admins/Profiles/*"+ adminID);
        storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageUrl = uri.toString();
                String uid = adminID;
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
                            Toast.makeText(Make_Post_Admin.this, "Post posted", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), Home_admin.class);
                            startActivity(intent);
                            progressBar.setVisibility(View.GONE);

                        } else {
                            Toast.makeText(Make_Post_Admin.this, "Post posting Failed", Toast.LENGTH_LONG).show();
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
        startActivity(new Intent(getApplicationContext(), Home_admin.class));
    }
}