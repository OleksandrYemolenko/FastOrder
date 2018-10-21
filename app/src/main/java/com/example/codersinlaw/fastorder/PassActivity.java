package com.example.codersinlaw.fastorder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import static com.example.codersinlaw.fastorder.MainActivity.STORAGE_NAME;

public class PassActivity extends AppCompatActivity {

    private String title;
    private Button btn;
    private Intent intent;
    public static String phone = "+380000000000";

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_pass);
        title = "Authentication";
        setTitle(title);

        btn = (Button) findViewById(R.id.btn_pass);
        context = this;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();

                editor.putBoolean("isLogined", true);
                editor.putString("phone", phone);

                editor.commit();

                intent = new Intent(PassActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
