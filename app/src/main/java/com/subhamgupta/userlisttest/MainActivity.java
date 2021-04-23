package com.subhamgupta.userlisttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import android.view.Window;
import android.view.WindowManager;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements OnItemClick {

    RecyclerView recyclerView, imagerecycler, articlerecycler;


    List<String> pageNo, category, page;
    static String imageUrl = "https://commons.wikimedia.org/w/api.php?action=query&prop=imageinfo&iiprop=timestamp|user|url&generator=categorymembers&gcmtype=file&gcmtitle=Category:Featured_pictures_on_Wikimedia_Commons&format=json&utf8";
    static String allCategoryUrl = "https://en.wikipedia.org/w/api.php?action=query&list=allcategories&acprefix=List+of&formatversion=2&format=json";
    static String articleurl = "https://en.wikipedia.org/w/api.php?format=json&action=query&generator=random&grnnamespace=0&prop=revisions|images&rvprop=content&grnlimit=10";

    private RequestQueue requestQueue;
    ArrayList<String> urls;
    HashMap<String, String> titleurl;
    List<String> title = new ArrayList<>();
    List<String> arts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        //recyclerview for all the categories
        recyclerView = findViewById(R.id.allcategory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //recyclerview for all the articles
        articlerecycler = findViewById(R.id.articleview);
        articlerecycler.setHasFixedSize(false);
        //articlerecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        //recycler  view for all categories
        imagerecycler = findViewById(R.id.imagess);
        imagerecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        images();
        AllCategories();
        Article();




    }


    public void AllCategories(){
        category = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, allCategoryUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.e("received ",response.toString());
                        try {

                            JSONObject jsonObject = (JSONObject) response.getJSONObject("query");
                            JSONArray object = jsonObject.getJSONArray("allcategories");

                            for (int i =0;i<object.length();i++){
                                category.add(object.getJSONObject(i).getString("category"));
                                //Log.e("category",object.getJSONObject(i).getString("category"));
                            }
                            //Log.e("pages",jsonObject.get("allcategories").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        recyclerView.setAdapter(new AllCategories(category));
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("err",error.getMessage());

                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
    public void images(){
        pageNo = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, imageUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.e("received ",response.toString());

                        try {
                            JsonParser parser = new JsonParser();
                            JSONObject jsonObject = (JSONObject) response.get("query");

                            JsonElement element = parser.parse(jsonObject.getString("pages"));
                            JsonObject object = element.getAsJsonObject();
                            Set<Map.Entry<String, JsonElement>> entries = object.entrySet();
                            for(Map.Entry<String, JsonElement> entry: entries) {
                                System.out.println(entry.getKey());
                                pageNo.add(entry.getKey());
                            }
                            titleurl = new HashMap<>();

                            //Log.e("pages",jsonObject.get("pages").toString());
                            for (int i =0;i<pageNo.size();i++){
                                JSONObject object1 = jsonObject.getJSONObject("pages").getJSONObject(pageNo.get(i));
                                JSONArray jsonObject1 = object1.getJSONArray("imageinfo");
                                urls = new ArrayList<>();
                                for (int j = 0; j<jsonObject1.length();j++){
                                    JSONObject jsonObject2 = jsonObject1.getJSONObject(j);
                                    String u = jsonObject2.getString("url");
                                    //Log.e("url",u);
                                    urls.add(u);
                                    String tit = object1.getString("title").substring(5);
                                    String ti = tit.substring(0,tit.length()-4);
                                    titleurl.put(u, ti);
                                }

                                //JSONObject  jsonObject2 = jsonObject1.getJSONObject("")
                            }
                            setImages(titleurl);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("err",error.getMessage());

                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
    public void setImages(HashMap<String, String> titleurl){
        List<String> url = new ArrayList<>(titleurl.keySet());
        List<String> title = new ArrayList<>(titleurl.values());
        /*for (int i = 0;i<url.size();i++){

            //Log.e("url",url.get(i));
            //Log.e("title",title.get(i));
        }*/
        imagerecycler.setAdapter(new ImageAdapter(url, title));

    }

    public void Article(){
        page = new ArrayList<>();
        List<Details> articles = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, articleurl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.e("received ",response.toString());

                        try {
                            JsonParser parser = new JsonParser();
                            JSONObject jsonObject = (JSONObject) response.get("query");
                            //Log.e("respo", String.valueOf(jsonObject.getJSONObject("pages")));
                            JsonElement element = parser.parse(jsonObject.getString("pages"));
                            JsonObject object = element.getAsJsonObject();
                            Set<Map.Entry<String, JsonElement>> entries = object.entrySet();
                            for(Map.Entry<String, JsonElement> entry: entries) {
                                System.out.println(entry.getKey());
                                page.add(entry.getKey());

                            }

                            //Log.e("pages",jsonObject.get("pages").toString());
                            for (int i =0;i<page.size();i++){
                                JSONObject object1 = jsonObject.getJSONObject("pages").getJSONObject(page.get(i));//page.get(i)=2266111
                                JSONArray jsonObject1 = object1.getJSONArray("revisions");
                                String ti = object1.getString("title");
                                title.add(ti);
                                urls = new ArrayList<>();
                                for (int j = 0; j<jsonObject1.length();j++){
                                    JSONObject jsonObject2 = jsonObject1.getJSONObject(j);
                                    String art = jsonObject2.getString("*");
                                    arts.add(art);
                                    //Log.e("revesions",art);
                                }  //JSONObject  jsonObject2 = jsonObject1.getJSONObject("")
                            }
                            for (int i = 0;i<arts.size();i++){
                                articles.add(new Details(title.get(i),arts.get(i)));
                            }
                            articlerecycler.setAdapter(new MyAdapter(articles, MainActivity.this));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("err",error.getMessage());

                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onClickItem(int position) {
        Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
        intent.putExtra("title",title.get(position));
        intent.putExtra("article", arts.get(position));
        Log.e("title",title.get(position));
        Log.e("article",arts.get(position));
        startActivity(intent);
    }

    @Override
    public void onLongClickItem(int position) {

    }
}