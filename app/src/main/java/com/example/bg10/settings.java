package com.example.bg10;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class settings extends AppCompatActivity {
    Switch s1;
    SharedPref sharedpref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){

            setTheme(R.style.darktheme);
        }else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        s1=findViewById(R.id.switch1);

        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            s1.setChecked(true);
        }
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                    Intent mySuperIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mySuperIntent);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                    Intent mySuperIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mySuperIntent);
                }
            }
        });
        */

        sharedpref = new SharedPref(this);
        if(sharedpref.lloadNightModeState()==true){
            setTheme(R.style.darktheme);
        }else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        s1=findViewById(R.id.switch1);

        if(sharedpref.lloadNightModeState()==true){
            s1.setChecked(true);
        }
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sharedpref.setNightModeState(true);
                    Toast.makeText(getApplicationContext(), "Please refresh feed or restart", Toast.LENGTH_SHORT).show();
                    Intent mySuperIntent = new Intent(getApplicationContext(), settings.class);
                    startActivity(mySuperIntent);
                    finish();
                }else{
                    sharedpref.setNightModeState(false);

                    Toast.makeText(getApplicationContext(), "Please refresh feed or restart", Toast.LENGTH_SHORT).show();
                    Intent mySuperIntent = new Intent(getApplicationContext(), settings.class);
                    startActivity(mySuperIntent);
                    finish();
                }
            }
        });

    }


}
