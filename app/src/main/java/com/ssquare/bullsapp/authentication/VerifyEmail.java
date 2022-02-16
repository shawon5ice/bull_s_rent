package com.ssquare.bullsapp.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ssquare.bullsapp.R;
import com.ssquare.bullsapp.models.UserModel;

public class VerifyEmail extends AppCompatActivity {

    private LinearProgressIndicator progressIndicator;
    private TextView progressTV;
    private Button resend;
    int progress = 100;
    int refreshTime = 1000;
    private Handler handler = new Handler();

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);
        getSupportActionBar().hide();

        progressTV = findViewById(R.id.waitTimerTV);
        progressIndicator = findViewById(R.id.verificationProgressBar);
        resend = findViewById(R.id.resendVerificationButton);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        resendMail();

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendMail();
            }
        });


    }

    void resendMail() {
        progress = 100;
        resend.setEnabled(false);
        mAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void unused) {
                progressIndicator.setProgress(progress);
                progressTV.setText("Wait" + progress + " seconds to resend verification email again");
                mAuth.getCurrentUser().reload();
                if (mAuth.getCurrentUser().isEmailVerified()) {
                    FirebaseFirestore.getInstance().collection("users").document(mAuth.getCurrentUser().getUid()).get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot snapshot = task.getResult();
                                        if (snapshot.exists()) {
                                            UserModel user = new UserModel(snapshot.getString("uid"), snapshot.getString("name"),
                                                    snapshot.getString("userName"), snapshot.getString("email"), snapshot.getString("phoneNo"), true);
                                            Intent intent = new Intent(getApplicationContext(), Login.class);
                                            intent.putExtra("uid", mAuth.getCurrentUser().getUid());
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }
                            });
                }
            }
        });

        resend.setEnabled(true);
    }
}