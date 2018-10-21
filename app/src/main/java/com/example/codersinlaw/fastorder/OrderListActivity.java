package com.example.codersinlaw.fastorder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;
import com.squareup.picasso.Picasso;

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
import java.util.List;

public class OrderListActivity extends AppCompatActivity {
    private int category;

    private RecyclerView recView;
    private LinearLayoutManager manager;
    private OrderListActivity.RecyclerAdapter adapter;
    private Intent intent;
    private String title;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_orderlist);

        recView = findViewById(R.id.orderRecView);

        context = this;
        manager = new LinearLayoutManager(context);
        System.out.println("MANAGER = " + manager);
        System.out.println("RECVIEW = " + recView);

        recView.setLayoutManager(manager);
        adapter = new OrderListActivity.RecyclerAdapter();
        recView.setAdapter(adapter);
        //adapter.addAll(getItems());
        //new AsyncRequest().execute();
    }


  /*  public void ChangeActivity(int pos, String title) {
        try {
            intent = new Intent(this, AlgoContentPageActivity.class);
            intent.putExtra("pos", pos);
            intent.putExtra("act", "a");
            intent.putExtra("title", title);
            startActivity(intent);
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), "Exception", Toast.LENGTH_LONG).show();
        }
    } */

    public class RecyclerAdapter extends RecyclerView.Adapter<OrderListActivity.RecyclerViewHolder> {
        ArrayList<OrderItem> items = new ArrayList<>();

        public void addAll(List<OrderItem> items) {
            int pos = getItemCount();
            this.items.addAll(items);
            notifyItemRangeInserted(pos, this.items.size());
        }

        @NonNull
        @Override
        public OrderListActivity.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderitem, parent, false);
            return new OrderListActivity.RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final OrderListActivity.RecyclerViewHolder holder, final int position) {
            final OrderItem orderItem = items.get(position);

            holder.bind(orderItem);

            holder.itemView.setOnClickListener (new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO переход в список в истории
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView title, price;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            price = (TextView) itemView.findViewById(R.id.price);
            title.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf"));
            price.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf"));
        }

        public void bind(final OrderItem recyclerItem) {
            title.setText(recyclerItem.getName());
            price.setText(Integer.toString(recyclerItem.getPrice()));
        }
    }

    /*

    class AsyncRequest extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                category = Integer.parseInt(intent.getStringExtra("category"));
                URL url = new URL(Handler.createLink("menu.getProducts", "category_id=" + category));
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
            //TODO заменить DishItem на OrderItem
            ArrayList<DishItem> items = new ArrayList<>();
            try {
                //System.out.println("REQUEST   ==== " + s);
                JSONObject obj = new JSONObject(s);
                JSONArray arr = obj.getJSONArray("response");
                //System.out.println(arr);
                for(int i = 0; i < arr.length(); ++i) {
                    String name = (String)arr.getJSONObject(i).get("product_name");
                    String photo = (String)arr.getJSONObject(i).get("photo_origin");
                    int price = Integer.parseInt((String)arr.getJSONObject(i).getJSONObject("price").get("1"));
                    String priceStr = Double.toString(price / 100.0) + "₴";
                    //System.out.println(price);
                    int id = Integer.parseInt((String)arr.getJSONObject(i).get("product_id"));

                    items.add(new DishItem(name, Handler.link + photo, id, priceStr, "Описание товара из чего состоит хз будет ли в релизе лалалала саша пидор"));
                }
            } catch (JSONException e) {
                System.out.println(e);
            }

            adapter.addAll(items);
        }
    } */
}
