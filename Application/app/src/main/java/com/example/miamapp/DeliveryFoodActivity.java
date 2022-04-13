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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DeliveryFoodActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FoodAdaptater foodadaptater;
    ArrayList<FoodData> list;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore ;
    private List<FoodData> listoffood;
    private SearchView searchview;
    TextView nameuser;
    Button all,pizza,hamburger,sushi;
    TextView filtercategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_food);
        getSupportActionBar().hide();
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayoutDelivery);
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

        listoffood=new ArrayList<>();
        foodadaptater=new FoodAdaptater(listoffood);
        recyclerView=findViewById(R.id.listOfFood);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(foodadaptater);

        searchview=findViewById(R.id.searchviews);
        searchview.clearFocus();


        fStore.collection("deliveryfoods").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                    FoodData foods=doc.getDocument().toObject(FoodData.class);
                    listoffood.add(foods);
                    foodadaptater.notifyDataSetChanged();
                }
            }
        });

        pizza=findViewById(R.id.pizzaButton2);
        hamburger=findViewById(R.id.burgerButton);
        all=findViewById(R.id.allButton);
        sushi=findViewById(R.id.sushiButton);
        filtercategory=findViewById(R.id.filterText);
        openButton(pizza,"Pizza");
        openButton(hamburger,"Burger");
        openButton(all,"");
        openButton(sushi,"Sushi");

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
        List<FoodData> filterList=new ArrayList<>();
        for(FoodData item:listoffood){
            if(item.getName().toLowerCase().contains(text.toLowerCase())){
                filterList.add(item);
            }
        }
        if(filterList.isEmpty()){
            Toast.makeText(this, "No Data Founded", Toast.LENGTH_SHORT).show();
        }else{
            foodadaptater.setList(filterList);
        }


    }
    private void openButton(Button btn,String request){

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<FoodData> filterList=new ArrayList<>();
                for(FoodData item:listoffood){
                    if(item.getCategory().toLowerCase().contains(request.toLowerCase())){
                        filterList.add(item);
                    }
                }
                if(filterList.isEmpty()){
                    Toast.makeText(DeliveryFoodActivity.this, "NO DATA FOUND", Toast.LENGTH_SHORT).show();
                }else{
                    foodadaptater.setList(filterList);

                    if(request.equals("")){
                        filtercategory.setText("FILTER : NONE");
                    }else{
                        filtercategory.setText("FILTER : "+request);
                    }
                }
            }
        });
    }


}