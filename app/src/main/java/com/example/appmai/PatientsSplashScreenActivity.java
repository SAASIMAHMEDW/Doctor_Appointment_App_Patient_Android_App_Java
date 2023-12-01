package com.example.appmai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class PatientsSplashScreenActivity extends AppCompatActivity {

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
                x();
            }
        }, 4000);

    }

    public void x(){
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES_NAME,MODE_PRIVATE);
        Boolean flag =  preferences.getBoolean(SHARED_PREFERENCES_KAY_FLAG,false);
        EMAIL = preferences.getString("SHARED_EMAIL","EXXX");
        PASSWORD = preferences.getString("SHARED_PASSWORD","PXXX");
        Intent intent;
        if (flag){
            intent = new Intent(PatientsSplashScreenActivity.this, MainPanel.class);
            intent.putExtra("SHARED_EMAIL_INTENT",EMAIL);
            intent.putExtra("SHARED_PASSWORD_INTENT",PASSWORD);
        }else {
            intent = new Intent(PatientsSplashScreenActivity.this, PatientsLoginActivity.class);
        }
        startActivity(intent);
        finish();
    }


}