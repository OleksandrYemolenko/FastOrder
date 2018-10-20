package com.example.codersinlaw.fastorder;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    /*class AsyncRequest extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(Handler.createLink("menu.getCategories"));
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String content = "", line = "";
                while((line = bf.readLine()) != null) {
                    content += line;
                }

                con.disconnect();
                return content;
            } catch (MalformedURLException e) {
                System.out.println(e);
            } catch (IOException e) {
                System.out.println(e);
            }

            return "error";
        }

        @Override
        protected void onPostExecute(String s) {
            ArrayList<CategoryItem> items = new ArrayList<>();
            try {
                //System.out.println("REQUEST   ==== " + s);
                JSONObject obj = new JSONObject(s);
                JSONArray arr = obj.getJSONArray("response");
                for(int i = 0; i < arr.length(); ++i) {
                    String name = (String)arr.getJSONObject(i).get("category_name");
                    String photo = (String)arr.getJSONObject(i).get("category_photo");
                    int id = Integer.parseInt((String)arr.getJSONObject(i).get("category_id"));
                    items.add(new CategoryItem(name, Handler.link + photo, id));
                }
            } catch (JSONException e) {
                System.out.println(e);
            }

            adapter.addAll(items);
        }
    }*/
}
