package com.ssquare.bullsapp;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.ssquare.bullsapp.authentication.Login;

public class LandingPage extends AppCompatActivity {

    FirebaseAuth mAuth;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Bull's Rent");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_200)));
        mAuth = FirebaseAuth.getInstance();

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment()).commit();

        bottomNavigationView.setSelectedItemId(R.id.home_nav);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.home_nav:
                        fragment = new HomeFragment();
                        break;
                    case  R.id.fav_nav:
                        fragment = new FavoriteFragment();
                        break;
                    case R.id.search_nav:
                        fragment = new SearchFragment();
                        break;
                    case R.id.profile_nav:
                        fragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,fragment).commit();
                return true;
            }
        });


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
        Intent intent = new Intent(getApplicationContext(),UserProfile.class);
        startActivity(intent);
    }

    void logOut(){
        try {
            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(), Login.class));
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Logout failed",Toast.LENGTH_SHORT).show();
        }
    }
}