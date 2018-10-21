package com.example.codersinlaw.fastorder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;

import static com.example.codersinlaw.fastorder.MainActivity.STORAGE_NAME;

public class PassActivity extends AppCompatActivity {

    private String title;
    private Button btn;
    private Intent intent;
    public static String phone = "+380676988515", firstname = "Oleg", secondname = "Fomenko";

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

                new AsyncRequest().execute();

                intent = new Intent(PassActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    class AsyncRequest extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... voids) {
            String content = Handler.sendRequest(Handler.createLink("clients.getGroups"), "GET");
            System.out.println("CONTENT ====== " + content);
            JSONObject obj = null;
            try {
                obj = new JSONObject(content);
                JSONArray arr = obj.getJSONArray("response");
                obj = arr.getJSONObject(0);
                System.out.println(obj);
                content = Handler.sendRequest(Handler.createLink("clients.createClient", "client_name=" + secondname, "phone=" + phone, "client_groups_id_client="+obj.get("client_groups_id"), "client_sex=0"), "post");
                obj = new JSONObject(content);
            } catch (JSONException e) {
                System.out.println(e);
            }

            return obj;
        }

        @Override
        protected void onPostExecute(JSONObject obj) {
            SharedPreferences sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            System.out.println(obj);
            try {
                editor.putString("id", obj.get("response").toString());
                System.out.println(sp.getString("id", "Errrorrrr"));
            } catch (JSONException e) {
                editor.putString("id", "0");
                e.printStackTrace();
            }



            editor.commit();
        }
    }
}
