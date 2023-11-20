package com.example.appmai;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class Avaliable extends AppCompatActivity {
    int COUNTER=0;
    String EMAIL,PASSWORD,XX_EMAIL;
    String NOTIFICATION_STATUS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliable);
        get_email_password_intent();
//        bottomNavBar();

        new Thread(this::bottomNavBar).start();
//        statusNotificationService();
//        Notification(NOTIFICATION_STATUS);
//        new Thread(this::getNotificationStatus).start();
//        new Thread(this::repeat).start();
        new Thread(this::getNotificationStatus).start();
    }
    public void statusNotificationService(){
        Intent intent = new Intent(Avaliable.this, NotificationStatusService.class);
        intent.putExtra("EMAIL_NOTI",EMAIL);
        startService(intent);
//        startService(new Intent(Avaliable.this,NotificationService.class));
    }
//    public void repeat(){
//
//        final Handler handler = new Handler();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                COUNTER++;
//                Toast.makeText(Avaliable.this, ""+COUNTER, Toast.LENGTH_SHORT).show();
//                handler.postDelayed(this,1000);
//            }
//        };
//        handler.post(runnable);
//    }

    public void getNotificationStatus(){
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        CollectionReference NOTIFICATION_STATUS_CR = fb.collection("USERS").document(EMAIL).collection(EMAIL).document(EMAIL).collection("NOTIFICATION");
        NOTIFICATION_STATUS_CR.addSnapshotListener((value, error) -> {
            if (error != null) {
                // Handle error
                return;
            }
            // Update TextView with data from the collection
            assert value != null;
            for (DocumentChange dc : value.getDocumentChanges()) {
                NOTIFICATION_STATUS = dc.getDocument().getString("status");
                if (!NOTIFICATION_STATUS.equals("NULL"))
                 Notification(NOTIFICATION_STATUS);
            }
        });
    }

    public void delayed(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
//                Notification(NOTIFICATION_STATUS);
            Toast.makeText(getApplicationContext(), NOTIFICATION_STATUS, Toast.LENGTH_SHORT).show();
            }
        };
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable,2000);
    }

    public void Notification(String status){
        Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.doctor_img_splashsscreen,null);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(Avaliable.this)
                .setLargeIcon(bitmap)
                .setSmallIcon(R.drawable.doctor_img_splashsscreen)
                .setContentText(status)
                .setSubText("set sub text")
                .setChannelId("CHANNEL ID")
                .build();
        notificationManager.createNotificationChannel(new NotificationChannel("CHANNEL ID","CHANNEL NAME",NotificationManager.IMPORTANCE_HIGH));
        notificationManager.notify(69,notification);
    }


    public void bottomNavBar(){
        BottomNavigationView userPanelBottomViewNav;
        userPanelBottomViewNav = findViewById(R.id.userPanelBottomViewNav);

        userPanelBottomViewNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home){
                    fragmentLoader(new HomeFragment(),false);
                }else if (id == R.id.nav_list){
                    fragmentLoader(new ListFragment(),false);
                }else {
                    fragmentLoader(new ProfileFragment(),false);
                }
                return true;
            }
        });
        userPanelBottomViewNav.setSelectedItemId(R.id.nav_home);

    }

    public void fragmentLoader(Fragment fragment, boolean flag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("EMAILX",EMAIL);
        bundle.putString("XX_EMAIL",XX_EMAIL);
        bundle.putString("PASSWORDX",PASSWORD);
        fragment.setArguments(bundle);

        if (flag){
            ft.add(R.id.userPanelContainer,fragment);
        }else {
            ft.replace(R.id.userPanelContainer,fragment);
//            ft.addToBackStack("null");
        }
        ft.commit();
    }
    public void get_email_password_intent(){
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String Shared_email = intent.getStringExtra("EMAIL");
        String Shared_password = intent.getStringExtra("PASSWORD");

        if (Shared_email!=null){
            EMAIL = Shared_email;
        }else if(email==null) {
            EMAIL = intent.getStringExtra("USER_EMAIL");
        }
        else {
            EMAIL = email;
        }
        PASSWORD = intent.getStringExtra("password");
//        if (Shared_password==null){
//            PASSWORD = intent.getStringExtra("password");
//        }else {
//            PASSWORD = intent.getStringExtra("PASSWORD");
//        }

    }

}