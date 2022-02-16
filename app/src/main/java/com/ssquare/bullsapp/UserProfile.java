package com.ssquare.bullsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserProfile extends AppCompatActivity {

    public static final String USER_KEY = "userDetails";
//    private UserModelClass user;

    private TextInputLayout userName,name,phoneNo,password,email;
    private TextView name_TV,userName_TV;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_200)));
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        String uId = mAuth.getCurrentUser().getUid();
        name = findViewById(R.id.profile_name_ET);
        phoneNo = findViewById(R.id.profile_phoneNo_ET);
        password = findViewById(R.id.profile_password_ET);
        email = findViewById(R.id.profile_email_ET);

        name_TV = findViewById(R.id.full_name_TV);
        userName_TV = findViewById(R.id.user_name_TV);

        db.collection("users")
                .document(mAuth.getCurrentUser().getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot snapshot = task.getResult();
                            if(snapshot.exists()){
                                Log.i("test",snapshot.toString());
                                String n = snapshot.getString("name");
                                name.getEditText().setText(snapshot.getString("name"));
                                email.getEditText().setText(snapshot.getString("email"));
                                phoneNo.getEditText().setText(snapshot.getString("phoneNo"));
                                password.getEditText().setText("arman");
                                name_TV.setText(snapshot.getString("name"));
                                userName_TV.setText(snapshot.getString("userName"));
                            }else{
                                Toast.makeText(getApplicationContext(),"no result found",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Failed to get data",Toast.LENGTH_SHORT).show();
                    }
                });


//        UserModelClass user = getIntent().getParcelableExtra(USER_KEY);
//
//        if(user != null){
//            name.getEditText().setText(user.getName());
//            email.getEditText().setText(user.getMail());
//            phoneNo.getEditText().setText(user.getPhone());
//            password.getEditText().setText(user.getPass());
//            name_TV.setText(user.getName());
//            userName_TV.setText(user.getuName());
//        }
    }
}