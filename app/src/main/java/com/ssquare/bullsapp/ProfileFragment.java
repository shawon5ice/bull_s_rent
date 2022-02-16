package com.ssquare.bullsapp;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private TextInputLayout userName,name,phoneNo,password,email;
    private TextView name_TV,userName_TV;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    private ProgressBar progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        name = view.findViewById(R.id.profile_name_ET);
        progressBar = view.findViewById(R.id.profileLoadingProgressBar);
        email = view.findViewById(R.id.profile_email_ET);
        phoneNo = view.findViewById(R.id.profile_phoneNo_ET);
        password = view.findViewById(R.id.profile_password_ET);
        name_TV = view.findViewById(R.id.full_name_TV);
        userName_TV = view.findViewById(R.id.user_name_TV);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onStart() {
        //data is not updating according to db
        super.onStart();
        try {
            progressBar.setVisibility(View.VISIBLE);
            db.collection("users")
                    .document(mAuth.getCurrentUser().getUid()).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot snapshot = task.getResult();
                                if (snapshot.exists()) {
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
        } finally {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}