package com.example.shoppy;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;



public class SSDetails extends AppCompatActivity  {

    Button btnchangepassword;
    Button btndelete;


    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDb;
    private ProgressDialog dailog;

    private static final String USERS = "users";

    private FirebaseUser user1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_s_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Security Setting");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //if the user is not logged in
        //that means current user will return null
        if (firebaseAuth.getCurrentUser() == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, MainActivity.class));
        }

        dailog = new ProgressDialog(this);
        btnchangepassword = findViewById(R.id.changepassword);
        btndelete = findViewById(R.id.deactive);

        btnchangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SSDetails.this,changepassword.class);
                startActivity(intent);
            }
        });



        btndelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final FirebaseUser user = firebaseAuth.getCurrentUser();

                dailog.setMessage("Deleting User");
                dailog.show();

                AlertDialog.Builder builder = new AlertDialog.Builder(SSDetails.this);
                builder.setTitle("Delete Account");
                builder.setMessage("Sure To Delete");
                builder.setPositiveButton("Delete ",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {
                               user.delete();
                               Toast.makeText(SSDetails.this,"Account Deleted Sucessfully",Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(SSDetails.this,registration.class);
                                startActivity(intent);
                            }
                        });
                builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {

                        dailog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.show();


            }
        });

    }






}
