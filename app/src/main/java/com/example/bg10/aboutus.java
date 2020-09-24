package com.example.bg10;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class aboutus extends AppCompatActivity {
    ImageView o1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_aboutus);

        o1= (ImageView) findViewById(R.id.imageaboutus);
        o1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","admin@ohhmybug.com", null));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Hey there!");
                intent.putExtra(Intent.EXTRA_TEXT, "Sent from TheTechDynamite for Android OS");
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));


            }
        });


    }
}
