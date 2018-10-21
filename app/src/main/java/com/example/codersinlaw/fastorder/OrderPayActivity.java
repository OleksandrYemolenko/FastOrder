package com.example.codersinlaw.fastorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;

import org.w3c.dom.Text;

public class OrderPayActivity extends AppCompatActivity {

    private TextView price, time, address;
    private Button map, timeD;
    int pri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_order);

        Slidr.attach(this);

        pri = getIntent().getIntExtra("price", 0);

        bind();
    }

    public void bind() {
        price = (TextView) findViewById(R.id.priceO);
        time = (TextView) findViewById(R.id.timeO);
        address = (TextView) findViewById(R.id.addressO);
        map = (Button) findViewById(R.id.mapB);
        timeD = (Button) findViewById(R.id.timeD);
    }
}
