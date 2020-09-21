package com.example.shoppy;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import Model.Products;
import Prevalent.Prevalent;
import ViewHolder.OnClick_ViewHolder;

public class ShopName_Onclick extends AppCompatActivity {

    private DatabaseReference productref;
    private RecyclerView recyclerView;
    RecyclerView .LayoutManager layoutManager;
    private String shopkeeperid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_name__onclick);

        productref = FirebaseDatabase.getInstance().getReference().child("Products List");
        recyclerView = findViewById(R.id.ocrecycleview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        shopkeeperid = getIntent().getStringExtra("shopkeeperid");

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Products>options = new
                FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(productref.child(shopkeeperid),Products.class)
                .build();

        FirebaseRecyclerAdapter<Products, OnClick_ViewHolder>adapter =
                new FirebaseRecyclerAdapter<Products, OnClick_ViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull OnClick_ViewHolder onClick_viewHolder, int i, @NonNull final Products products)
                    {
                        onClick_viewHolder.txtproductname.setText(products.getProductname());
                        onClick_viewHolder.txtproductdes.setText(products.getDescryption());
                        onClick_viewHolder.txtproductprice.setText(products.getPrice());

                        Picasso.get().load(products.getImage()).into(onClick_viewHolder.pdimageview);


                        onClick_viewHolder.itemView.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent = new Intent(ShopName_Onclick.this,Product_OC.class);
                                intent.putExtra("pid",products.getPid());
                                intent.putExtra("sid",products.getShopkeeper_id());
                                startActivity(intent);


                            }
                        });
                    }

                    @NonNull
                    @Override
                    public OnClick_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onclick_layout,parent,false);
                        OnClick_ViewHolder holder = new OnClick_ViewHolder(view);
                        return holder;
                    }
                };

            recyclerView.setAdapter(adapter);
            adapter.startListening();
    }
}
