package com.example.appmai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Avaliable extends AppCompatActivity {
    String EMAIL,PASSWORD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliable);
        get_email_password();
        bottomNavBar();
    }

    public void bottomNavBar(){
        BottomNavigationView adminPanelBottomViewNav;
        adminPanelBottomViewNav = findViewById(R.id.adminPanelBottomViewNav);


        adminPanelBottomViewNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home){
                    fragmentLoader(new HomeFragment(),true);
                }else if (id == R.id.nav_list){
                    fragmentLoader(new ListFragment(),false);
                }else {
                    fragmentLoader(new ProfileFragment(),false);
                }

                return true;
            }
        });
        adminPanelBottomViewNav.setSelectedItemId(R.id.nav_home);

    }

    public void fragmentLoader(Fragment fragment, boolean flag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("EMAILX",EMAIL);
        bundle.putString("PASSWORDX",PASSWORD);
        fragment.setArguments(bundle);

        if (flag){
            ft.add(R.id.adminpanelContainer, fragment);
        }else {
            ft.replace(R.id.adminpanelContainer,fragment);
        }
        ft.commit();
    }
    public void get_email_password(){
        Intent intent = getIntent();
        EMAIL = intent.getStringExtra("email");
        PASSWORD = intent.getStringExtra("password");
    }

}