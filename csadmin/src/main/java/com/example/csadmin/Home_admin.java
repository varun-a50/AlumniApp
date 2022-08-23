package com.example.csadmin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home_admin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private CircleImageView profileImageView;

    RecyclerView recyclerView,recycler2;
    Post_Adapter_Admin adapter;
    Event2_Adapter adapter2;




    private FirebaseStorage storage;
    private StorageReference storage1;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String adminID;
    TextView event;
    FirebaseDatabase firebaseDatabase;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        recycler2 = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext());
        layoutManager2.setReverseLayout(true);
        layoutManager2.setStackFromEnd(true);
        recycler2.setLayoutManager(layoutManager2);

        FirebaseRecyclerOptions<Event> options2
                = new FirebaseRecyclerOptions.Builder<Event>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Events"), Event.class)
                .build();

        adapter2 = new Event2_Adapter(options2,getApplicationContext());
        adapter2.notifyDataSetChanged();

        recycler2.setAdapter(adapter2);
        recycler2.stopNestedScroll();


        recyclerView = (RecyclerView) findViewById(R.id.home_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<Post> options
                = new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Posts"), Post.class)
                .build();

        adapter = new Post_Adapter_Admin(options,getApplicationContext());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        event = findViewById(R.id.textView2);
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), All_Event_Admin.class));
            }
        });


        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);


        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Admins");
        adminID = user.getUid();
        final TextView textViewname = header.findViewById(R.id.fullname);
        profileImageView = header.findViewById(R.id.dp);




        databaseReference.child(adminID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Admin userProfile = snapshot.getValue(Admin.class);

                if(userProfile != null){
                    String fullname = userProfile.first_name;


                    textViewname.setText(fullname);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home_admin.this,"Something happened wrong!",Toast.LENGTH_LONG).show();


            }
        });
        Userinfo();
        updateData();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search,menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                startActivity(new Intent(getApplicationContext(),SearchforStudentOrAlumni.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        adapter2.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
        adapter2.stopListening();
    }

    private void Userinfo() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference photoReference= storageReference.child("Admins/Profiles/*"+adminID);

        final long ONE_MEGABYTE = 1024 * 1024;
        photoReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                profileImageView.setImageBitmap(bmp);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
            }
        });
    }
    private   void  updateData(){
        storage1 = storage.getReference().child("Admins/Profiles/*"+ adminID);
        storage1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageUrl = uri.toString();
                databaseReference = firebaseDatabase.getReference("Admins");
                databaseReference.child(adminID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(adminID).child("randomKey").setValue(adminID);
                        databaseReference.child(adminID).child("profileUrl").setValue(imageUrl);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



    }



    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent3 = new Intent(this,Admin_Login.class);
                startActivity(intent3);
                break;
            case R.id.Students_record:
                Intent intent = new Intent(this, All_Student_Admin.class);
                startActivity(intent);

                break;
            case R.id.Alumni_record:
                Intent intent1 = new Intent(this, All_Alumni_Admin.class);
                startActivity(intent1);

                break;
            case R.id.Create_record:
                Intent intent2 = new Intent(this, Event_schedule_Admin.class);
                startActivity(intent2);

                break;
            case R.id.Create_Post:
                Intent intent7 = new Intent(this, Make_Post_Admin.class);
                startActivity(intent7);

                break;
            case R.id.profile:
                Intent intent4 = new Intent(this,Admin_Profile_Edit.class);
                startActivity(intent4);
                break;

        }

        return false;
    }
}