package com.example.miamapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodAdaptater extends RecyclerView.Adapter<FoodAdaptater.ViewHolder> {
    public List<FoodData> list;

    public FoodAdaptater(List<FoodData> listoffood) {
        this.list=listoffood;
    }

    public void setList(List<FoodData> filterlist){
        this.list=filterlist;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public FoodAdaptater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewitems,parent,false);
        return new FoodAdaptater.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdaptater.ViewHolder holder, int position) {
        holder.nameText.setText(list.get(position).getName());
        holder.priceText.setText("Price : " +list.get(position).getPrice()+" "+list.get(position).getDevice());
        holder.resto.setText("By "+list.get(position).getResto());
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
        public TextView resto;
        public TextView quantity;
        Button decreab,increaseb;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mview=itemView;
            nameText=(TextView) mview.findViewById(R.id.nameview);
            priceText=(TextView) mview.findViewById(R.id.priceview);
            imageView=(ImageView) mview.findViewById(R.id.imageIngredients);
            resto=(TextView) mview.findViewById(R.id.restoname);
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
