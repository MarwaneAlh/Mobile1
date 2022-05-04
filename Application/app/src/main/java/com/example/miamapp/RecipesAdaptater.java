package com.example.miamapp;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mview = itemView;

            nameview = (TextView) mview.findViewById(R.id.nameviews);
            healthscore = (TextView) mview.findViewById(R.id.healthscore);
            imageView = (ImageView) mview.findViewById(R.id.imageRecipes);
            addtocart = (Button) mview.findViewById(R.id.addtocartbutton);
            summary = mview.findViewById(R.id.summary);
            time = mview.findViewById(R.id.time);
            summary.setMovementMethod(new ScrollingMovementMethod());


        }

    }
}