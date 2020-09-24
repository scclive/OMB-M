package com.example.bg10;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class legacy extends Fragment {
    Bundle b1 = new Bundle();
    String url = "https://ohhmybug.com/wp-json/wp/v2/posts/?filter[category_name]=legacy&_embed";
    View v;
    String quotecontent;
    SwipeRefreshLayout pullToRefresh;
    JSONArray responseglobal = null;
    String postTitle[];
    String postExcerpt[];
    String MediaUrl[] = null;
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_legacy, container, false);

        /*
        postList = (ListView) v.findViewById(R.id.postList);
        mediaList = (ListView) v.findViewById(R.id.postList);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        */
        firstServiceCall();
        pullToRefresh = v.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                firstServiceCall();
            }
        });
        return v;
    }


    public void firstServiceCall() {
        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    ArrayList<ExampleItem> exampleList = new ArrayList<>();


                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() > 0) {
                            responseglobal = response;
                            postTitle = new String[response.length()];
                            postExcerpt = new String[response.length()];
                            MediaUrl = new String[response.length()];
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    Log.d("L1", "Legacy");
                                    JSONObject jsonObj = response.getJSONObject(i);
                                    JSONObject mapExcerptj = jsonObj.getJSONObject("excerpt");
                                    postExcerpt[i] = (String) String.valueOf(Html.fromHtml(String.valueOf(mapExcerptj.get("rendered"))));
                                    Log.d("L1", "Legacy: "+postExcerpt[i]);


                                    JSONObject mapContent = jsonObj.getJSONObject("content");
                                    Log.d("L1", "Legacy: mapContent: "+mapContent);
                                    quotecontent = (String) String.valueOf(Html.fromHtml(String.valueOf(mapContent.get("rendered"))));
                                    Log.d("L1", "Legacy: Content: " + quotecontent);
                                    //excerpt=title
                                    //content=excerpt
                                    exampleList.add(new ExampleItem("null", postExcerpt[i], quotecontent));

                                } catch (JSONException e) {
                                    Log.d("L1", "Legacy: Invalid JSON Object.");
                                }
                            }
                            mRecyclerView = v.findViewById(R.id.postList);
                            mRecyclerView.setHasFixedSize(true);
                            mLayoutManager = new LinearLayoutManager(getContext());
                            mAdapter = new ExampleAdapter(exampleList, getContext());

                            mRecyclerView.setLayoutManager(mLayoutManager);
                            mRecyclerView.setAdapter(mAdapter);
                            Log.d("L1", "Legacy: adapter called.");
                            v.findViewById(R.id.progressbarlegacy).setVisibility(View.GONE);
                            pullToRefresh.setRefreshing(false);
                            mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {

                                    try {
                                        JSONObject jsonObj = responseglobal.getJSONObject(position);
                                        Toast.makeText(getContext(), postTitle[position], Toast.LENGTH_LONG).show();
                                        Log.d("L1", "Legacy: Clicked");

                                        b1.putString("JSONObject", jsonObj.toString());
                                        Intent mySuperIntent = new Intent(getContext(), Articleweb.class);
                                        mySuperIntent.putExtras(b1);
                                        startActivity(mySuperIntent);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Log.d("L1", "Legacy: Click Exception");
                                    }
                                }
                            });
                        } else {
                            Log.d("L1", "Legacy: No data");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("L1", "Legacy: Posts Load Failed");
                        v.findViewById(R.id.progressbarhome).setVisibility(View.GONE);
                        pullToRefresh.setRefreshing(false);
                        Snackbar.make(v, "No response. Server offline or check connection.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
        );
        RequestQueue rQueue = Volley.newRequestQueue(getContext());
        rQueue.add(arrReq);
    }
}