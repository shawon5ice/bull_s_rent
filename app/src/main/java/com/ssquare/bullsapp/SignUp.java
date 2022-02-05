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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private TextInputLayout fullName,userName,email,password,phoneNo;
    private ImageView image;
    private TextView logo_name,logo_slogan;
    private Button signUpButton,gotoSignInButton;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        image = findViewById(R.id.signUpLogoImage);
        logo_name = findViewById(R.id.signup_logo_name);
        logo_slogan = findViewById(R.id.signUp_logo_slogan);
        fullName = findViewById(R.id.signUpFullName);
        userName = findViewById(R.id.signUpUserName);
        email = findViewById(R.id.signUpEmail);
        password = findViewById(R.id.signUpPassword);
        phoneNo = findViewById(R.id.signUpPhoneNo);
        signUpButton = findViewById(R.id.signUpButton);
        gotoSignInButton = findViewById(R.id.gotoSignInButton);

        gotoSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                Pair pairs[] = new Pair[10];
                pairs[0] = new Pair<View,String>(image,"logo_image");
                pairs[1] = new Pair<View,String>(logo_name,"logo_name");
                pairs[2] = new Pair<View,String>(logo_slogan,"logo_slogan");
                pairs[3] = new Pair<View,String>(fullName,"full_name");
                pairs[4] = new Pair<View,String>(userName,"user_name");
                pairs[5] = new Pair<View,String>(email,"email");
                pairs[6] = new Pair<View,String>(password,"password");
                pairs[7] = new Pair<View,String>(phoneNo,"phone_no");
                pairs[8] = new Pair<View,String>(signUpButton,"sign_up_button");
                pairs[9] = new Pair<View,String>(gotoSignInButton,"goto_signIn_button");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = fullName.getEditText().toString();
                String uName = fullName.getEditText().toString();
                String mail = fullName.getEditText().toString();
                String pass = fullName.getEditText().toString();
                String phone = fullName.getEditText().toString();

                UserHelperClass userHelperClass = new UserHelperClass(name,uName,mail,phone,pass);

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                reference.child("arman").setValue(userHelperClass);
            }
        });
    }
}