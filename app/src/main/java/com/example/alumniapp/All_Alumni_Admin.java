package com.example.alumniapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class All_Alumni_Admin extends AppCompatActivity {
    RecyclerView recyclerView;
    Alumni_Adapter adapter;

    private CircleImageView profileImageView;


    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_alumni_admin);

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_alu);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<Alumni> options
                = new FirebaseRecyclerOptions.Builder<Alumni>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Alumnis"), Alumni.class)
                .build();

        adapter  = new Alumni_Adapter(options,getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Home_admin.class));
    }
}