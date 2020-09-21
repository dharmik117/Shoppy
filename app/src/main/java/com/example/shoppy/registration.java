package com.example.shoppy;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
import Model.customer;
import Prevalent.Prevalent;
import de.hdodenhof.circleimageview.CircleImageView;

public class registration extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextemail;
    private EditText editTextpassword;
    private EditText edittextfullname;
    private EditText edittextemailid;
    private EditText edittextphone;
    private  RadioButton radiogendermale,radiogenderfemale;
    private Button buttonregister;
    private TextView textviewsignin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private CircleImageView userprofileimage;
    private String phone,password;

    private DatabaseReference databaseReference;

    String gender="";


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),Homepage.class));
            finish();
        }


        progressDialog = new ProgressDialog(this);

        editTextpassword = (EditText) findViewById(R.id.password);
        editTextemail = (EditText) findViewById(R.id.edittextemail);
        edittextfullname = (EditText)findViewById(R.id.edittextfullname);
        edittextemailid = (EditText)findViewById(R.id.edittextemailid);
        edittextphone = (EditText)findViewById(R.id.edittextphone);
        buttonregister = (Button) findViewById(R.id.btnregister);
        textviewsignin = (TextView) findViewById(R.id.textviewsignin);
        radiogendermale = (RadioButton)findViewById(R.id.radioButtonmale);
        radiogenderfemale = (RadioButton)findViewById(R.id.radioButtonfemale);
        userprofileimage = (CircleImageView)findViewById(R.id.profilepic);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Create Your Profile");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        buttonregister.setOnClickListener(this);
        textviewsignin.setOnClickListener(this);

    }

    

    private void registerUser(){

        final String email = editTextemail.getText().toString().trim();
        final String password = editTextpassword.getText().toString().trim();
        final String fullname = edittextfullname.getText().toString().trim();
        final String emailid = edittextemailid.getText().toString().trim();
        final String phone = edittextphone.getText().toString().trim();


        if (radiogendermale.isChecked()){

            gender="Male";
        }

        if (radiogenderfemale.isChecked()){

            gender="Female";
        }


        if (TextUtils.isEmpty(email)){

            Toast.makeText(this,"Please Enter Email-Id",Toast.LENGTH_SHORT).show();
            return;

        }

        if (TextUtils.isEmpty(password)){

            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            return;

        }

        if (TextUtils.isEmpty(fullname)){

            Toast.makeText(this,"Please Enter Full Name",Toast.LENGTH_SHORT).show();
            return;

        }

        if (TextUtils.isEmpty(emailid)){

            Toast.makeText(this,"Please Enter Email-Id",Toast.LENGTH_SHORT).show();
            return;

        }

        if (TextUtils.isEmpty(phone)){

            Toast.makeText(this,"Please Enter Your Phone Number",Toast.LENGTH_SHORT).show();
            return;

        }



        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            customer information = new customer(
                                    email,
                                    fullname,
                                    emailid,
                                    phone,
                                    gender
                            );

                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(firebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(information);



                            Toast.makeText(registration.this,"Sucessfully Registered",Toast.LENGTH_SHORT).show();
                            {
                                startActivity(new Intent(getApplicationContext(),Homepage.class));
                                finish();

                            }

                        }
                        else {
                            String message = task.getException().getMessage();
                            Toast.makeText(registration.this,"Error" + message,Toast.LENGTH_SHORT).show();

                        }
                        progressDialog.dismiss();
                    }
                });

        Sessions sessions = new Sessions(registration.this);
        sessions.createloginsession(email,fullname,emailid,phone,gender);

    }



    @Override
    public void onClick(View view) {

        if (view == buttonregister)
        {
            registerUser();
        }

        if (view == textviewsignin){
            startActivity(new Intent(this,MainActivity.class));
        }

    }
}
