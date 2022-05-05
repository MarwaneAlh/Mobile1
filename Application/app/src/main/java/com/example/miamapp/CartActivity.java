package com.example.miamapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class CartActivity extends AppCompatActivity {

    TextView nameuser;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore ;
    RecyclerView recyclerView;
    private List<CartData> listofitem;
    CartAdaptater cartadaptater;
    TextView allprice;
    double pricett;
    Button payButton;
    Button clearAllbtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_cart);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayoutCart);
        NavigationView nav=(NavigationView) drawerLayout.findViewById(R.id.navigationView);
        View headerView=nav.getHeaderView(0);
        nameuser=(TextView) headerView.findViewById(R.id.usernames);
        fAuth = FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
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

        listofitem=new ArrayList<>();
        pricett=0;
        cartadaptater=new CartAdaptater(listofitem);
        recyclerView=findViewById(R.id.listofitem);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartadaptater);
        allprice=findViewById(R.id.totalPrice);



        fStore.collection("cart").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Log.d(TAG,"EROOR here there is impossible"+ error.getMessage());
                }


                pricett=0;
                for(DocumentSnapshot doc:value.getDocuments()){
                    String name = doc.get("name").toString();


                    if(!name.equals("null")){
                        CartData cartitems=doc.toObject(CartData.class);

                        listofitem.add(cartitems);


                        pricett+=Double.parseDouble(doc.get("quantity").toString())*Double.parseDouble(doc.get("price").toString());
                        double roundprice=Math.round(pricett*100.0)/100.0;
                        allprice.setText("TOTAL PRICE: "+ String.valueOf(roundprice)+" "+doc.get("device"));
                        cartadaptater.notifyDataSetChanged();



                    }

                }
            }
        });
        payButton=findViewById(R.id.payButton);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent o = new Intent(CartActivity.this,ToBuyActivity.class);
                o.putExtra("PRICE_VALUE",allprice.getText().toString());
                o.putExtra("NAME_USER",nameuser.getText().toString());
                startActivity(o);
            }
        });




    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        startActivity((new Intent(getApplicationContext(),LoginActivity.class)));
        finish();
    }

}