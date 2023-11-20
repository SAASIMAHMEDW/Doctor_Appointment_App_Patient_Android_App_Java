package com.example.appmai;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class NotificationStatusService extends Service {
    String EMAIL;
    String NOTIFICATION_STATUS="NAHI";
    int COUNTER=0;
    boolean STOP_LOOP_FLAG=true;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
//        getNotificationStatus();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        EMAIL =  intent.getStringExtra("EMAIL_NOTI");
        while (COUNTER<1){
            COUNTER++;
//        getNotificationStatus();
            getNotificationStatus_delayed();
//            if (!NOTIFICATION_STATUS.equals("NAHI")){
//                Notification(EMAIL);
//            }
        }
        return START_STICKY;
//        return super.onStartCommand(intent, flags, startId);
    }
    public void x(){
        if (NOTIFICATION_STATUS.equals("pending"))
            Notification("pending");
        else if (NOTIFICATION_STATUS.equals("rejected"))
            Notification("rejected");
        else if (NOTIFICATION_STATUS.equals("accepted"))
            Notification("accepted");
        else if (NOTIFICATION_STATUS.equals("finished")){
            STOP_LOOP_FLAG=false;
            Notification("finished");
        }

        else
            STOP_LOOP_FLAG=false;
    }

    public void x_delayed(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                x();
            }
        };
        Handler handler = new Handler(Looper.myLooper());
        handler.postDelayed(runnable,3000);
    }

    public void getNotificationStatus_delayed(){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    getNotificationStatus();
                }
            };
            Handler handler = new Handler(Looper.myLooper());
            handler.postDelayed(runnable,1000);
    }
    public void Notification(String status){
        Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.doctor_img_splashsscreen,null);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        assert bitmapDrawable != null;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(this)
                .setLargeIcon(bitmap)
                .setSmallIcon(R.drawable.doctor_img_splashsscreen)
                .setContentText(status)
                .setSubText("set sub text")
                .setChannelId("CHANNEL ID")
                .build();
        notificationManager.createNotificationChannel(new NotificationChannel("CHANNEL ID","CHANNEL NAME",NotificationManager.IMPORTANCE_HIGH));
        notificationManager.notify(69,notification);
    }

    public void getNotificationStatus(){
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        DocumentReference NOTIFICATION_STATUS_DR = fb.collection("USERS").document(EMAIL).collection(EMAIL).document("NOTIFICATION");
        NOTIFICATION_STATUS_DR.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                NOTIFICATION_STATUS = documentSnapshot.get("status").toString();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
