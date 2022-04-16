package com.example.miamapp;

import android.app.Application;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

public class CartAdaptater extends RecyclerView.Adapter<CartAdaptater.ViewHolder> {

    public List<CartData> list;

    public CartAdaptater(List<CartData> listofitem) {

        this.list=listofitem;
    }




    @NonNull
    @Override
    public CartAdaptater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewcartitem,parent,false);
        return new CartAdaptater.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdaptater.ViewHolder holder, int position) {
        holder.nameText.setText(list.get(position).getName());
        double pricetoround=Double.parseDouble(list.get(position).getPrice())*Double.parseDouble(list.get(position).getQuantity());
        double roundtwodigit=Math.round(pricetoround*100.0)/100.0;

        holder.priceText.setText(roundtwodigit+" "+list.get(position).getDevice());
        holder.quantity.setText("X"+list.get(position).getQuantity());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View mview;
        public TextView nameText;
        public TextView priceText;
        public TextView resto;
        public TextView quantity;
        Button decreab,increaseb,deletebtn;
        FirebaseAuth fAuth;
        FirebaseFirestore fStore ;
        TextView pricett;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mview=itemView;
            nameText=(TextView) mview.findViewById(R.id.itemname);
            priceText=(TextView) mview.findViewById(R.id.priceview);
            resto=(TextView) mview.findViewById(R.id.restoname);
            decreab=(Button) mview.findViewById(R.id.decreasButton);
            increaseb=(Button) mview.findViewById(R.id.increasButton);
            quantity=mview.findViewById(R.id.quantity);
            fAuth = FirebaseAuth.getInstance();
            fStore= FirebaseFirestore.getInstance();
            deletebtn=mview.findViewById(R.id.deletebtn);
            deleteitem(deletebtn);





        }

        private void deleteitem(Button btn){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d("TODELETE",nameText.getText().toString());
                    fStore.collection("cart").document(nameText.getText().toString()+" item").delete();
                    list.clear();
                    notifyDataSetChanged();

                }
            });
        }

    }





}
