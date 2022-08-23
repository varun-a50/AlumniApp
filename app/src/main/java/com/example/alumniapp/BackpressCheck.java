package com.example.alumniapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BackpressCheck extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference databaseReference,firebaseDatabasestudent,firebaseDatabaseAdmin;
    private String userID;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backpress_check);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Alumnis");
        firebaseDatabasestudent = FirebaseDatabase.getInstance().getReference("Students");
        firebaseDatabaseAdmin = FirebaseDatabase.getInstance().getReference("Admins");
        userID = user.getUid();

        firebaseDatabaseAdmin.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Admin userProfile = snapshot.getValue(Admin.class);

                if(userProfile != null){



                    Intent intent = new Intent(getApplicationContext(),Home_admin.class);
                    startActivity(intent);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    finish();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Something happened wrong!",Toast.LENGTH_LONG).show();


            }
        });





        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Alumni userProfile = snapshot.getValue(Alumni.class);

                if(userProfile != null){
                    Intent intent = new Intent(getApplicationContext(),Home.class);
                    startActivity(intent);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    finish();





                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Something happened wrong!",Toast.LENGTH_LONG).show();


            }
        });

        firebaseDatabasestudent.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student userProfile = snapshot.getValue(Student.class);

                if(userProfile != null){
                    Intent intent = new Intent(getApplicationContext(),Home.class);
                    startActivity(intent);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    finish();




                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Something happened wrong!",Toast.LENGTH_LONG).show();

            }
        });
    }
}