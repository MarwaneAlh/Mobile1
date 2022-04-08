package com.example.miamapp;


import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


//Class qui s'occupe de l'interface inscription

public class RegisterActivity extends AppCompatActivity {
    EditText mFullName,mEmail,mPassword;
    Button mregisterbtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore ;
    String userID;



//Correspondance avec les elements xml du layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        mFullName = findViewById(R.id.fullName);
        mEmail= findViewById(R.id.email);
        mPassword =findViewById(R.id.password);
        mregisterbtn =findViewById(R.id.connexionBtn);
        mLoginBtn =findViewById(R.id.connexiontext);
        fAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();



        //Gestion de l'action lorsque l'utilisateur clique sur le bouton inscirption
        //Gestion egalement des comportements en cas d'erreurs
        mregisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String fullName = mFullName.getText().toString().trim();
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

                        userID=fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference=fStore.collection("users").document(userID);
                            Map<String,Object> user= new HashMap<>();
                            user.put("fullName",fullName);
                            user.put("email",email);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG,"Data stored !");

                                }
                            });


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