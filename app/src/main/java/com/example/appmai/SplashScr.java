package com.example.appmai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScr extends AppCompatActivity {

    public static String SHARED_PREFERENCES_NAME = "DOCTOR_APPOITNMENT_APP_PATIENTS_SHARED_PREFERENCES";
    public static String SHARED_PREFERENCES_KAY_FLAG;
    String EMAIL,PASSWORD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_s);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES_NAME,MODE_PRIVATE);
                Boolean flag =  preferences.getBoolean(SHARED_PREFERENCES_KAY_FLAG,false);
                EMAIL = preferences.getString("EMAIL","EXXX");
                PASSWORD = preferences.getString("PASSWORD","PXXX");
                if (flag){
                    Intent intent = new Intent(SplashScr.this,Avaliable.class);
                    intent.putExtra("EMAIL",EMAIL);
                    intent.putExtra("PASSWORD",PASSWORD);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(SplashScr.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        }, 4000);

    }


}