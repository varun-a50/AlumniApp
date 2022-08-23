package com.example.alumniapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class Admin_Profile_Edit extends AppCompatActivity {
    CircleImageView circular;
    ProgressBar progressBar;
    EditText name,mid_name,surname,email,mobile;
    Button update;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String userID;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile_edit);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();



        update = findViewById(R.id.update);
        circular = findViewById(R.id.profile_image);
        name = findViewById(R.id.et1);
        mid_name = findViewById(R.id.et2);
        surname = findViewById(R.id.et3);
        email = findViewById(R.id.rg_email);
        mobile = findViewById(R.id.mobile);

        storage = FirebaseStorage.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("Admins/Profiles");
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Admins");
        userID = user.getUid();

        circular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPhoto.launch("image/*");
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
                uploadProfileImage();
            }
        });

        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Admin userProfile = snapshot.getValue(Admin.class);

                if(userProfile != null){
                    String fullname = userProfile.first_name;
                    String m = userProfile.mid_name;
                    String l = userProfile.last_name;
                    String e = userProfile.email_ID;
                    String mo = userProfile.mobile_no;


                    name.setText(fullname);
                    mid_name.setText(m);
                    surname.setText(l);
                    email.setText(e);
                    mobile.setText(mo);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Something happened wrong!",Toast.LENGTH_LONG).show();


            }
        });
        Userinfo();

    }
    private void Userinfo() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference photoReference= storageReference.child("Admins/Profiles/*"+userID);

        final long ONE_MEGABYTE = 1024 * 1024;
        photoReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                circular.setImageBitmap(bmp);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
            }
        });
    }
    private   void  updateData(){
        StorageReference storage1 = storage.getReference().child("Admins/Profiles/*"+ userID);
        storage1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageUrl = uri.toString();
                databaseReference = firebaseDatabase.getReference("Admins");
                databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(userID).child("randomKey").setValue(userID);
                        databaseReference.child(userID).child("profileUrl").setValue(imageUrl);

                        String first_name = name.getText().toString();
                        String mid_name1 = mid_name.getText().toString();
                        String last_name = surname.getText().toString();
                        String email_ID = email.getText().toString();
                        String mobile_no = mobile.getText().toString();


                        databaseReference.child(userID).child("first_name").setValue(first_name);
                        databaseReference.child(userID).child("mid_name").setValue(mid_name1);
                        databaseReference.child(userID).child("last_name").setValue(last_name);
                        databaseReference.child(userID).child("email_ID").setValue(email_ID);
                        databaseReference.child(userID).child("mobile_no").setValue(mobile_no);
                        Intent intent = new Intent(getApplicationContext(), Home_admin.class);
                        startActivity(intent);




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



    }
    private void uploadProfileImage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Set your profile");
        progressDialog.setMessage("Please wait, while we are setting your data ");
        progressDialog.show();

        if (imageUri != null) {

            // Create a reference to 'images/mountains.jpg'
            storageReference = FirebaseStorage.getInstance().getReference("Admins/Profiles/*"+userID);

            storageReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), Home_admin.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            progressDialog.setMessage("Uploading......");
                        }
                    });
        }
        else {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),"Image not changed!!",Toast.LENGTH_SHORT).show();
        }
    }



    ActivityResultLauncher<String> mPhoto = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {

                    circular.setImageURI(result);
                    imageUri = result;
                }
            }
    );
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Home_admin.class));
    }
}