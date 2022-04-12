package com.example.miamapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class IngredientActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    IngredientAdaptater ingredientadaptater;
    ArrayList<IngredientData> list;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore ;
    private List<IngredientData> listofingredient;
    private SearchView searchview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_ingredient);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayoutIngredient);
        NavigationView nav=(NavigationView) drawerLayout.findViewById(R.id.navigationView);
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
                    case R.id.menuRecipes:
                    case R.id.menuDeliveryFood:
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



        listofingredient=new ArrayList<>();
        ingredientadaptater=new IngredientAdaptater(listofingredient);
        recyclerView=findViewById(R.id.listOfIngredients);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(ingredientadaptater);

        searchview=findViewById(R.id.searchviews);
        searchview.clearFocus();



        fAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        fStore.collection("ingredients").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Log.d(TAG,"EROOR here there is impossible"+ error.getMessage());
                }
                for(DocumentChange doc:value.getDocumentChanges()){
                    /*if(doc.getType() == DocumentChange.Type.ADDED){
                        String name = doc.getDocument().getString("name");
                        Log.d(TAG,"Name "+""+name);


                    }*/
                    IngredientData ingredients=doc.getDocument().toObject(IngredientData.class);
                    listofingredient.add(ingredients);
                    ingredientadaptater.notifyDataSetChanged();
                }
            }
        });

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });



    }



    public void logout(){
        FirebaseAuth.getInstance().signOut();
        startActivity((new Intent(getApplicationContext(),LoginActivity.class)));
        finish();
    }

    private void filterList(String text){
        List<IngredientData> filterList=new ArrayList<>();
        for(IngredientData item:listofingredient){
            if(item.getName().toLowerCase().contains(text.toLowerCase())){
                filterList.add(item);
            }
        }
        if(filterList.isEmpty()){
            Toast.makeText(this, "No Data Founded", Toast.LENGTH_SHORT).show();
        }else{
        ingredientadaptater.setList(filterList);
        }


    }



    }

