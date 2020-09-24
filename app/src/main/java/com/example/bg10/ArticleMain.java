package com.example.bg10;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ArticleMain extends AppCompatActivity {
    JSONObject response;
    String postTitle;
    String postExcerpt;
    String postContent;
    String postCategory;
    String MediaUrl;
    String formattype;
    TextView t1;
    TextView t2;
    ImageView i1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        Bundle b1 = getIntent().getExtras();
        try {
            response = new JSONObject(getIntent().getStringExtra("JSONObject"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_article_main);

        t1=findViewById(R.id.articletitle2);
        t2=findViewById(R.id.articleexcerpt2);
        i1=findViewById(R.id.articleimage2);

        try {


            formattype = response.getString("format");
            Log.d("L1", "formattype: "+formattype);
            JSONArray mapCategory = response.getJSONArray("categories");
            Log.d("L1", "mapCategory: "+mapCategory);

            postCategory = mapCategory.getString(0);
            Log.d("L1", "postCategory: "+postCategory);



            if(formattype.equals("quote")||postCategory.equals("4")){
                if(formattype.equals("quote")){
                    Log.d("L1", "Quote");
                    JSONObject mapExcerptj = response.getJSONObject("excerpt");
                    postExcerpt = (String) (mapExcerptj.get("rendered"));
                    Log.d("L1", postExcerpt);
                    JSONObject mapContent = response.getJSONObject("content");
                    postContent = (String) mapContent.get("rendered");
                    Log.d("L1", "Quote Content: " + postContent);
                    //excerpt=title
                    //content=excerpt
                    t1.setText(Html.fromHtml(postExcerpt));
                    Log.d("L1", "Set Title");
                    t2.setText(Html.fromHtml(postContent));
                    Log.d("L1", "Set Content");
                } else{
                    Log.d("L1", "Video");
                    JSONObject mapTitlej = response.getJSONObject("title");
                    JSONObject mapExcerptj = response.getJSONObject("excerpt");
                    postTitle = (String) mapTitlej.get("rendered");
                    postExcerpt = (String) (mapExcerptj.get("rendered"));
                    Log.d("L1", postExcerpt);
                    JSONObject mapContent = response.getJSONObject("content");
                    postContent = (String) mapContent.get("rendered");
                    Log.d("L1", "Video Content: " + postContent);

                    JSONObject map_embeddedj = response.getJSONObject("_embedded");
                    Log.d("L1", "_embedded.");
                    JSONArray mapWpfeaturedmediaj = map_embeddedj.getJSONArray("wp:featuredmedia");
                    Log.d("L1", "wp:embbeddedmedia: " + mapWpfeaturedmediaj);
                    JSONObject mapSerialnumberj = mapWpfeaturedmediaj.getJSONObject(0);
                    Log.d("L1", "0: " + mapSerialnumberj);
                    MediaUrl = (String) mapSerialnumberj.getString("source_url");
                    Log.d("L1", MediaUrl);

                    //content=excerpt+content
                    t1.setText(Html.fromHtml(postTitle));
                    Log.d("L1", "Set Title");
                    t2.setText(Html.fromHtml(postExcerpt+"                      "+postContent));
                    Log.d("L1", "Set Content");
                    Picasso.with(getApplicationContext()).load(MediaUrl).into(i1);
                    Log.d("L1", "Set Image");
                }
            }else{
                JSONObject mapTitlej = response.getJSONObject("title");
                JSONObject mapContentj = response.getJSONObject("content");
                postTitle = (String) mapTitlej.get("rendered");
                postContent = (String) mapContentj.get("rendered");
                Log.d("L1", postTitle);
                Log.d("L1", postContent);


                JSONObject map_embeddedj = response.getJSONObject("_embedded");
                Log.d("L1", "_embedded.");
                JSONArray mapWpfeaturedmediaj = map_embeddedj.getJSONArray("wp:featuredmedia");
                Log.d("L1", "wp:embbeddedmedia: " + mapWpfeaturedmediaj);
                JSONObject mapSerialnumberj = mapWpfeaturedmediaj.getJSONObject(0);
                Log.d("L1", "0: " + mapSerialnumberj);
                MediaUrl = (String) mapSerialnumberj.getString("source_url");
                Log.d("L1", MediaUrl);


                t1.setText(Html.fromHtml(postTitle));
                Log.d("L1", "Set Title");
                t2.setText(Html.fromHtml(postContent));
                Log.d("L1", "Set Content");
                Picasso.with(getApplicationContext()).load(MediaUrl).into(i1);
                Log.d("L1", "Set Image");
            }


        } catch (JSONException e) {
            Log.d("L1", "Invalid JSON Object.");
        }


    }

}
