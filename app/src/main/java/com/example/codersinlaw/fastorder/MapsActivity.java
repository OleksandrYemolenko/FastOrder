package com.example.codersinlaw.fastorder;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        new AsyncRequest().execute();
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
            for (int i = 0; i < aj.size(); ++i) {
                try {
                    JSONObject obj = aj.get(i);
                    Double lat = Double.parseDouble(obj.get("lat").toString());
                    Double lng = Double.parseDouble(obj.get("lng").toString());
                    LatLng sydney = new LatLng(lat, lng);
                    mMap.addMarker(new MarkerOptions().position(sydney).title(obj.get("name") + " " + obj.get("address")));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));

                    System.out.println(obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
