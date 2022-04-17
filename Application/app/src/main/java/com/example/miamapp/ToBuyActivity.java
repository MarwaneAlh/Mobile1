package com.example.miamapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ToBuyActivity extends AppCompatActivity {

    TextView totalprice;
    Button cancelButton,validate;
    String nameuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_to_buy);
        Bundle extras = getIntent().getExtras();
        totalprice=findViewById(R.id.totalp);
        totalprice.setText(extras.getString("PRICE_VALUE"));
        nameuser=extras.getString("NAME_USER");
        cancelButton=findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }
        });

        validate=findViewById(R.id.payButtons);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getApplicationContext(),PopActivity.class);
                i.putExtra("NAME_USERS",nameuser);
                startActivity(i);

            }
        });


    }
}