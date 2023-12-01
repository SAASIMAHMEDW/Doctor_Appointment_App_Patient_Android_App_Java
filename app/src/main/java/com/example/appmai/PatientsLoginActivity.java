package com.example.appmai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class PatientsLoginActivity extends AppCompatActivity {

    String EMAIL, PASSWORD;
    Button btn_login;
    TextView go_to_Signup;
    ProgressBar progg;
    EditText user_email;
    EditText user_password;
    CheckBox show_hide_password_checkbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializer();

        show_hide_password_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
//                    if (adminLoginPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD )
//                        adminLoginPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    user_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                    else adminLoginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    show_hide_password_checkbox.setText("Hide Password");
                    user_password.setSelection(user_password.length());
                }else {
                    user_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                    if (adminLoginPassword.getInputType() == InputType.TYPE_CLASS_TEXT )
//                        adminLoginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    show_hide_password_checkbox.setText("Show Password");
                    user_password.setSelection(user_password.length());
                }
            }
        });

        go_to_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_Signup();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickFieldsVisibility(false);
                get_login_user_data();
                check_email_password();

            }
        });
    }

    public void initializer(){
        go_to_Signup = findViewById(R.id.goto_signup);
        show_hide_password_checkbox = findViewById(R.id.show_hide_password_checkbox);
        progg = findViewById(R.id.show_progress);
        user_email = findViewById(R.id.user_login_email);
        user_password = findViewById(R.id.user_login_password);
        btn_login = findViewById(R.id.btn_login);
    }

    public void handleClickFieldsVisibility(boolean flag){
        go_to_Signup.setClickable(flag);
        show_hide_password_checkbox.setEnabled(flag);
        user_email.setEnabled(flag);
        user_password.setEnabled(flag);
        btn_login.setEnabled(flag);
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
                            Toast.makeText(PatientsLoginActivity.this, "Authentication Successfull", Toast.LENGTH_SHORT).show();
                            //user_main_activity();
                            send_email_password();
                            sharedPreferences_check();
                        }
                        else {
                            progg.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user.
                            handleClickFieldsVisibility(true);
                            Toast.makeText(PatientsLoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public void sharedPreferences_check(){
        SharedPreferences sharedPreferences = getSharedPreferences(PatientsSplashScreenActivity.SHARED_PREFERENCES_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SHARED_EMAIL",EMAIL);
        editor.putString("SHARED_PASSWORD",PASSWORD);
        editor.putBoolean(PatientsSplashScreenActivity.SHARED_PREFERENCES_KAY_FLAG,true);
        editor.apply();

    }

    public void goto_Signup() {
        Intent intent = new Intent(getApplicationContext(), PatientsRegisterActivity.class);
        startActivity(intent);
        //finish();

    }

    //public void user_main_activity() {
        //Intent intent = new Intent(getApplicationContext(), Avaliable.class);
       // startActivity(intent);
       // finish();

   // }

    public void get_login_user_data() {
        EMAIL = user_email.getText().toString();
        PASSWORD = user_password.getText().toString();

    }

    public void check_email_password() {
        progg.setVisibility(View.VISIBLE);

        if (TextUtils.isEmpty(EMAIL)) {
            handleClickFieldsVisibility(true);
            progg.setVisibility(View.GONE);
            Toast.makeText(PatientsLoginActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(PASSWORD)) {
            progg.setVisibility(View.GONE);
            handleClickFieldsVisibility(true);
            Toast.makeText(PatientsLoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }else {
            vefify_user_login(EMAIL, PASSWORD);
        }
    }


    public void send_email_password(){
        Intent intent = new Intent(getApplicationContext(), MainPanel.class);
        intent.putExtra("email",EMAIL);
        intent.putExtra("password",PASSWORD);
        startActivity(intent);
        finish();
    }
}