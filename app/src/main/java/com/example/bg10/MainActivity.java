package com.example.bg10;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Bundle b1 = new Bundle();
    SharedPref sharedpref;
    home homefragment = new home();
    boolean doubleBackToExitPressedOnce = false;
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    @Override
    protected void onPostResume() {
        Log.d("M1", "MainActivity: onPostResume");
        sharedpref = new SharedPref(this);
        if(sharedpref.lloadNightModeState()==true){
            setTheme(R.style.darktheme);
        }else{
            setTheme(R.style.AppTheme);
        }
        super.onPostResume();
    }

    @Override
    public void recreate() {
        Log.d("M1", "MainActivity: ReCreate");
        sharedpref = new SharedPref(this);
        if(sharedpref.lloadNightModeState()==true){
            setTheme(R.style.darktheme);
        }else{
            setTheme(R.style.AppTheme);
        }
        super.recreate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);











        Log.d("L1", "MainActivity");
        fragmentTransaction.add(R.id.f1, homefragment);
        fragmentTransaction.commit();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String body = "The Tech Dynamite (TTD)'s unusal intake helps you grasp the best of what other people have already figured out and" +
                        "Peek at the models and understand how the world technology works.\n\n" +
                        "Visit https://ohhmybug.com/ \n" +
                        "Or\n" +
                        "Download TechDynamite App for Android OS for more";
                myIntent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(myIntent, "Share Using: "));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            //double back press to exit

                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    return;
                }
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Contact) {
            b1.putString("Title1", "Contact");
            Intent mySuperIntent = new Intent(getApplicationContext(), aboutus.class);
            mySuperIntent.putExtras(b1);
            startActivity(mySuperIntent);
        } else if (id == R.id.action_About) {
            b1.putString("Title1", "About");
            Intent mySuperIntent = new Intent(getApplicationContext(), ScrollingActivity.class);
            mySuperIntent.putExtras(b1);
            startActivity(mySuperIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_Home) {
            if (getTitle() != "Tech Dynamite") {
                setTitle("Tech Dynamite");
                //Intent mySuperIntent = new Intent(getApplicationContext(), MainActivity.class);
                //startActivity(mySuperIntent);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.f1, homefragment);
                Log.d("L1", "Home Replace");
                fragmentTransaction.commit();
            }

        } else if (id == R.id.nav_howto) {
            if (getTitle() != "How To") {
                setTitle("How To");
                howto howtofragment = new howto();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.f1, howtofragment);
                Log.d("L1", "How To Replace");
                fragmentTransaction.commit();
                Log.d("L1", "How To Commit");
            }


        } else if (id == R.id.nav_Videos) {
            if (getTitle() != "Videos") {
                setTitle("Videos");
                videos videosfragment = new videos();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.f1, videosfragment);
                Log.d("L1", "Videos Replace");
                fragmentTransaction.commit();
                Log.d("L1", "Videos Commit");
            }


        } else if (id == R.id.nav_Amuse) {
            if (getTitle() != "Amuse") {
                setTitle("Amuse");
                amuse amusefragment = new amuse();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.f1, amusefragment);
                Log.d("L1", "Amuse Replace");
                fragmentTransaction.commit();
                Log.d("L1", "Amuse Commit");
            }


        } else if (id == R.id.nav_Images) {
            if (getTitle() != "Images") {
                setTitle("Images");
                images imagesfragment = new images();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.f1, imagesfragment);
                Log.d("L1", "Images Replace");
                fragmentTransaction.commit();
                Log.d("L1", "Images Commit");
            }


        } else if (id == R.id.nav_Legacy) {
            if (getTitle() != "Legacy") {
                setTitle("Legacy");
                legacy legacyfragment = new legacy();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.f1, legacyfragment);
                Log.d("L1", "Legacy Replace");
                fragmentTransaction.commit();
                Log.d("L1", "Legacy Commit");
            }
            ;

        } else if (id == R.id.nav_privacy) {
            b1.putString("Title1", "Privacy");
            Intent mySuperIntent = new Intent(getApplicationContext(), ScrollingActivity.class);
            mySuperIntent.putExtras(b1);
            startActivity(mySuperIntent);
        }
        else if (id == R.id.nav_settngs) {
            b1.putString("Title1", "Settings");
            Intent mySuperIntent = new Intent(getApplicationContext(), settings.class);
            mySuperIntent.putExtras(b1);
            startActivity(mySuperIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
