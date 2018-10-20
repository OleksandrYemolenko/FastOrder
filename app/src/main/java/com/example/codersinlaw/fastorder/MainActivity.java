package com.example.codersinlaw.fastorder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String title;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        title = "Dishes";

        /*bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_favorites:
                        Toast.makeText(MainActivity.this, "action_favorites", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        }); */

        setTitle(title);
    }

    public void ChangeActivity(View view) {
        try {
            Intent intent = new Intent(this, CategoryListActivity.class);
            startActivity(intent);
        } catch(Exception e) {
            //Toast.makeText(getApplicationContext(), "Exception" + e, Toast.LENGTH_LONG).show();
            System.out.println(e);
        }
        // return;
    }
}
