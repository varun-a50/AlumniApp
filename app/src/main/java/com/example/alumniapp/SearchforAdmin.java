package com.example.alumniapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SearchforAdmin extends AppCompatActivity {
    RecyclerView recyclerView;
    SearchForAdmin_Adapter adapter;

    Button btnback;
    EditText searchbox;
    TextView textView;
    ImageButton search_button;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchfor_admin);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Students");
        searchbox = findViewById(R.id.et_search1);
        searchbox.setHint("Search for Admin....");
        textView=(TextView)findViewById(R.id.search_result);
        textView.setVisibility(View.GONE);
        search_button = findViewById(R.id.search_button);



        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = searchbox.getText().toString();
                if(searchText.isEmpty()) {
                    searchbox.requestFocus();
                    searchbox.setError("must type a letter");
                }else{
                    recyclerView = (RecyclerView) findViewById(R.id.searchbar);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    layoutManager.setReverseLayout(true);
                    layoutManager.setStackFromEnd(true);
                    recyclerView.setLayoutManager(layoutManager);


                    FirebaseRecyclerOptions<Admin> options
                            = new FirebaseRecyclerOptions.Builder<Admin>()
                            .setQuery(FirebaseDatabase.getInstance().getReference("Admins").orderByChild("first_name").startAt(searchText).endAt(searchText + "\uf8ff"),Admin.class)
                            .build();


                    adapter = new SearchForAdmin_Adapter(options, getApplicationContext());
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);


                    if (searchText.isEmpty() && options.getSnapshots().equals(null)){
                        adapter.stopListening();
                        textView.setVisibility(View.GONE);

                    }else{
                        adapter.startListening();

                        textView.setVisibility(View.GONE);


                    }


                }


            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),BackpressCheck.class));
    }
}