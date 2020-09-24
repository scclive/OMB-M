package com.example.bg10;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Articleweb extends AppCompatActivity {
    JSONObject response;
    String postUrl;
    String postTitle;
    String postExcerpt;
    String postContent;
    String postCategory;
    String MediaUrl;
    String formattype;
    WebView w1;
    FloatingActionButton fab;
    SharedPref sharedpref;
    String darkcss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("M1", "Articleweb: onCreate");
        sharedpref = new SharedPref(this);
        if(sharedpref.lloadNightModeState()==true){
            setTheme(R.style.darktheme);
            darkcss="<style type=\"text/css\">body{color: #fff; background-color: #000;}</style>";
        }else{
            setTheme(R.style.AppTheme);
            darkcss="";
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articleweb2);
        getSupportActionBar().hide();
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarlegacy);




        w1=findViewById(R.id.articleexcerpt3);
        fab= (FloatingActionButton) findViewById(R.id.fabartivleweb2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            w1.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY > oldScrollY && scrollY > 0) {
                        fab.hide();
                    }
                    if (scrollY < oldScrollY) {
                        fab.show();
                    }
                }
            });
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String title = "";
                String body = postTitle+"\n\n"+postUrl;
                myIntent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(myIntent, "Share Using: "));
            }
        });






        Bundle b1 = getIntent().getExtras();
        try {
            response = new JSONObject(getIntent().getStringExtra("JSONObject"));

            formattype = response.getString("format");
            Log.d("L1", "ArticleWeb: formattype: "+formattype);
            JSONArray mapCategory = response.getJSONArray("categories");
            Log.d("L1", "ArticleWeb: mapCategory: "+mapCategory);
            postCategory = mapCategory.getString(0);
            Log.d("L1", "ArticleWeb: postCategory: "+postCategory);
            postUrl = response.getString("link");

            if(formattype.equals("quote")||postCategory.equals("4")){
                if(formattype.equals("quote")){
                    Log.d("L1", "ArticleWeb: Legacy");
                    JSONObject mapExcerptj = response.getJSONObject("excerpt");
                    postExcerpt = (String) (mapExcerptj.get("rendered"));
                    Log.d("L1", "ArticleWeb: Legacy: postExcerpt: "+postExcerpt);
                    JSONObject mapContent = response.getJSONObject("content");
                    postContent = (String) mapContent.get("rendered");
                    Log.d("L1", "ArticleWeb: Legacy: postContent: " + postContent);
                    //excerpt=title
                    //content=excerpt
                    postTitle=postExcerpt;
                    postExcerpt=postContent;
                } else{
                    Log.d("L1", "ArticleWeb: Video");
                    JSONObject mapTitlej = response.getJSONObject("title");
                    JSONObject mapExcerptj = response.getJSONObject("excerpt");
                    postTitle = (String) mapTitlej.get("rendered");
                    postExcerpt = (String) (mapExcerptj.get("rendered"));
                    Log.d("L1", "ArticleWeb: Video: postExcerpt: "+postExcerpt);
                    JSONObject mapContent = response.getJSONObject("content");
                    postContent = (String) mapContent.get("rendered");
                    Log.d("L1", "ArticleWeb: Video: postContent: " + postContent);
                    //content=excerpt+content
                    postContent=postExcerpt+postContent;

                    JSONObject map_embeddedj = response.getJSONObject("_embedded");
                    Log.d("L1", "ArticleWeb: Video: _embedded.");
                    JSONArray mapWpfeaturedmediaj = map_embeddedj.getJSONArray("wp:featuredmedia");
                    Log.d("L1", "ArticleWeb: Video: wp:embbeddedmedia: " + mapWpfeaturedmediaj);
                    JSONObject mapSerialnumberj = mapWpfeaturedmediaj.getJSONObject(0);
                    Log.d("L1", "ArticleWeb: Video: 0: " + mapSerialnumberj);
                    MediaUrl = (String) mapSerialnumberj.getString("source_url");
                    Log.d("L1", MediaUrl);

                }
            }else{
                JSONObject mapTitlej = response.getJSONObject("title");
                postTitle = (String) mapTitlej.get("rendered");
                JSONObject mapContent = response.getJSONObject("content");
                postContent = (String) mapContent.get("rendered");
                Log.d("L1", "ArticleWeb: Content: " + postContent);
                JSONObject map_embeddedj = response.getJSONObject("_embedded");
                Log.d("L1", "ArticleWeb: _embedded.");
                JSONArray mapWpfeaturedmediaj = map_embeddedj.getJSONArray("wp:featuredmedia");
                Log.d("L1", "ArticleWeb: wp:embbeddedmedia: " + mapWpfeaturedmediaj);
                JSONObject mapSerialnumberj = mapWpfeaturedmediaj.getJSONObject(0);
                Log.d("L1", "ArticleWeb: 0: " + mapSerialnumberj);
                MediaUrl = (String) mapSerialnumberj.getString("source_url");
            }

            w1.setWebViewClient(new WebViewClient());
            String imgcss = "<style>img{max-width: 100%; width:auto; height: auto;}</style>";
            String iframecss = "<style>iframe{max-width: 100%; width:100%; height: 100%; align: middle; allowfullscreen: allowfullscreen;}</style>";
            String featuredimage = "<img src=\""+MediaUrl+"\" >";
            String title = "<h1>"+postTitle+"</h1>";
            String a ="<html><head>" + imgcss + iframecss + "</head><body>" + featuredimage + title + postContent + "</body></html>";
            Log.d("L1", "ArticleWeb: WebContent: "+a);
            w1.loadDataWithBaseURL("", "<html><head>" + darkcss + imgcss + iframecss + "</head><body>" + featuredimage + title + postContent + "</body></html>", "text/html", "UTF-8", "");

            //w1.setInitialScale(100);

            WebSettings webSettings = w1.getSettings();
            webSettings.setJavaScriptEnabled(true);
            //webSettings.setAppCacheEnabled(true);
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            webSettings.setDomStorageEnabled(true);
            webSettings.setSupportZoom(true);
            //webSettings.setTextZoom(5);
            //w1.setInitialScale(1);
            //webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
            //webSettings.setLoadWithOverviewMode(true);
            //webSettings.setUseWideViewPort(true);


        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("L1", "ArticleWeb: Invalid JSON Object");
            AlertDialog alertDialog = new AlertDialog.Builder(Articleweb.this).create();
            alertDialog.setTitle("Oops");
            alertDialog.setMessage("The post you are trying to open is missing or either unavailable at the moemnt.");

            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            Log.d("L1", "ArticleWeb: Alert Message");
            alertDialog.show();
            //Toast.makeText(getApplicationContext(), "Oops", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onPostResume() {
        Log.d("M1", "Articleweb: onPostResume");
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
        Log.d("M1", "Articleweb: ReCreate");
        sharedpref = new SharedPref(this);
        if(sharedpref.lloadNightModeState()==true){
            setTheme(R.style.darktheme);
        }else{
            setTheme(R.style.AppTheme);
        }
        super.recreate();
    }





    @Override
    public void onBackPressed() {
        if (w1.canGoBack()) {
            w1.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
