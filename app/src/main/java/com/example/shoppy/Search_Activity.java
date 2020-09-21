package com.example.shoppy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import Model.Shops;
import ViewHolder.ShopViewHolder;

public class Search_Activity extends AppCompatActivity {

    private EditText etsearch;
    private ImageView searchbutton;
    private RecyclerView searchlist;
    private String Searchinput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_);

        etsearch = findViewById(R.id.saedittext);
        searchbutton = findViewById(R.id.assearchbutton);
        searchlist = findViewById(R.id.asrecycle);
        searchlist.setLayoutManager(new LinearLayoutManager(Search_Activity.this));


        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Searchinput = etsearch.getText().toString();
                onStart();
            }
        });

        searchbutton.isSelected();

    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference refrence = FirebaseDatabase.getInstance().getReference().child("Add_Shop_From_ShopKeeper");

        FirebaseRecyclerOptions<Shops> options = new
                FirebaseRecyclerOptions.Builder<Shops>()
                .setQuery(refrence.orderByChild("shopname").startAt(Searchinput),Shops.class).build();


        FirebaseRecyclerAdapter<Shops, ShopViewHolder> adapter = new
                FirebaseRecyclerAdapter<Shops, ShopViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ShopViewHolder shopViewHolder, int i, @NonNull Shops shops)
                    {
                        shopViewHolder.txtshopname.setText(shops.getShopname());
                        shopViewHolder.txtshopdes.setText(shops.getDescryption());

                        Picasso.get().load(shops.getImage()).into(shopViewHolder.imageview);


                        Glide.with(Search_Activity.this)
                                .load(shops.getImage())
                                .into(shopViewHolder.imageview);

                        shopViewHolder.itemView.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent = new Intent(Search_Activity.this,ShopName_Onclick.class);
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_design,parent,false);
                        ShopViewHolder holder = new ShopViewHolder(view );
                        return holder;
                    }
                };

        searchlist.setAdapter(adapter);
        adapter.startListening();
    }


}