package com.ssquare.bullsapp;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ssquare.bullsapp.adapters.NoteAdapter;
import com.ssquare.bullsapp.models.NoteModel;
import com.ssquare.bullsapp.models.UserModelClass;
import com.ssquare.bullsapp.utils.ExampleBottomSheetDialog;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomePage extends AppCompatActivity implements ExampleBottomSheetDialog.BottomSheetListener {

    FirebaseAuth mAuth;
    FloatingActionButton fab;
    private String uid;

    private ArrayList<NoteModel> notes;

    private RecyclerView taskRecyclerView;
    private NoteAdapter noteAdapter;
    private ProgressBar progressBar;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Bull's Rent");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_200)));

        notes = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        fab = findViewById(R.id.addTaskId);
        progressBar = findViewById(R.id.progressBarId);

        String uid = mAuth.getCurrentUser().getUid();

        taskRecyclerView = findViewById(R.id.taskRecyclerView);

        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(this);
        taskRecyclerView.setAdapter(noteAdapter);
        rootNode = FirebaseDatabase.getInstance("https://bull-s-rent-625fb-default-rtdb.asia-southeast1.firebasedatabase.app/");


        noteAdapter.setTask(notes);
        dataBaseCheck();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExampleBottomSheetDialog bottomSheetDialog = new ExampleBottomSheetDialog();
                bottomSheetDialog.show(getSupportFragmentManager(),"exampleBottomSheet");
//                startActivity(new Intent(getApplicationContext(),AddNote.class));
            }
        });

    }

    void dataBaseCheck() {
        reference = rootNode.getReference("users").child(mAuth.getCurrentUser().getUid()).child("notes");
        progressBar.setVisibility(View.VISIBLE);
        try {
            Query allNotes = reference.orderByChild("time");
            allNotes.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds: snapshot.getChildren()) {

                        NoteModel note = ds.getValue(NoteModel.class);
                        String time = note.getTime();
                        String timeStamp = note.getTimeStamp();
                        String title = note.getTitle();
                        String description = note.getDescription();
                        String priority = note.getPriority();


                        // Then add the value you require to add in your ArrayList
                        notes.add(new NoteModel(timeStamp,time,title,description,priority));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }finally {
            progressBar.setVisibility(View.INVISIBLE);
        }
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_profile:
                profileView();
                return true;

            case R.id.action_logout:
                logOut();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page_menu, menu);
        return true;
    }


    void profileView(){
        String uId = mAuth.getCurrentUser().getUid();
        System.out.println(uId);
        DatabaseReference reference = FirebaseDatabase.getInstance("https://bull-s-rent-625fb-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("users");
        Query checkUser = reference.orderByChild("uid").equalTo(uId);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nameFromDB = snapshot.child(uId).child("name").getValue(String.class);
                    String emailFromDB = snapshot.child(uId).child("email").getValue(String.class);
                    String phoneNoFromDB = snapshot.child(uId).child("phoneNo").getValue(String.class);
                    String userNameFromDB = snapshot.child(uId).child("userName").getValue(String.class);
                    String passwordFromDB = snapshot.child(uId).child("password").getValue(String.class);

                    Intent intent = new Intent(getApplicationContext(),UserProfile.class);
                    UserModelClass userModelClass = new UserModelClass(uId,nameFromDB,userNameFromDB,emailFromDB,phoneNoFromDB,passwordFromDB);
                    intent.putExtra("userDetails", userModelClass);

                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
            }
        });

    }

    void logOut(){
        try {
            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(), Login.class));
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Logout failed",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onButtonClicked(String title,String desc) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        long date = new Date().getTime();

        String timeStamp = formatter.format(date);

        NoteModel note = new NoteModel(timeStamp,String.valueOf(date),title,desc,"0");


        rootNode = FirebaseDatabase.getInstance("https://bull-s-rent-625fb-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference reference1 = rootNode.getReference("users");

        reference1.child(mAuth.getCurrentUser().getUid())
                .child("notes")
                .child(String.valueOf(date))
                .setValue(note);
        dataBaseCheck();
    }
}