package com.example.shoppy;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

import Model.Sessions;
import Prevalent.Prevalent;

public class Notificationpage extends AppCompatActivity {

    private Button btngo;
    private TextView trial;
    private DatabaseReference tryref;
    private FirebaseAuth mauth;
    private String currentuserid;
    private FirebaseUser firebaseUser;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificationpage);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        trial = (TextView) findViewById(R.id.txttry);

        trial.setText(Prevalent.UserPhonekey);

        Sessions sessions = new Sessions(this);
        HashMap<String, String> userdetails = sessions.getuserdetails();
        String phone = userdetails.get(Sessions.KEY_Phoneno);

        Toast.makeText(this, "user" + phone, Toast.LENGTH_SHORT).show();


    }
}
