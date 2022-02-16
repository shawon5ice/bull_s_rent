package com.ssquare.bullsapp.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ssquare.bullsapp.LandingPage;
import com.ssquare.bullsapp.R;

public class Login extends AppCompatActivity {


    private boolean logButttonClicked = false;
    public static final String USER_KEY = "userDetails";

    private Button callSignUp_btn,login_btn,forget_btn,verify_btn;
    private ImageView image;
    private TextView logoText,sloganText;
    private TextInputLayout email,password;
    private String uid;
    private ProgressBar progressBar;
    Handler handler = new Handler();
    Runnable refresh;
    int reloadValue = 0;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = Login.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(Login.this, android.R.color.darker_gray));

        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        callSignUp_btn = findViewById(R.id.gotoSinUpButton);
        login_btn = findViewById(R.id.login_btn);
        forget_btn = findViewById(R.id.forget_btn);
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.logo_slogan);
        email = findViewById(R.id.signInEmail);
        password = findViewById(R.id.password);
//        verify_btn = findViewById(R.id.retry_btn);
        progressBar = findViewById(R.id.LoginProgressBarId);

        mAuth = FirebaseAuth.getInstance();
        uid = getIntent().getStringExtra("uid");

        callSignUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("sign up called");
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                Pair pairs[] = new Pair[7];
                pairs[0] = new Pair<View, String>(image, "logo_image");
                pairs[1] = new Pair<View, String>(logoText, "logo_name");
                pairs[2] = new Pair<View, String>(sloganText, "logo_slogan");
                pairs[3] = new Pair<View, String>(email, "email_tran");
                pairs[4] = new Pair<View, String>(password, "password_tran");
                pairs[5] = new Pair<View, String>(login_btn, "login_btn");
                pairs[6] = new Pair<View, String>(callSignUp_btn, "gotoSinUpButton");

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                startActivity(intent, activityOptions.toBundle());
            }
        });

//            verify_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//                            Snackbar.make(verify_btn, "Please check your mail to \nverify this account", Snackbar.LENGTH_SHORT).show();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Snackbar.make(verify_btn, e.getMessage(), Snackbar.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            });

        email.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (logButttonClicked) {
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
                if (logButttonClicked) {
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
        if(user!=null && user.getEmail()!=null){
            Intent intent = new Intent(getApplicationContext(), LandingPage.class);
            startActivity(intent);
            finish();
        }
    }

    private Boolean validateUsername() {
        String val = email.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            email.setError("Field cannot be empty");
            return false;
        }else {
            email.setError(null);
            email.setErrorEnabled(false);
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
        progressBar.setVisibility(View.VISIBLE);
        login_btn.setEnabled(false);
        login_btn.setBackgroundColor(getColor(R.color.little_white));
        logButttonClicked = true;
        if(!validatePassword() | !validateUsername()){
            return;
        }

        final String userEnteredEmail = email.getEditText().getText().toString().trim();
        final String userEnteredPassword = password.getEditText().getText().toString().trim();


        mAuth.signInWithEmailAndPassword(userEnteredEmail,userEnteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), LandingPage.class);
                    startActivity(intent);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Snackbar.make(login_btn,e.getLocalizedMessage(),Snackbar.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                login_btn.setEnabled(true);
                login_btn.setBackgroundColor(getColor(R.color.black));
            }
        });

    }


}