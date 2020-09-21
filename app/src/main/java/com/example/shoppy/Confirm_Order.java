package com.example.shoppy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Confirm_Order extends AppCompatActivity {

    private EditText nameedittext,phoneedittext,addedittext,cityedittext;
    private Button confirmbtn;
    private TextView total;
    private String totalamount = "";

    private FirebaseAuth firebaseauth;
    private String currentuserid,shopidref,p;
    private FirebaseUser firebaseUser;
    private DatabaseReference profileuserref;
    private String h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm__order);

        confirmbtn = (Button) findViewById(R.id.coconfirmbtn);
        nameedittext = (EditText) findViewById(R.id.coname);
        phoneedittext = (EditText) findViewById(R.id.cophone);
        addedittext = (EditText) findViewById(R.id.coaddress);
        cityedittext = (EditText) findViewById(R.id.cocity);
        total = findViewById(R.id.cototal);

        totalamount = getIntent().getStringExtra("Total Price");
        shopidref = getIntent().getStringExtra("shopidref");
        p = getIntent().getStringExtra("shopid1");

        Toast.makeText(this, "j" + p, Toast.LENGTH_SHORT).show();

        firebaseauth = FirebaseAuth.getInstance();
        currentuserid = firebaseauth.getCurrentUser().getUid();
        profileuserref = FirebaseDatabase.getInstance().getReference().child("User").child(currentuserid);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        total.setText(totalamount + "RS.");

        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Check();
            }
        });

    }

    private void Check()
    {
        if(TextUtils.isEmpty(nameedittext.getText().toString()))
        {
            Toast.makeText(this, "Please Write Your Name", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(phoneedittext.getText().toString()))
        {
            Toast.makeText(this, "Please Write Your Phone Number", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(addedittext.getText().toString()))
        {
            Toast.makeText(this, "Please Write Your Address", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(cityedittext.getText().toString()))
        {
            Toast.makeText(this, "Please Write Your City Name", Toast.LENGTH_SHORT).show();
        }

        else
            {
                ConfirmOrder();
            }

    }

    private void ConfirmOrder()
    {
        final String savecurrentdate;

        java.util.Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentdate = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a");
        savecurrentdate = currentdate.format(calendar.getTime());


        final DatabaseReference orderref = FirebaseDatabase.getInstance().getReference().child("Orders");

        final DatabaseReference orderref1 = FirebaseDatabase.getInstance().getReference().child("Orders_For_Admin");

        HashMap<String, Object> ordermap = new HashMap<>();

        ordermap.put("totalamount",totalamount);
        ordermap.put("name",nameedittext.getText().toString());
        ordermap.put("phone",phoneedittext.getText().toString());
        ordermap.put("dateandtime", savecurrentdate);
        ordermap.put("address",addedittext.getText().toString());
        ordermap.put("city",cityedittext.getText().toString());
        ordermap.put("state","Not Shipped");
        ordermap.put("Product_Ready","Not Ready");
        ordermap.put("UserId",currentuserid);
        ordermap.put("shopkeeperid",shopidref);
        ordermap.put("shopid",p);

        orderref.child("For_Admin").child(shopidref).updateChildren(ordermap);

        orderref
                //.child(p)
                .child(shopidref)
                .child(currentuserid)
                .updateChildren(ordermap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                FirebaseDatabase.getInstance().getReference().child("Cart List").child("User View")
                        .child("Products")
                        .child(currentuserid)
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(Confirm_Order.this, "Order Placed Sucessfully", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(Confirm_Order.this,Homepage.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }

                                else
                                    {
                                        String message = task.getException().getMessage();
                                        Toast.makeText(Confirm_Order.this, "Error" + message, Toast.LENGTH_LONG).show();
                                    }
                            }
                        });

            }
        });
    }
}
