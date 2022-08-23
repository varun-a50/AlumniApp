package com.example.csadmin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.util.Calendar;
import java.util.Locale;

public class Event_schedule_Admin extends AppCompatActivity {
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    Button button, button2, button3;
    TextView dateView, timeView;
    EditText title, details,name,last1;
    int hour, minute;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    ProgressBar progressBar;
    private FirebaseUser user;
    private String adminID;
    String UDI;
    FirebaseStorage firebaseStorage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_schedule_admin);

        name = findViewById(R.id.et1);
        last1 = findViewById(R.id.et3);

        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        adminID = user.getUid();
        firebaseStorage = FirebaseStorage.getInstance();



        button3 = findViewById(R.id.post);
        title = findViewById(R.id.title1);
        details = findViewById(R.id.details);
        dateView = (TextView) findViewById(R.id.showdate);
        timeView = findViewById(R.id.showtime);
        button2 = findViewById(R.id.timeselector);
        firebaseDatabase = FirebaseDatabase.getInstance();


        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerEvent();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popTimePicker();

            }
        });

        button = findViewById(R.id.dateselector);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Event_schedule_Admin.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = day + "/" + month + "/" + year;
                dateView.setText(date);
            }
        };

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
                Toast.makeText(Event_schedule_Admin.this,"Something happened wrong!",Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);

            }
        });

    }

    private void registerEvent() {



        StorageReference storage = firebaseStorage.getReference().child("Admins/Profiles/*"+ adminID);
        storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageUrl = uri.toString();
                String uid = adminID;
                String First_Name = name.getText().toString();
                String Last_Name = last1.getText().toString();
                String Title = title.getText().toString();
                String Details = details.getText().toString();
                String Date = dateView.getText().toString();
                String Time = timeView.getText().toString();
                String randomkey = firebaseDatabase.getReference().push().getKey();


                if (uid.isEmpty()){
                    Toast.makeText(getApplicationContext(),"NO UID found",Toast.LENGTH_LONG).show();
                }

                if (Title.isEmpty()) {
                    title.requestFocus();
                    title.setError("needed");
                    return;
                }
                if (Details.isEmpty()) {
                    details.requestFocus();
                    return;
                }
                if (Date.isEmpty()) {
                    dateView.requestFocus();
                    return;
                }
                if (Title.isEmpty()) {
                    timeView.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                Event event = new Event(imageUrl,uid,First_Name,Last_Name,Title,Details,Date,Time,randomkey);

                FirebaseDatabase.getInstance().getReference("Events")
                        .child(randomkey)
                        .setValue(event).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Event_schedule_Admin.this, "Event posted", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), Home_admin.class);
                            startActivity(intent);
                            progressBar.setVisibility(View.GONE);

                        } else {
                            Toast.makeText(Event_schedule_Admin.this, "Event posting Failed", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });




            }
        });


    }

    public void popTimePicker(){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                timeView.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Home_admin.class));
    }

}