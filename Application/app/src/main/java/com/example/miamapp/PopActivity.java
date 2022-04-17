package com.example.miamapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

public class PopActivity extends AppCompatActivity {

    TextView returnhome;
    TextView nameuser;
    private static int TIME_OUT = 5000;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);
        getSupportActionBar().hide();
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width-100),(int)(height*.5));
        Bundle extras = getIntent().getExtras();
        nameuser=findViewById(R.id.nameText);
        nameuser.setText("Hi "+extras.getString("NAME_USERS")+",");
        returnhome=findViewById(R.id.returnhome);
        returnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My notification", "My notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(PopActivity.this,"My notification");
        mBuilder.setContentTitle("MiamApp Notification");
        mBuilder.setContentText("Your order is being delivered!");
        mBuilder.setSmallIcon(R.drawable.ic_baseline_fastfood);
        mBuilder.setAutoCancel(true);



        NotificationManagerCompat mNotificationManage=NotificationManagerCompat.from(PopActivity.this);
        mNotificationManage.notify(1,mBuilder.build());


    }

}