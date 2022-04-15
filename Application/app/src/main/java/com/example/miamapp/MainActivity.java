package com.example.miamapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.util.ArrayList;

//Classe representant la page d'acceuil suite a la connexion
//Ici est executé une requete vers l'api spooncular

public class MainActivity extends AppCompatActivity {
    String url ="https://api.spoonacular.com/recipes/716429" +
            "/information?includeNutrition=false?&apiKey=e778f2da2efe4c31a2c0151e0ac2e79e";
    FirebaseAuth fAuth;
    FirebaseFirestore fStore ;
    TextView nameuser;
    Button open_ingredient_windows;
    Button open_delivery_windows;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView nav=(NavigationView) drawerLayout.findViewById(R.id.navigationView);
        View headerView=nav.getHeaderView(0);
        nameuser=(TextView) headerView.findViewById(R.id.usernames);

        fAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        String userID;
        userID=fAuth.getCurrentUser().getUid();



        DocumentReference documentReference=fStore.collection("users").document(userID);

       documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
           @Override
           public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
              nameuser.setText(value.getString("fullName"));
           }
       });



        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        findViewById(R.id.imageshop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }
        });

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        return true;
                    case R.id.ShopCart:
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        return true;
                    case R.id.menuRecipes:
                    case R.id.menuDeliveryFood:
                        startActivity(new Intent(getApplicationContext(),DeliveryFoodActivity.class));
                        return true;
                    case R.id.Ingredient:
                        startActivity(new Intent(getApplicationContext(), IngredientActivity.class));
                        return true;
                    case R.id.Signout:
                        logout();
                        return true;
                }
                return false;
            }
        });

        open_ingredient_windows=findViewById(R.id.ingredientButton);
        open_ingredient_windows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), IngredientActivity.class));
            }
        });
        open_delivery_windows=findViewById(R.id.deliveryFoodButton);
        open_delivery_windows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DeliveryFoodActivity.class));
            }
        });




       /* RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
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

*/
    }
    public void logout(){
        FirebaseAuth.getInstance().signOut();
        startActivity((new Intent(getApplicationContext(),LoginActivity.class)));
        finish();
    }

    public static void todeletecart(){
        FirebaseAuth fAuth;
        FirebaseFirestore fStore ;
        fAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();


        fStore.collection("cart").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Log.d(TAG,"EROOR here there is impossible"+ error.getMessage());
                }
                for(DocumentSnapshot doc:value.getDocuments()){
                        doc.get("name");
                        String name = doc.get("name").toString();
                        String test = doc.getId();
                        if(!name.equals("null")){

                            Log.d(TAG,"Name to delete "+""+test);
                            fStore.collection("cart").document(test).delete();

                        }else {
                            Log.d(TAG, "Name " + "" + name);

                        }
                    }

                }
            });
        


    }




}