package com.example.codersinlaw.fastorder;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends TabActivity {

    private String title;
    private BottomNavigationView bottomNavigationView;
    public static ArrayList<CartItem> cartItems = new ArrayList<>();
    public static ArrayList<CartItem> placeList = new ArrayList<>();
    public static ArrayList<String> spots = new ArrayList<>();
    public static ArrayList<JSONObject> spotsObjects = new ArrayList<>();

    public static final String STORAGE_NAME = "STORAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        new AsyncRequest().execute();

        title = "Dishes";
        setTitle(title);

        TabHost tabHost = getTabHost();

        TabHost.TabSpec tabSpec;

        tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setIndicator("Категории");
        tabSpec.setContent(new Intent(this, CategoryListActivity.class));
        tabHost.addTab(tabSpec);


        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("Корзина");
        tabSpec.setContent(new Intent(this, CartListActivity.class));
        tabHost.addTab(tabSpec);


        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setIndicator("Заказы");
        tabSpec.setContent(new Intent(this, OrderPayActivity.class));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag4");
        tabSpec.setIndicator("Карта");
        tabSpec.setContent(new Intent(this, MapsActivity.class));
        tabHost.addTab(tabSpec);

        SharedPreferences sp = this.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
        /*SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isLogined", false);
        editor.commit();*/

        boolean logined = sp.getBoolean("isLogined", false);

        if(!logined) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }

    class AsyncRequest extends AsyncTask<Void, Void, ArrayList<JSONObject> > {

        @Override
        protected ArrayList<JSONObject> doInBackground(Void... voids) {
            ArrayList<JSONObject> aj = new ArrayList<>();
            try {
                String link = Handler.createLink("access.getSpots");
                String content = Handler.sendRequest(link, "GET");

                JSONObject obj = new JSONObject(content);
                JSONArray arr = obj.getJSONArray("response");
                for(int i = 0; i < arr.length(); ++i) {
                    String id = (String)arr.getJSONObject(i).get("spot_id");
                    String name = (String)arr.getJSONObject(i).get("spot_name");
                    String address = (String) arr.getJSONObject(i).get("spot_adress");

                    String helper = address.replace(" ", "+");
                    link = "https://maps.google.com/maps/api/geocode/json?address="+helper+"&key=AIzaSyDjgfR1P5MpP8BUoFvJcrqTA_1xBJ-TVhE";
                    JSONArray res = new JSONObject(Handler.sendRequest(link, "GET")).getJSONArray("results");
                    JSONObject loc = res.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                    String lng = loc.getString("lng");
                    String lat = loc.getString("lat");

                    JSONObject o = new JSONObject();
                    o.put("id", id);
                    o.put("name", name);
                    o.put("address", address);
                    o.put("lng", lng);
                    o.put("lat", lat);
                    aj.add(o);
                }
            } catch (JSONException e) {
                System.out.println(e);
            }

            return aj;
        }

        @Override
        protected void onPostExecute(ArrayList<JSONObject> aj) {
            spotsObjects = aj;
            for (int i = 0; i < aj.size(); ++i) {
                try {
                    spots.add(aj.get(i).get("name") + " " + aj.get(i).get("address"));
                } catch (JSONException e) {
                    System.out.println(e);
                }
            }
        }
    }

}
