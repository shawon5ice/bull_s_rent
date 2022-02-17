package com.ssquare.bullsapp.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ssquare.bullsapp.R;
import com.ssquare.bullsapp.models.UserModel;

public class SignUp extends AppCompatActivity {

    private TextInputLayout fullName,userName,email,password,phoneNo;
    private ImageView image;
    private TextView logo_name,logo_slogan;
    private Button signUpButton,gotoSignInButton;
    private ProgressBar progressIndicator;

    private FirebaseAuth mAuth;
    private boolean regClicked = false;
    private boolean verified = false;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
        progressIndicator = findViewById(R.id.signUpProgressIndicator);

        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();



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

        fullName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (regClicked){
                    validateName();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        email.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (regClicked){
                    validateEmail();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        userName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (regClicked){
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
                if (regClicked){
                    validatePassword();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        phoneNo.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (regClicked){
                    validatePhoneNo();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    private Boolean validateName() {
        String val = fullName.getEditText().getText().toString();
        if (val.isEmpty()) {
            fullName.setError("Field cannot be empty");
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername() {
        String val = userName.getEditText().getText().toString();
        String noWhiteSpace = "^\\\\s*$";

        if (val.isEmpty()) {
            userName.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            userName.setError("Username too long");
            return false;
        } else if (val.contains(" ")) {
            userName.setError("White Spaces are not allowed");
            return false;
        } else {
            userName.setError(null);
            userName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = email.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            email.setError("Invalid email address");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePhoneNo() {
        String val = phoneNo.getEditText().getText().toString();

        if (val.isEmpty()) {
            phoneNo.setError("Field cannot be empty");
            return false;
        } else {
            phoneNo.setError(null);
            phoneNo.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            password.setError("Password is too weak");
            password.setErrorEnabled(false);
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }


    public void regUser(View view){

        regClicked = true;
        if(!validateName() | !validatePassword() | !validatePhoneNo() | !validateEmail() | !validateUsername()){
            return;
        }
        progressIndicator.setVisibility(View.VISIBLE);

        String name = fullName.getEditText().getText().toString().trim();
        String uName = userName.getEditText().getText().toString().trim();
        String mail = email.getEditText().getText().toString().trim();
        String pass = password.getEditText().getText().toString().trim();
        String phone = phoneNo.getEditText().getText().toString().trim();


//        //phone Number Authentication page.
//        Intent intent = new Intent(getApplicationContext(),VerifyPhoneNumber.class);
//        intent.putExtra("phoneNo",phone);
//        startActivity(intent);

        try {
            signUpButton.setEnabled(false);
            gotoSignInButton.setEnabled(false);
            mAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String uid = mAuth.getCurrentUser().getUid();
                        UserModel user = new UserModel(uid,name,uName,mail,phone,verified);

                        //FireStoreDb data sending
                        db.collection("users").document(uid).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Intent intent = new Intent(getApplicationContext(),Login.class);
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(view,e.getMessage(),Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        Snackbar.make(view,"Authentication Failed",Snackbar.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    email.setError(e.getMessage());
                }
            });
        }finally {
            progressIndicator.setVisibility(View.GONE);
            signUpButton.setEnabled(true);
            gotoSignInButton.setEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        progressIndicator.setVisibility(View.GONE);
        signUpButton.setEnabled(true);
        gotoSignInButton.setEnabled(true);
    }
}