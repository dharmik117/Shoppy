package com.example.shoppy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import Model.Cart;
import Model.Products;
import Model.Shops;
import ViewHolder.Cart_ViewHolder;

public class Cart_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button nextprocessbutton;
    private TextView txttotalamount,txtmasg1;

    private int overalltotalprice = 0;

    private FirebaseAuth firebaseauth;
    private String currentuserid,o;
    private FirebaseUser firebaseUser;
    private DatabaseReference profileuserref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_);

        recyclerView = findViewById(R.id.rvcartlist);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        nextprocessbutton = (Button) findViewById(R.id.nextprocessbtn);
        txttotalamount = (TextView) findViewById(R.id.tvtotalprice);
        txtmasg1 = (TextView) findViewById(R.id.msg1);

        firebaseauth = FirebaseAuth.getInstance();
        currentuserid = firebaseauth.getCurrentUser().getUid();
        profileuserref = FirebaseDatabase.getInstance().getReference().child("User").child(currentuserid);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        o = getIntent().getStringExtra("shopid");

        Toast.makeText(this, "j" + o, Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onStart() {
        super.onStart();

        CheckOrderState();

        txttotalamount.setText( String.valueOf(overalltotalprice));

        final DatabaseReference cartlistref = FirebaseDatabase.getInstance().getReference().child("Cart List");

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartlistref
                                .child("User View")
                                .child("Products")
                                .child(currentuserid),Cart.class).build();

        FirebaseRecyclerAdapter<Cart, Cart_ViewHolder> adapter
                = new FirebaseRecyclerAdapter<Cart, Cart_ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Cart_ViewHolder cart_viewHolder, final int i, @NonNull final Cart cart)
            {
                cart_viewHolder.txtproductque.setText("Quantity = "+cart.getQuentity());
                cart_viewHolder.txtproductname.setText(cart.getPname() );
                cart_viewHolder.txtproductprice.setText(cart.getPrice());

                nextprocessbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {

                        Intent intent = new Intent(Cart_Activity.this,Confirm_Order.class);
                        intent.putExtra("Total Price",String.valueOf(overalltotalprice));
                        intent.putExtra("shopidref",cart.getShop());
                        intent.putExtra("shopid1",o);
                        startActivity(intent);
                        finish();
                    }
                });

                int onetypeproductTprice = ((Integer.parseInt(cart.getPrice()))) * Integer.parseInt(cart.getQuentity());
                overalltotalprice = overalltotalprice + onetypeproductTprice;

                cart_viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        CharSequence options [] =  new CharSequence[]
                                {
                                       "Edit",
                                       "Remove"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(Cart_Activity.this);
                        builder.setTitle("Cart Options");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i)
                            {
                                if(i == 0)
                                {
                                    Intent intent = new Intent(Cart_Activity.this,Product_OC.class);
                                    intent.putExtra("pid",cart.getPid());
                                    startActivity(intent);
                                }

                                if(i == 1)
                                {

                                    cartlistref.child("Admin View")
                                            .child("Products")
                                            .child(currentuserid)
                                            .child(cart.getPid())
                                            .removeValue();

                                    cartlistref.child("User View")
                                            .child("Products")
                                            .child(currentuserid)
                                            .child(cart.getPid())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task)
                                                {
                                                    if (task.isSuccessful())
                                                    {
                                                        Toast.makeText(Cart_Activity.this, "Item removed", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(Cart_Activity.this,Homepage.class);
                                                        startActivity(intent);
                                                    }
                                                }
                                            });
                                }
                            }
                        });

                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public Cart_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout_design,parent,false);
                Cart_ViewHolder holder = new Cart_ViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();




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
                    String shippingstate = dataSnapshot.child("state").getValue().toString();
                    String username = dataSnapshot.child("name").getValue().toString();

                    if (shippingstate.equals("Shipped"))
                    {
                        txttotalamount.setText("Dear" + username + "\n Order Is Shipped Successfully");
                        recyclerView.setVisibility(View.GONE);
                        txtmasg1.setText("Congratulation Your Order Has Been Shipped  Suceesfully....Soon You Will Get Yopur Order At Your Door Step...");
                        txtmasg1.setVisibility(View.VISIBLE);
                        nextprocessbutton.setVisibility(View.GONE);

                        Toast.makeText(Cart_Activity.this, "You Can Purchase More Product Once You Recived Your First Ordre", Toast.LENGTH_SHORT).show();
                    }
                    else if(shippingstate.equals("Not Shipped"))
                    {
                        txttotalamount.setText("Shipping State = Not Shipped");
                        recyclerView.setVisibility(View.GONE);
                        txtmasg1.setText("Congratulation Your Order Has Been Shipped  Suceesfully....Soon It Will Be Verified...");
                        txtmasg1.setVisibility(View.VISIBLE);
                        nextprocessbutton.setVisibility(View.GONE);

                        Toast.makeText(Cart_Activity.this, "You Can Purchase More Product Once You Recived Your First Ordre", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
