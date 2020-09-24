package com.example.bg10;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
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

import com.android.volley.DefaultRetryPolicy;
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


public class home extends Fragment {
    Bundle b1 = new Bundle();
    String url = "https://ohhmybug.com/wp-json/wp/v2/posts/?filter[posts_per_page]=30&_embed";
    View v;
    String quotecontent;
    SwipeRefreshLayout pullToRefresh;
    JSONArray responseglobal = null;
    String postTitle[];
    String postExcerpt[];
    String MediaUrl[] = null;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    Parcelable mListState;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = v.findViewById(R.id.postList);
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



     /*
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    */





    /*
    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        // Save list state
        Log.d("L1", "Home: onSaveInstanceState");
        mListState = mLayoutManager.onSaveInstanceState();
        Log.d("L1", "Home: onSaveInstanceState: mListState");
        state.putParcelable(KEY_RECYCLER_STATE, mListState);
        Log.d("L1", "Home: onSaveInstanceState: putParcelable");
    }
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        // Retrieve list state and list/item positions
        if(state != null)
            mListState = state.getParcelable(KEY_RECYCLER_STATE);
    }
    */




    /*
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        // Retrieve list state and list/item positions
        Log.d("L1", "Home: onActivityCreated");
        if(state != null){
            Log.d("L1", "Home: onActivityCreated: Before getParcelable");
            mListState = state.getParcelable(KEY_RECYCLER_STATE);
            Log.d("L1", "Home: onActivityCreated: getParcelable");
            mLayoutManager.onRestoreInstanceState(mListState);
            //mLayoutManager.onRestoreInstanceState(mListState);
            Log.d("L1", "Home: onActivityCreated: onRestoreInstanceState");
        }

    }
    */

    /*
    @Override
    public void onResume() {
        super.onResume();

        Log.d("L1", "Home: onResume");
        if (mListState != null) {
            Log.d("L1", "Home: onResume: Before onRestoreInstanceState");
            //mLayoutManager.onActivityCreated(mListState);
            mLayoutManager.onRestoreInstanceState(mListState);
            Log.d("L1", "Home: onResume: onRestoreInstanceState");
        }
    }

    */

/*
    @Override
    public void onPause() {
        super.onPause();
        Log.d("L1", "Home: onPause");
        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        Log.d("L1", "Home: onPause: Parcelable");
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
        Log.d("L1", "Home: onPause: Saved");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("L1", "Home: onResume");
        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            Log.d("L1", "Home: onResume: Parcelable");
            mRecyclerView.getLayoutManager();
            Log.d("L1", "Home: onResume: Saved");
        }
    }
*/
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
                                    JSONObject jsonObj = response.getJSONObject(i);
                                    JSONObject mapTitlej = jsonObj.getJSONObject("title");
                                    JSONObject mapExcerptj = jsonObj.getJSONObject("excerpt");
                                    postTitle[i] = (String) String.valueOf(Html.fromHtml(String.valueOf(mapTitlej.get("rendered"))));
                                    postExcerpt[i] = (String) String.valueOf(Html.fromHtml(String.valueOf(mapExcerptj.get("rendered"))));
                                    Log.d("L1", "Home: postTitle: "+postTitle[i]);


                                    if (jsonObj.getString("format").equals("quote")) {
                                        Log.d("L1", "Home: Quote");
                                        JSONObject mapContent = jsonObj.getJSONObject("content");
                                        //Log.d("L1", "Quote Content");
                                        quotecontent = (String) String.valueOf(Html.fromHtml(String.valueOf(mapContent.get("rendered"))));
                                        //Log.d("L1", "Quote Content: "+quotecontent);
                                        exampleList.add(new ExampleItem(null, postExcerpt[i], quotecontent));
                                    }else{
                                        JSONObject map_embeddedj = jsonObj.getJSONObject("_embedded");
                                        Log.d("L1", "Home: _embedded.");
                                        JSONArray mapWpfeaturedmediaj = map_embeddedj.getJSONArray("wp:featuredmedia");
                                        Log.d("L1", "Home: wp:embbeddedmedia: " + mapWpfeaturedmediaj);
                                        JSONObject mapSerialnumberj = mapWpfeaturedmediaj.getJSONObject(0);
                                        Log.d("L1", "Home: 0: " + mapSerialnumberj);
                                        JSONObject mapMediaDetails = mapSerialnumberj.getJSONObject("media_details");
                                        Log.d("L1", "Home: mapMediaDetails: " + mapMediaDetails);
                                        JSONObject mapSizes = mapMediaDetails.getJSONObject("sizes");
                                        Log.d("L1", "Home: mapSizes: " + mapSizes);
                                        JSONObject mapMedium = mapSizes.getJSONObject("medium");
                                        Log.d("L1", "Home: mapMedium: " + mapMedium);
                                        MediaUrl[i] = (String) mapMedium.getString("source_url");
                                        Log.d("L1", "Home: MediaUrl: "+MediaUrl[i]);

                                        exampleList.add(new ExampleItem(MediaUrl[i], postTitle[i], postExcerpt[i]));
                                        Log.d("L1", "Home: exampleList");
                                    }
                                } catch (JSONException e) {
                                    exampleList.add(new ExampleItem(null, postTitle[i], postExcerpt[i]));
                                    Log.d("L1", "Home: Exception: Invalid JSON Object.");
                                }
                            }

                            mRecyclerView.setHasFixedSize(true);
                            mLayoutManager = new LinearLayoutManager(getContext());
                            mAdapter = new ExampleAdapter(exampleList, getContext());

                            mRecyclerView.setLayoutManager(mLayoutManager);
                            mRecyclerView.setAdapter(mAdapter);
                            v.findViewById(R.id.progressbarhome).setVisibility(View.GONE);
                            pullToRefresh.setRefreshing(false);
                            Log.d("L1", "Home: adapter called.");
                            mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {

                                    try {
                                        JSONObject jsonObj = responseglobal.getJSONObject(position);
                                        Toast.makeText(getContext(), postTitle[position], Toast.LENGTH_LONG).show();
                                        Log.d("L1", "Home: Clicked");

                                        b1.putString("JSONObject", jsonObj.toString());
                                        Intent mySuperIntent = new Intent(getContext(), Articleweb.class);
                                        mySuperIntent.putExtras(b1);
                                        startActivity(mySuperIntent);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Log.d("L1", "Home: Click Exception");
                                    }
                                }
                            });
                        } else {
                            Log.d("L1", "Home: No data");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("L1", "Home: Posts Load Failed");
                        v.findViewById(R.id.progressbarhome).setVisibility(View.GONE);
                        pullToRefresh.setRefreshing(false);
                        Snackbar.make(v, "No response. Server offline or check connection.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
        );
        arrReq.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                2,
                2));
        RequestQueue rQueue = Volley.newRequestQueue(getContext());
        rQueue.add(arrReq);
    }
}