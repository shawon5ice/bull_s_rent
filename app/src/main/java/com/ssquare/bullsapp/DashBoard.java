package com.ssquare.bullsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class DashBoard extends AppCompatActivity {

    Button logout,profileView;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        logout = findViewById(R.id.logout);
        profileView = findViewById(R.id.profileView);
        mAuth = FirebaseAuth.getInstance();
    }
}