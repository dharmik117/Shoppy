package com.example.shoppy;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class EditProfile extends AppCompatActivity {

    private EditText etusername,etemail,etphone;
    private Button btnsave;
    private FirebaseAuth firebaseauth;
    private FirebaseDatabase firebasedatabase;
    private String currentuserid;
    private DatabaseReference profileuserref;
    private FirebaseUser firebaseUser;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Edit Your Profile");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        firebaseauth = FirebaseAuth.getInstance();
        currentuserid = firebaseauth.getCurrentUser().getUid();
        profileuserref = FirebaseDatabase.getInstance().getReference().child("User").child(currentuserid);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        etusername = findViewById(R.id.etusername);
        etemail = findViewById(R.id.etemail);
        etphone = findViewById(R.id.etphone);
        btnsave = findViewById(R.id.btnsave);


        profileuserref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    final String myuserprofname = dataSnapshot.child("fullname").getValue().toString().trim();
                    final String myphone = dataSnapshot.child("phoneno").getValue().toString().trim();
                    String myemail = dataSnapshot.child("emailid").getValue().toString().trim();



                    etusername.setText(myuserprofname);
                    etphone.setText(myphone);
                    etemail.setText(myemail);

                    btnsave.setOnClickListener(new View.OnClickListener() {


                        @Override
                        public void onClick(View view) {

                            final ProgressDialog progressDialog = new ProgressDialog(EditProfile.this);
                            progressDialog.setTitle("Update Profile");
                            progressDialog.setMessage("Updating Profile...");
                            progressDialog.show();


                            FirebaseUser user = firebaseauth.getCurrentUser();
                            String userKey = user.getUid();

                            final String name = etusername.getText().toString().trim();
                            final String phone = etphone.getText().toString().trim();
                            final String email = etemail.getText().toString().trim();


                            FirebaseDatabase  database = FirebaseDatabase.getInstance();
                            DatabaseReference mDatabaseRef = database.getReference();


                            mDatabaseRef.child("User").child(userKey).child("fullname").setValue(name);
                            mDatabaseRef.child("User").child(userKey).child("phoneno").setValue(phone);
                            mDatabaseRef.child("User").child(userKey).child("emailid").setValue(email)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid)
                                {
                                    Toast.makeText(EditProfile.this,"Profile Update Sucessfully",Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    finish();

                                    Intent intent = new Intent(EditProfile.this,ProfilePage.class);
                                    startActivity(intent);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {
                                    Toast.makeText(EditProfile.this,"Failed To Update Profile",Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();

                                }
                            });
                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
