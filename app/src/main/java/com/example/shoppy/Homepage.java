package com.example.shoppy;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

import Model.Sessions;
import Model.Shops;
import Model.customer;
import ViewHolder.ShopViewHolder;

public class Homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener

{
    private DrawerLayout drawer;
    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    FirebaseUser firebaseUser;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference shopsref,profileuserref,uidref;
    private FloatingActionButton cartfb;
    private String currentuserid,shopid1,ut;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        firebaseAuth = FirebaseAuth.getInstance();
        shopsref = FirebaseDatabase.getInstance().getReference().child("Shop_List");

        recyclerView = findViewById(R.id.shoplistrecycleview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        cartfb = findViewById(R.id.fbcart);

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));


        }


       final FirebaseUser user = firebaseAuth.getCurrentUser();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        if (toolbar != null)
        {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        drawer = findViewById(R.id.drawerlayout);
        NavigationView navigationView = findViewById(R.id.navigationview1);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

                drawer.addDrawerListener(toggle);
                toggle.syncState();


    }


    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Shops> options =
                new FirebaseRecyclerOptions.Builder<Shops>()
                        .setQuery(shopsref,Shops.class)
                        .build();

        FirebaseRecyclerAdapter<Shops, ShopViewHolder> adapter =
                new FirebaseRecyclerAdapter<Shops, ShopViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ShopViewHolder shopViewHolder, final int i, @NonNull final Shops shops)
                    {
                        String permission = shops.getPermission();
                        if(permission.equals("True"))

                        {

                        shopViewHolder.txtshopname.setText(shops.getShopname());
                        shopViewHolder.txtshopdes.setText(shops.getDescryption());


                        Picasso.get().load(shops.getImage()).into(shopViewHolder.imageview);


                            Glide.with(Homepage.this)
                                    .load(shops.getImage())
                                    .into(shopViewHolder.imageview);
                        }

                        else
                            {
                                shopViewHolder.cardviewhomepage.setVisibility(View.GONE);
                            }

                        shopViewHolder.itemView.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent = new Intent(Homepage.this,ShopName_Onclick.class);
                                intent.putExtra("shopkeeperid",shops.getShopkeeperid());
                                startActivity(intent);
                            }
                        });

                        cartfb.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent = new Intent(Homepage.this,Cart_Activity.class);
                                intent.putExtra("shopid",shops.getShopid());
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

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        switch (menuItem.getItemId()){
            case R.id.profilePage:
                Intent i=new Intent(Homepage.this,ProfilePage.class);
                startActivity(i);
                break;

            case R.id.orderpae:
                Intent j =new Intent(Homepage.this,Orderpae.class);
                startActivity(j);
                break;

            case R.id.notificationpage:
                Intent k=new Intent(Homepage.this,Notificationpage.class);
                startActivity(k);
                break;

            case R.id.settingpage:
                Intent l =new Intent(Homepage.this,Setting.class);
                startActivity(l);
                break;


            case R.id.cartpage:
                Intent m =new Intent(Homepage.this,Cart_Activity.class);
                startActivity(m);
                break;


                default:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmntcontainer,
                            new ProfileFragment()).commit();

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (this.drawer.isDrawerOpen(GravityCompat.START)) {
            this.drawer.closeDrawer(GravityCompat.START);
        } else
            {
            super.onBackPressed();
            }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.account:
                Intent k=new Intent(Homepage.this,ProfilePage.class);
                startActivity(k);
                break;

            case R.id.logout:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this,MainActivity.class));

            case R.id.search:
                Intent intent = new Intent(this,Search_Activity.class);
                startActivity(intent);

        }

        return super.onOptionsItemSelected(item);

    }
}