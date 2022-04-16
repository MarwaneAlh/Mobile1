package com.example.miamapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.json.JSONException;
import org.json.JSONObject;

public class RecipesActivity extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore ;
    TextView nameuser;
    String url ="https://api.spoonacular.com/recipes/716429" +
            "/information?includeNutrition=false?&apiKey=e778f2da2efe4c31a2c0151e0ac2e79e";
    TextView recipesdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_recipes);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayoutRecipes);
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
                        startActivity(new Intent(getApplicationContext(),RecipesActivity.class));
                        return true;
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

        findViewById(R.id.imageshop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }
        });
        recipesdata=findViewById(R.id.recipesdata);
        RequestQueue queue= Volley.newRequestQueue(RecipesActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    recipesdata.setText(response.getString("title"));



                } catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RecipesActivity.this,"ERROR TO LOAD JSON FILE",
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);

    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        startActivity((new Intent(getApplicationContext(),LoginActivity.class)));
        finish();
    }

}