package com.example.csadmin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Admin_Login extends AppCompatActivity {
    Button btn_sign;
    EditText editTextemail_ID,editTextpassword;

    ProgressBar progressBar;
    TextView btn_reg,forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
    }
}