package com.example.miamapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.miamapp.R;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//Classe representant la page d'acceuil suite a la connexion
//Ici est executé une requete vers l'api spooncular

public class MainActivity extends AppCompatActivity {
    String url ="https://api.spoonacular.com/recipes/716429" +
            "/information?includeNutrition=false?&apiKey=e778f2da2efe4c31a2c0151e0ac2e79e";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView t = findViewById(R.id.textView4);



        RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    t.setText(response.getString("title"));



                } catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"ERROR TO LOAD JSON FILE",
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);


    }
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity((new Intent(getApplicationContext(),LoginActivity.class)));
        finish();
    }

}