package com.example.miamapp;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RecipesAdaptater extends RecyclerView.Adapter<RecipesAdaptater.ViewHolder> {
    public List<RecipesData> list;

    public RecipesAdaptater(List<RecipesData> list){
        this.list=list;
    }


    @NonNull
    @Override
    public RecipesAdaptater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewrecipes, parent, false);
        return new RecipesAdaptater.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdaptater.ViewHolder holder, int position) {
        holder.nameview.setText(list.get(position).getTitle());
        holder.healthscore.setText(list.get(position).getHealthScore());
        holder.summary.setText(list.get(position).getInstruction());
        holder.time.setText(list.get(position).getTime());
        Picasso.get().load(list.get(position).getImage()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mview;
        public TextView nameview;
        public TextView healthscore;
        public TextView summary;
        public ImageView imageView;
        public Button addtocart;
        public TextView time;
        FirebaseAuth fAuth;
        FirebaseFirestore fStore ;
        String userID;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mview = itemView;

            nameview = (TextView) mview.findViewById(R.id.nameviews);
            healthscore = (TextView) mview.findViewById(R.id.healthscore);
            imageView = (ImageView) mview.findViewById(R.id.imageRecipes);
            addtocart = (Button) mview.findViewById(R.id.addtocartbuttonrecipes);
            summary = mview.findViewById(R.id.summary);
            time = mview.findViewById(R.id.time);
            summary.setMovementMethod(new ScrollingMovementMethod());
            fAuth = FirebaseAuth.getInstance();
            fStore= FirebaseFirestore.getInstance();
            addCartRecipesItem(addtocart);


        }

        public void addCartRecipesItem(Button btn ){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Random rand = new Random();
                    int int_random = rand.nextInt(100);
                    for(int i =0;i<list.size();i++){
                        if(list.get(i).getTitle().equals(nameview.getText())){
                        for(int j=0;j<list.get(i).getIngredientsrecipes().size();j++) {
                            Log.d("beta : ",list.get(i).getIngredientsrecipes().get(j).getName());

                        }
                        }

                    }
                    userID=fAuth.getCurrentUser().getUid();
                    String namedocument="test"+int_random+" item";
                    String getprice = String.valueOf(int_random);
                    String getdevice="$";
                    String onlyprice=getprice;
                    String quantity="1";
                    DocumentReference documentReference=fStore.collection("cart").document(namedocument);
                    Map<String, Object> docData = new HashMap<>();
                    docData.put("name", namedocument);
                    docData.put("price",onlyprice);
                    //docData.put("photo", imageView.getText().toString());
                    docData.put("device", getdevice);
                    docData.put("quantity",quantity);
                    documentReference.set(docData);

                    Toast.makeText(itemView.getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}