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

//Class qui s'occupe de l'interface inscription

public class RegisterActivity extends AppCompatActivity {
    EditText mFullName,mEmail,mPassword;
    Button mregisterbtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;

//Correspondance avec les elements xml du layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFullName = findViewById(R.id.fullName);
        mEmail= findViewById(R.id.email);
        mPassword =findViewById(R.id.password);
        mregisterbtn =findViewById(R.id.connexionBtn);
        mLoginBtn =findViewById(R.id.connexiontext);
        fAuth = FirebaseAuth.getInstance();


        //Gestion de l'action lorsque l'utilisateur clique sur le bouton inscirption
        //Gestion egalement des comportements en cas d'erreurs
        mregisterbtn.setOnClickListener(new View.OnClickListener() {
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

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Compte créer !",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(RegisterActivity.this, "Erreur !"
                                    +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }
}