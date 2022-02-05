package com.ssquare.bullsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    private Button callSignUp_btn,login_btn,forget_btn;
    private ImageView image;
    private TextView logoText,sloganText;
    private TextInputLayout userName,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        callSignUp_btn = findViewById(R.id.gotoSinUpButton);
        login_btn = findViewById(R.id.login_btn);
        forget_btn = findViewById(R.id.forget_btn);
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.logo_slogan);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);


        callSignUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                Pair pairs[] = new Pair[7];
                pairs[0] = new Pair<View,String>(image,"logo_image");
                pairs[1] = new Pair<View,String>(logoText,"logo_name");
                pairs[2] = new Pair<View,String>(sloganText,"logo_slogan");
                pairs[3] = new Pair<View,String>(userName,"username_tran");
                pairs[4] = new Pair<View,String>(password,"password_tran");
                pairs[5] = new Pair<View,String>(login_btn,"login_btn");
                pairs[6] = new Pair<View,String>(callSignUp_btn,"gotoSinUpButton");

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
                startActivity(intent,activityOptions.toBundle());
            }
        });
    }
}