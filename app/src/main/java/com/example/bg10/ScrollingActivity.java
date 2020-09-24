package com.example.bg10;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ScrollingActivity extends AppCompatActivity {
    Bundle b1 = new Bundle();
    String url = "https://ohhmybug.com/wp-json/wp/v2/pages";
    View v;
    String message;
    int arraynumber;
    String quotecontent;
    SwipeRefreshLayout pullToRefresh;
    JSONArray responseglobal = null;
    String postUrl;
    String postTitle;
    String postExcerpt;
    String MediaUrl = null;
    WebView w1;
    FloatingActionButton fab;
    SharedPref sharedpref;
    String darkcss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("M1", "Scrolling: onCreate");
        sharedpref = new SharedPref(this);
        if(sharedpref.lloadNightModeState()==true){
            setTheme(R.style.darktheme);
            darkcss="<style type=\"text/css\">body{color: #fff; background-color: #000;}</style>";
        }else{
            setTheme(R.style.AppTheme);
            darkcss="";
        }

        super.onCreate(savedInstanceState);



        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarscrolling);
        //toolbar.setTitle(message);
        //setSupportActionBar(toolbar);


        b1 = getIntent().getExtras();
        message = b1.getString("Title1");
        if(message.equals("Privacy")){
            arraynumber=1;
            setContentView(R.layout.activity_scrolling);
            firstServiceCall();
        }
        else if(message.equals("About")){
            arraynumber=0;
            setContentView(R.layout.activity_scrolling);
            firstServiceCall();
        }


        Log.d("L1", "Scrolling: activity");
        fab = (FloatingActionButton) findViewById(R.id.fabscrolling);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String title = "";
                String body = postTitle + "\n\n" + postUrl;
                myIntent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(myIntent, "Share Using: "));
            }
        });





    }

    public void firstServiceCall() {
        Log.d("L1", "Scrolling: firstServiceCall");
        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("L1", "Scrolling: onResponse");
                        if (response.length() > 0) {
                            responseglobal = response;

                            try {
                                JSONObject jsonObj = response.getJSONObject(arraynumber);
                                JSONObject mapTitlej = jsonObj.getJSONObject("title");
                                postTitle = (String) mapTitlej.get("rendered");
                                Log.d("L1", "Scrolling: postTitle: " + postTitle);
                                postUrl = jsonObj.getString("link");

                                JSONObject mapContent = jsonObj.getJSONObject("content");
                                Log.d("L1", "Scrolling: content");
                                quotecontent = (String) mapContent.get("rendered");
                                Log.d("L1", "Scrolling: content: " + quotecontent);
                                //Log.d("L1", "Quote Content: "+quotecontent);

                                w1 = findViewById(R.id.webviewscrolling);
                                Log.d("L1", "Scrolling: WebViewID");


                                //w1.setWebViewClient(new WebViewClient());
                                String imgcss = "<style>img{max-width: 100%; width:auto; height: auto;}</style>";
                                String iframecss = "<style>iframe{max-width: 100%; width:100%; height: 100%; align: middle; allowfullscreen: allowfullscreen;}</style>";
                                String title = "<h1>" + postTitle + "</h1>";
                                String a = "<html><head>" + imgcss + iframecss + "</head><body>" + title + quotecontent + "</body></html>";
                                Log.d("L1", "ArticleWeb: WebContent: " + a);
                                w1.loadDataWithBaseURL("", "<html><head>" + darkcss + imgcss + iframecss + "</head><body>" + title + quotecontent + "</body></html>", "text/html", "UTF-8", "");


                            } catch (JSONException e) {

                                Log.d("L1", "Scrolling: Exception: Invalid JSON Object.");
                            }
                            Log.d("L1", "Scrolling: adapter called.");

                        } else {
                            Log.d("L1", "Scrolling: No data");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("L1", "Scrolling: Posts Load Failed");
                        Snackbar.make(findViewById(R.id.webviewscrolling), "No response. Server offline or check connection.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        //findViewById(R.id.progressbarhome).setVisibility(View.GONE);
                        pullToRefresh.setRefreshing(false);
                    }
                }
        );
        RequestQueue rQueue = Volley.newRequestQueue(getApplicationContext());
        rQueue.add(arrReq);
    }

    @Override
    protected void onPostResume() {
        Log.d("M1", "Scroll: onPostResume");
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
        Log.d("M1", "Scroll: ReCreate");
        sharedpref = new SharedPref(this);
        if(sharedpref.lloadNightModeState()==true){
            setTheme(R.style.darktheme);
        }else{
            setTheme(R.style.AppTheme);
        }
        super.recreate();
    }
}
