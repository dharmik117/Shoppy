package com.example.shoppy;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import Model.Products;
import Model.Shops;
import Model.customer;
import Prevalent.Prevalent;
import ViewHolder.ShopViewHolder;
import kotlin.jvm.Throws;

public class Product_OC extends AppCompatActivity {

    private ImageView pdimage;
    private ElegantNumberButton numberbutton;
    private TextView productname,productprice,productdes;
    private DatabaseReference orders,orderproductref,uidref,userids;
    private TextView tryit;
    private Button addtocart;
    private String productid = "",state = "Normal";
    public static String shippingstate;
    private DatabaseReference profileuserref;
    private ConstraintLayout poc1;

    private FirebaseAuth firebaseauth;
    private String currentuserid;
    private String userid1;
    private FirebaseUser firebaseUser;
    private String shopkeeperid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__o_c);

        numberbutton = (ElegantNumberButton) findViewById(R.id.ocpdnumber);
        pdimage = (ImageView) findViewById(R.id.ocpdimage);
        productname = (TextView) findViewById(R.id.ocpdname);
        productdes = (TextView) findViewById(R.id.ocpddes);
        productprice = (TextView) findViewById(R.id.ocpdprice);
        addtocart = (Button) findViewById(R.id.ocpdaddtocartbtn);
        poc1 = findViewById(R.id.poc1);

        orders = FirebaseDatabase.getInstance().getReference().child("Orders");
        orderproductref = FirebaseDatabase.getInstance().getReference().child("Order Product");
        uidref = FirebaseDatabase.getInstance().getReference().child("User");
        userids = FirebaseDatabase.getInstance().getReference().child("Userids");

        firebaseauth = FirebaseAuth.getInstance();
        currentuserid = firebaseauth.getCurrentUser().getUid();
        profileuserref = FirebaseDatabase.getInstance().getReference().child("User").child(currentuserid);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        shopkeeperid = getIntent().getStringExtra("sid");

        uidref.child(currentuserid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    String userid = dataSnapshot.child("fullname").getValue().toString().trim();

                    userid1 = userid;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        productid = getIntent().getStringExtra("pid");
        getproductdetails(productid);

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(state.equals("Order Placed") || state.equals("Order Shipped"))
                {
                    Toast.makeText(Product_OC.this, "You Can Purchase More Product Once Order IS Shipped Or Confirmed", Toast.LENGTH_LONG).show();
                }
                else
                    {
                        addingtocartlist();

                    }

                final HashMap<String,Object> cartmap = new HashMap<>();

                cartmap.put("userid",currentuserid);

                userids.child(userid1).updateChildren(cartmap);

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        CheckOrderState();
    }

    private void addingtocartlist()
    {
        String savecurrentdateandtime;


        java.util.Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentdate = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm");
        savecurrentdateandtime = currentdate.format(calendar.getTime());

        final DatabaseReference cartlistref = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String,Object> cartmap = new HashMap<>();

        cartmap.put("pid",productid);
        cartmap.put("pname",productname.getText().toString());
        cartmap.put("price",productprice.getText().toString());
        cartmap.put("dateandtime",savecurrentdateandtime);
        cartmap.put("quentity",numberbutton.getNumber());
        cartmap.put("userid",currentuserid);
        cartmap.put("discount","");
        cartmap.put("shop",shopkeeperid);



        orderproductref
                .child(currentuserid)
                .updateChildren(cartmap);


            cartlistref.child("User View")
                    .child("Products")
                    .child(currentuserid)
                    .child(productid)
                    .updateChildren(cartmap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                cartlistref.child("Admin View")
                                        .child("Products")
                                        .child(currentuserid)
                                        .child(productid)
                                        .updateChildren(cartmap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task)
                                            {
                                                if(task.isSuccessful())
                                                {
                                                    Toast.makeText(Product_OC.this, "Added To Cart List", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(Product_OC.this,Homepage.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        });
                            }
                        }
                    });


    }

    private void getproductdetails(String productid)
    {
        DatabaseReference productsref = FirebaseDatabase.getInstance().getReference().child("Products List");

        productsref.child(shopkeeperid).child(productid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    Products products = dataSnapshot.getValue(Products.class);

                    productname.setText(products.getProductname());
                    productdes.setText(products.getDescryption());
                    productprice.setText(products.getPrice());

                    Picasso.get().load(products.getImage()).into(pdimage);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void CheckOrderState()
    {
        DatabaseReference orderref;
        orderref = FirebaseDatabase.getInstance().getReference().child("Orders").child("List").child(currentuserid);

        orderref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    shippingstate = dataSnapshot.child("state").getValue().toString();

                    if (shippingstate.equals("Shipped"))
                    {
                        state = "Order Shipped";
                    }
                    else if(shippingstate.equals("Not Shipped"))
                    {
                        state = "Order Placed";
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
