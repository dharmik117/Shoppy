package com.example.shoppy;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class Setting extends AppCompatActivity {

    private DrawerLayout drawer;
    private LinearLayout lleditprofile,llsecuritysetting;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    Button button;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        Toolbar toolbar = findViewById(R.id.toolbar);
        lleditprofile = findViewById(R.id.lleditprofile);
        llsecuritysetting = findViewById(R.id.llsecuritysetting);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        llsecuritysetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting.this,SSDetails.class);
                startActivity(intent);
            }
        });

        lleditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting.this,EditProfile.class);
                startActivity(intent);
            }
        });
        
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (this.drawer.isDrawerOpen(GravityCompat.START)) {
            this.drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }


}
