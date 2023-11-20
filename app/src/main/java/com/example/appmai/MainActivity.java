package com.example.appmai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    String EMAIL, PASSWORD;
    Button btn_login;
    TextView go_to_Signup;
    ProgressBar progg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        go_to_Signup = findViewById(R.id.goto_signup);
        go_to_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_Signup();
            }
        });

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_login_user_data();
                check_email_password();
                vefify_user_login(EMAIL, PASSWORD);

            }
        });
    }

    public void vefify_user_login(String email, String password) {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progg.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Authentication Successfull", Toast.LENGTH_SHORT).show();
                            //user_main_activity();
                            send_email_password();
                            sharedPreferences_check();
                        }
                        else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public void sharedPreferences_check(){
        SharedPreferences sharedPreferences = getSharedPreferences(SplashScr.SHARED_PREFERENCES_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("EMAIL",EMAIL);
        editor.putString("PASSWORD",PASSWORD);
        editor.putBoolean(SplashScr.SHARED_PREFERENCES_KAY_FLAG,true);
        editor.apply();

    }

    public void goto_Signup() {
        Intent intent = new Intent(getApplicationContext(), SignUpapp.class);
        startActivity(intent);
        //finish();

    }

    //public void user_main_activity() {
        //Intent intent = new Intent(getApplicationContext(), Avaliable.class);
       // startActivity(intent);
       // finish();

   // }

    public void get_login_user_data() {
        EditText user_email;
        EditText user_password;
        progg = findViewById(R.id.show_progress);
        user_email = findViewById(R.id.user_login_email);
        user_password = findViewById(R.id.user_login_password);
        EMAIL = user_email.getText().toString();
        PASSWORD = user_password.getText().toString();

    }

    public void check_email_password() {
        progg.setVisibility(View.VISIBLE);

        if (TextUtils.isEmpty(EMAIL)) {
            Toast.makeText(MainActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(PASSWORD)) {
            Toast.makeText(MainActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }

    }


    public void send_email_password(){
        Intent intent = new Intent(getApplicationContext(),Avaliable.class);
        intent.putExtra("email",EMAIL);
        intent.putExtra("password",PASSWORD);
        startActivity(intent);
        finish();
    }
}