package com.example.codersinlaw.fastorder;

import android.app.TabActivity;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends TabActivity {

    private String title;
    private BottomNavigationView bottomNavigationView;
    public static ArrayList<CartItem> cartItems = new ArrayList<>();
    public static ArrayList<CartItem> placeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
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
        tabSpec.setContent(new Intent(this, CategoryListActivity.class));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag4");
        tabSpec.setIndicator("Карта");
        tabSpec.setContent(new Intent(this, MapsActivity.class));
        tabHost.addTab(tabSpec);
    }


}
