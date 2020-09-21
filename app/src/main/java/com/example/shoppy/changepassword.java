package com.example.shoppy;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class changepassword extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;
    private EditText p2;
    private EditText p3, p4;
    private Button p5;
    private ProgressDialog dailog;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDb;

    private static final String USERS = "users";

    private FirebaseUser user1;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Change Password");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        dailog = new ProgressDialog(this);

        p2 = (EditText) findViewById(R.id.p2);
        p3 = (EditText) findViewById(R.id.p3);
        p4 = (EditText) findViewById(R.id.p4);

        p5 = (Button) findViewById(R.id.p5);
        p5.setOnClickListener(this);


    }


    private void registerUser(){

        final String oldpassword = p2.getText().toString().trim();
        final String newPass = p3.getText().toString().trim();
        final String confirm = p4.getText().toString().trim();



        user1 = FirebaseAuth.getInstance().getCurrentUser();
        final String email = user1.getEmail();


        firebaseAuth = FirebaseAuth.getInstance();


        if(!newPass.equals(confirm))
        {
            Toast.makeText(changepassword.this,"Password Not matching",Toast.LENGTH_SHORT).show();
        }
        else
        {

            AuthCredential credential = EmailAuthProvider.getCredential(email,oldpassword);

            dailog.setMessage("Changing Password");
            dailog.show();

            user1.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        user1.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()) {

                                    Toast.makeText(changepassword.this, "Your Password Not Changed", Toast.LENGTH_SHORT).show();
                                    dailog.dismiss();

                                } else {

                                    Toast.makeText(changepassword.this, "Your Password  Changed", Toast.LENGTH_SHORT).show();
                                    dailog.dismiss();

                                    finish();

                                    Intent intent = new Intent(changepassword.this,MainActivity.class);
                                    startActivity(intent);


                                }
                            }
                        });
                    } else
                        {


                        }
                }
            });

        }
    }

    public void onClick(View view) {


        if (view == p5) {
            registerUser();
        }

    }

}
