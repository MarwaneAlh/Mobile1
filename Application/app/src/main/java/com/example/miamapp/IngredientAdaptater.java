package com.example.miamapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class IngredientAdaptater extends RecyclerView.Adapter<IngredientAdaptater.ViewHolder> {
    public List<IngredientData> list;

    public IngredientAdaptater(List<IngredientData> list){
        this.list=list;
    }

    public void setList(List<IngredientData> filterlist){
        this.list=filterlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.viewitems,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameText.setText(list.get(position).getName());
        holder.priceText.setText("Price : " +list.get(position).getPrice()+" "+list.get(position).getDevice());
        Picasso.get().load(list.get(position).getPhoto()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

    View mview;
    public TextView nameText;
    public TextView priceText;
    public ImageView imageView;
        public TextView quantity;
        Button decreab,increaseb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mview=itemView;
            nameText=(TextView) mview.findViewById(R.id.nameview);
            priceText=(TextView) mview.findViewById(R.id.priceview);
            imageView=(ImageView) mview.findViewById(R.id.imageIngredients);
            decreab=(Button) mview.findViewById(R.id.decreasButton);
            increaseb=(Button) mview.findViewById(R.id.increasButton);
            quantity=mview.findViewById(R.id.quantity);
            operationfunction(decreab);
            operationfunction(increaseb);
        }

        public void  operationfunction(Button btn){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int numberinit = Integer.parseInt(quantity.getText().toString());
                    if(btn.getText().equals("-")){
                        if(!quantity.getText().toString().equals("0")) {
                            numberinit--;
                            quantity.setText(String.valueOf(numberinit));

                            notifyDataSetChanged();
                        }
                    }else{
                        numberinit++;
                        quantity.setText(String.valueOf(numberinit));
                        notifyDataSetChanged();
                    }
                }
            });

        }

    }


}
