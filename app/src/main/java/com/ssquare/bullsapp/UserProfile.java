package com.ssquare.bullsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.ssquare.bullsapp.models.UserModelClass;

public class UserProfile extends AppCompatActivity {

    public static final String USER_KEY = "userDetails";
//    private UserModelClass user;

    private TextInputLayout userName,name,phoneNo,password,email;
    private TextView name_TV,userName_TV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_200)));

        name = findViewById(R.id.profile_name_ET);
        phoneNo = findViewById(R.id.profile_phoneNo_ET);
        password = findViewById(R.id.profile_password_ET);
        email = findViewById(R.id.profile_email_ET);

        name_TV = findViewById(R.id.full_name_TV);
        userName_TV = findViewById(R.id.user_name_TV);

        UserModelClass user = getIntent().getParcelableExtra(USER_KEY);

        if(user != null){
            name.getEditText().setText(user.getName());
            email.getEditText().setText(user.getMail());
            phoneNo.getEditText().setText(user.getPhone());
            password.getEditText().setText(user.getPass());
            name_TV.setText(user.getName());
            userName_TV.setText(user.getuName());
        }
    }
}