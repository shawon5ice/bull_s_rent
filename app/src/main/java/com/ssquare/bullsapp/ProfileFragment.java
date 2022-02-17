package com.ssquare.bullsapp;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.ssquare.bullsapp.authentication.Login;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    private TextInputLayout userName,name,phoneNo,password,email;
    private TextView name_TV,userName_TV;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        name = view.findViewById(R.id.fragment_profile_name_ET);
//        progressBar = view.findViewById(R.id.profileLoadingProgressBar);
        email = view.findViewById(R.id.fragment_profile_email_ET);
        phoneNo = view.findViewById(R.id.fragment_profile_phoneNo_ET);
        password = view.findViewById(R.id.fragment_profile_password_ET);
        name_TV = view.findViewById(R.id.fragment_full_name_TV);
        userName_TV = view.findViewById(R.id.fragment_user_name_TV);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        db.collection("users")
                .document(mAuth.getCurrentUser().getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snapshot = task.getResult();
                            if (snapshot.exists()) {
                                Log.i("DB","in snapshot");
                                Log.i("DB",snapshot.getString("email"));
                                Log.i("test", snapshot.toString());
                                String n = snapshot.getString("name");
                                System.out.println(n);
                                name.getEditText().setText(snapshot.getString("name"));
                                email.getEditText().setText(snapshot.getString("email"));
                                phoneNo.getEditText().setText(snapshot.getString("phoneNo"));
                                password.getEditText().setText("arman");
                                name_TV.setText(n);
                                userName_TV.setText(snapshot.getString("userName"));

                            } else {
                                Toast.makeText(getActivity(), "no result found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed to get data", Toast.LENGTH_SHORT).show();
                    }
                });

        return view;
    }
}