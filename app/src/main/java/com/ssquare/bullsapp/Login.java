package com.ssquare.bullsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ssquare.bullsapp.models.UserModelClass;

public class Login extends AppCompatActivity {

    private boolean logButttonClicked = false;
    public static final String USER_KEY = "userDetails";

    private Button callSignUp_btn,login_btn,forget_btn;
    private ImageView image;
    private TextView logoText,sloganText;
    private TextInputLayout userName,password;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = Login.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(Login.this, android.R.color.darker_gray));

        setContentView(R.layout.activity_login);

        callSignUp_btn = findViewById(R.id.gotoSinUpButton);
        login_btn = findViewById(R.id.login_btn);
        forget_btn = findViewById(R.id.forget_btn);
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.logo_slogan);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();


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

        userName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(logButttonClicked){
                    validateUsername();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(logButttonClicked){
                    validatePassword();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            Intent intent = new Intent(getApplicationContext(),DashBoard.class);
            startActivity(intent);
            finish();
        }
    }

    private Boolean validateUsername() {
        String val = userName.getEditText().getText().toString();

        if (val.isEmpty()) {
            userName.setError("Field cannot be empty");
            return false;
        }else {
            userName.setError(null);
            userName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();

        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View view){
        logButttonClicked = true;
        if(!validatePassword() | !validateUsername()){
            return;
        }

        final String userEnteredUserName = userName.getEditText().getText().toString().trim();
        final String userEnteredPassword = password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance("https://bull-s-rent-625fb-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("users");
        Query checkUser = reference.orderByChild("userName").equalTo(userEnteredUserName);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    userName.setErrorEnabled(false);
                    String passwordFromDB = snapshot.child(userEnteredUserName).child("password").getValue(String.class).toString().trim();

                    if(passwordFromDB.equals(userEnteredPassword)){
                        password.setErrorEnabled(false);
                        String nameFromDB = snapshot.child(userEnteredUserName).child("name").getValue(String.class);
                        String emailFromDB = snapshot.child(userEnteredUserName).child("email").getValue(String.class);
                        String phoneNoFromDB = snapshot.child(userEnteredUserName).child("phoneNo").getValue(String.class);
                        String userNameFromDB = snapshot.child(userEnteredUserName).child("userName").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(),UserProfile.class);
                        UserModelClass userModelClass = new UserModelClass(nameFromDB,userNameFromDB,emailFromDB,phoneNoFromDB,passwordFromDB);
                        intent.putExtra("userDetails", userModelClass);

                        startActivity(intent);
                    }else{
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                }else{
                    userName.setError("No such user exits");
                    userName.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}