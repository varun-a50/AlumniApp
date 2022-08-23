package com.example.alumniapp;

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

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private CircleImageView profileImageView;

    RecyclerView recyclerView,recycler2;
    Post_Adapter adapter;
    Event2_Adapter adapter2;
    TextView event;
    Menu studentrecord,Alumnirecord,createEvent;

    private FirebaseStorage storage,storages;
    private StorageReference storageReference,storageReferences;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference databaseReference,firebaseDatabasestudent,firebaseDatabaseAdmin;
    private String userID;
    FirebaseDatabase firebaseDatabase;

    String key = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);




        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);


        recycler2 = (RecyclerView) findViewById(R.id.recyclerView2);
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



        recyclerView = (RecyclerView) findViewById(R.id.home_recycler1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);



        FirebaseRecyclerOptions<Post> options
                = new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Posts"), Post.class)
                .build();

        adapter = new Post_Adapter(options,getApplicationContext());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        event = findViewById(R.id.textView2);
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),All_Event.class));
            }
        });

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);


        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("Alumnis/Profiles");
        user = FirebaseAuth.getInstance().getCurrentUser();
        storages = FirebaseStorage.getInstance();
        storageReferences = FirebaseStorage.getInstance().getReference("Students/Profiles");
        databaseReference = FirebaseDatabase.getInstance().getReference("Alumnis");
        firebaseDatabasestudent = FirebaseDatabase.getInstance().getReference("Students");
        firebaseDatabaseAdmin = FirebaseDatabase.getInstance().getReference("Admins");
        userID = user.getUid();
        final TextView textViewname = header.findViewById(R.id.fullname);
        profileImageView = header.findViewById(R.id.dp);


        firebaseDatabaseAdmin.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
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
                Toast.makeText(Home.this,"Something happened wrong!",Toast.LENGTH_LONG).show();


            }
        });





        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Alumni userProfile = snapshot.getValue(Alumni.class);

                if(userProfile != null){
                    String fullname = userProfile.first_name;


                    textViewname.setText(fullname);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home.this,"Something happened wrong!",Toast.LENGTH_LONG).show();


            }
        });

        firebaseDatabasestudent.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student userProfile = snapshot.getValue(Student.class);

                if(userProfile != null){
                    String fullname = userProfile.first_name;


                    textViewname.setText(fullname);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home.this,"Something happened wrong!",Toast.LENGTH_LONG).show();

            }
        });

        Userinfo();
        Userinfos();
        Userinfoadmin();
        updateDatasadmin();
        updateData();
        updateDatas();

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
        StorageReference photoReference= storageReference.child("Alumnis/Profiles/*"+userID);

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
    private void Userinfos() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference photoReference= storageReference.child("Students/Profiles/*"+userID);

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
        StorageReference storage1 = storage.getReference().child("Alumnis/Profiles/*"+ userID);
        storage1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageUrl = uri.toString();
                databaseReference = firebaseDatabase.getReference("Alumnis");
                databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(userID).child("randomKey").setValue(userID);
                        databaseReference.child(userID).child("profileUrl").setValue(imageUrl);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



    }
    private   void  updateDatas(){
        StorageReference storage1 = storage.getReference().child("Students/Profiles/*"+ userID);
        storage1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageUrl = uri.toString();
                databaseReference = firebaseDatabase.getReference("Students");
                databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(userID).child("randomKey").setValue(userID);
                        databaseReference.child(userID).child("profileUrl").setValue(imageUrl);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }

    private void Userinfoadmin() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference photoReference= storageReference.child("Admins/Profiles/*"+userID);

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

    private   void  updateDatasadmin() {
        StorageReference storageadmin = storage.getReference().child("Admins/Profiles/*" + userID);
        storageadmin.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageUrl = uri.toString();
                databaseReference = firebaseDatabase.getReference("Admins");
                databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(userID).child("randomKey").setValue(userID);
                        databaseReference.child(userID).child("profileUrl").setValue(imageUrl);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent3 = new Intent(this, Login.class);
                startActivity(intent3);
                finish();
                break;
            case R.id.Create_Post:
                Intent intent7 = new Intent(this,Make_Post.class);
                startActivity(intent7);
                finish();
                break;
            case R.id.profile:
                Intent intent = new Intent(this,UpdateProfile.class);
                startActivity(intent);
                finish();
                break;

        }
        return true;
    }
}