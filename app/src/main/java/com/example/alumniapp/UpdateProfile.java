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
import android.widget.TextView;
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

public class UpdateProfile extends AppCompatActivity {

    CircleImageView circular;
    ProgressBar progressBar;
    EditText name,mid_name,surname,email,mobile,grade,year,exp,company,some;
    TextView class1,grades,ugorpg;
    Button update;
    private Uri imageUri;
    private FirebaseStorage storage,storages;
    private StorageReference storageReference,storageReferences,storageReferencefor;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference databaseReference,firebaseDatabasestudent;
    private String userID;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);
        update = findViewById(R.id.update);
        ugorpg = findViewById(R.id.textView5);
        grades = findViewById(R.id.gradess);
        class1 = findViewById(R.id.class1);
        circular = findViewById(R.id.profile_image);
        name = findViewById(R.id.et1);
        mid_name = findViewById(R.id.et2);
        surname = findViewById(R.id.et3);
        email = findViewById(R.id.rg_email);
        mobile = findViewById(R.id.mobile);
        grade = findViewById(R.id.grades);
        year = findViewById(R.id.spinner4);
        exp = findViewById(R.id.et22);
        company = findViewById(R.id.et21);
        some = findViewById(R.id.et23);

        storage = FirebaseStorage.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("Alumnis/Profiles");
        user = FirebaseAuth.getInstance().getCurrentUser();
        storages = FirebaseStorage.getInstance();
        storageReferences = FirebaseStorage.getInstance().getReference("Students/Profiles");
        storageReferencefor = FirebaseStorage.getInstance().getReference("Students/Profiles/*"+userID);
        String a = storageReferencefor.toString();
        databaseReference = FirebaseDatabase.getInstance().getReference("Alumnis");
        firebaseDatabasestudent = FirebaseDatabase.getInstance().getReference("Students");
        userID = user.getUid();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseDatabasestudent.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Student userProfile = snapshot.getValue(Student.class);
                        if (userProfile != null){
                            uploadProfileImages();
                            updateDatas();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Alumni userProfile = snapshot.getValue(Alumni.class);

                        if(userProfile != null){
                            uploadProfileImage();
                            updateData();
                            //UpdateAlumniInfo();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });







            }
        });
        circular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPhoto.launch("image/*");
            }
        });



        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Alumni userProfile = snapshot.getValue(Alumni.class);

                if(userProfile != null){
                    String fullname = userProfile.first_name;
                    String m = userProfile.mid_name;
                    String l = userProfile.last_name;
                    String e = userProfile.email_ID;
                    String mo = userProfile.mobile_no;
                    String g = userProfile.grade;
                    CharSequence y = userProfile.year;
                    String c = userProfile.company_name;
                    String ex = userProfile.work_experience;
                    String so = userProfile.something;



                    name.setText(fullname);
                    mid_name.setText(m);
                    surname.setText(l);
                    email.setText(e);
                    mobile.setText(mo);
                    grade.setText(g);
                    year.setText(y);
                    company.setText(c);
                    exp.setText(ex);
                    some.setText(so);


                    class1.setVisibility(View.GONE);





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
                    String fullname = userProfile.first_name;
                    String m = userProfile.mid_name;
                    String l = userProfile.last_name;
                    String e = userProfile.email_ID;
                    String mo = userProfile.mobile_no;
                    String y = userProfile.class_year;



                    name.setText(fullname);
                    mid_name.setText(m);
                    surname.setText(l);
                    email.setText(e);
                    mobile.setText(mo);
                    grade.setText(y);

                    grades.setVisibility(View.GONE);
                    ugorpg.setVisibility(View.GONE);
                    company.setVisibility(View.GONE);
                    exp.setVisibility(View.GONE);
                    some.setVisibility(View.GONE);
                    year.setVisibility(View.GONE);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Something happened wrong!",Toast.LENGTH_LONG).show();

            }
        });




        Userinfos();
        Userinfo();


    }

    private void Userinfo() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference photoReference= storageReference.child("Alumnis/Profiles/*"+userID);

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
    private void Userinfos() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference photoReference= storageReference.child("Students/Profiles/*"+userID);

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

                        String first_name = name.getText().toString();
                        String mid_name1 = mid_name.getText().toString();
                        String last_name = surname.getText().toString();
                        String email_ID = email.getText().toString();
                        String mobile_no = mobile.getText().toString();
                        String grade1 = grade.getText().toString();
                        String year1 = year.getText().toString();
                        String company_name = company.getText().toString();
                        String work_experience = exp.getText().toString();
                        String something = some.getText().toString();

                        databaseReference.child(userID).child("first_name").setValue(first_name);
                        databaseReference.child(userID).child("mid_name").setValue(mid_name1);
                        databaseReference.child(userID).child("last_name").setValue(last_name);
                        databaseReference.child(userID).child("email_ID").setValue(email_ID);
                        databaseReference.child(userID).child("mobile_no").setValue(mobile_no);
                        databaseReference.child(userID).child("grade").setValue(grade1);
                        databaseReference.child(userID).child("year").setValue(year1);
                        databaseReference.child(userID).child("company_name").setValue(company_name);
                        databaseReference.child(userID).child("work_experience").setValue(work_experience);
                        databaseReference.child(userID).child("something").setValue(something);
                        Intent intent = new Intent(getApplicationContext(),Home.class);
                        startActivity(intent);




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



    }
    private   void  updateDatas(){
        StorageReference storage2 = storage.getReference().child("Students/Profiles/*"+ userID);
        storage2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageUrl = uri.toString();
                firebaseDatabasestudent = firebaseDatabase.getReference("Students");
                firebaseDatabasestudent.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        firebaseDatabasestudent.child(userID).child("randomKey").setValue(userID);
                        firebaseDatabasestudent.child(userID).child("profileUrl").setValue(imageUrl);

                        String first_name = name.getText().toString();
                        String mid_name1 = mid_name.getText().toString();
                        String last_name = surname.getText().toString();
                        String email_ID = email.getText().toString();
                        String mobile_no = mobile.getText().toString();
                        String class1 = grade.getText().toString();


                        firebaseDatabasestudent.child(userID).child("first_name").setValue(first_name);
                        firebaseDatabasestudent.child(userID).child("mid_name").setValue(mid_name1);
                        firebaseDatabasestudent.child(userID).child("last_name").setValue(last_name);
                        firebaseDatabasestudent.child(userID).child("email_ID").setValue(email_ID);
                        firebaseDatabasestudent.child(userID).child("mobile_no").setValue(mobile_no);
                        firebaseDatabasestudent.child(userID).child("grade").setValue(class1);
                        Intent intent = new Intent(getApplicationContext(),Home.class);
                        startActivity(intent);


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
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),Home.class));
    }

    private void uploadProfileImage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Set your profile");
        progressDialog.setMessage("Please wait, while we are setting your data ");
        progressDialog.show();

        if (imageUri != null) {

            // Create a reference to 'images/mountains.jpg'
            storageReference = FirebaseStorage.getInstance().getReference("Alumnis/Profiles/*"+userID);
            storageReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(),Home.class);
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
        }else {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),"Image not changed!!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),Home.class);
            startActivity(intent);
        }

    }
    private void uploadProfileImages() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Set your profile");
        progressDialog.setMessage("Please wait, while we are setting your data ");
        progressDialog.show();

        if (imageUri != null) {

            // Create a reference to 'images/mountains.jpg'
            storageReferences = FirebaseStorage.getInstance().getReference("Students/Profiles/*"+userID);

                storageReferences.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(),Home.class);
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

}