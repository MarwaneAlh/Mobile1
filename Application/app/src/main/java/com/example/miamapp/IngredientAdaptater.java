package com.example.miamapp;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        holder.priceText.setText(list.get(position).getPrice()+" "+list.get(position).getDevice());
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
        Button decreab,increaseb,addCart;
        FirebaseAuth fAuth;
        FirebaseFirestore fStore ;
        String userID;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mview=itemView;
            fAuth = FirebaseAuth.getInstance();
            fStore= FirebaseFirestore.getInstance();
            nameText=(TextView) mview.findViewById(R.id.nameview);
            priceText=(TextView) mview.findViewById(R.id.priceview);
            imageView=(ImageView) mview.findViewById(R.id.imageIngredients);
            decreab=(Button) mview.findViewById(R.id.decreasButton);
            increaseb=(Button) mview.findViewById(R.id.increasButton);
            quantity=mview.findViewById(R.id.quantity);
            operationfunction(decreab);
            operationfunction(increaseb);
            addCart=mview.findViewById(R.id.buyButton);
            addCartButto(addCart);

            fAuth = FirebaseAuth.getInstance();
            fStore=FirebaseFirestore.getInstance();



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

        public void addCartButto(Button btn){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("demo","test");
                    userID=fAuth.getCurrentUser().getUid();
                    String namedocument=nameText.getText()+" item";
                    String getprice = (String)priceText.getText();
                    String getdevice=getprice.substring(getprice.length()-1);
                    String onlyprice=getprice.substring(0,getprice.length()-2);
                    DocumentReference documentReference=fStore.collection("cart").document(namedocument);
                    Map<String, Object> docData = new HashMap<>();
                    docData.put("name", nameText.getText().toString());
                    docData.put("price",onlyprice);
                    //docData.put("photo", imageView.getText().toString());
                    docData.put("device", getdevice);
                    docData.put("quantity", quantity.getText().toString());
                    documentReference.set(docData);
                    Toast.makeText(itemView.getContext(), "Added to cart", Toast.LENGTH_SHORT).show();





                }
            });
        }

    }


}
