package com.example.miamapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mloginbtn;
    TextView mregisterBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mEmail= findViewById(R.id.email);
        mPassword =findViewById(R.id.password);
        mloginbtn =findViewById(R.id.connexionBtn);
        mregisterBtn =findViewById(R.id.inscriptionText);
        fAuth = FirebaseAuth.getInstance();

        mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Veuillez entrer un email");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Veuillez entrer un mot de passe");
                    return;
                }

                if(password.length()<6){
                    mPassword.setError("Veuillez entrer un mot de passe de minimum 6 caracteres");
                }

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Connexion réussie !"
                                    , Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(LoginActivity.this, "Erreur connexion échoué" +
                                    " !" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });




            }
        });

        mregisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

    /*if(fAuth.getCurrentUser() !=null){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();*/
    }
    }
