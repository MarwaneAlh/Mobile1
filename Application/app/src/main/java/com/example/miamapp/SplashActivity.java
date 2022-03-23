package com.example.miamapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.miamapp.R;

public class SplashActivity extends AppCompatActivity {

    //Cette class represente L'acticité splash qui s'affiche un court instant au lancement
    // de l'application

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //Creation d'un thread qui va se lancer 3 milli secondes
        //Gestions des erreurs ici gérer
        //Apelle de la fonction startActivity afin de gerer la transition entre les deux activités
        //Lancement du thread
        Thread thread= new Thread(){
            @Override
            public void run() {
                try{
                    sleep(3000);

                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                }
            }
        };
        thread.start();
    }
}