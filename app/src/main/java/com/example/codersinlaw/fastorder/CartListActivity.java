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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

public class CartListActivity extends AppCompatActivity {
    private RecyclerView recView;
    private LinearLayoutManager manager;
    private CartListActivity.RecyclerAdapter adapter;
    private Intent intent;
    private Context context;

    private ArrayList<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.cartItems = MainActivity.cartItems;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_cart);

        recView = findViewById(R.id.cartRecView);

        context = this;
        intent = this.getIntent();

        manager = new LinearLayoutManager(context);

        recView.setLayoutManager(manager);
        adapter = new CartListActivity.RecyclerAdapter();
        recView.setAdapter(adapter);
        adapter.addAll(getItems());
    }

    private List<CartItem> getItems() {
        /*ArrayList<CartItem> items = new ArrayList<>();

        try {
            JSONArray arr = new JSONArray(intent.getStringExtra("cart"));
            for(int i = 0; i < arr.length(); ++i) {
                String name = (String)arr.getJSONObject(i).get("product_name");
                String photo = (String)arr.getJSONObject(i).get("photo_origin");
                int id = Integer.parseInt((String)arr.getJSONObject(i).get("product_id"));
                items.add(new CartItem(name, photo, id));
            }
        } catch (JSONException e) {
            System.out.println(e);
        }*/

        return cartItems;
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<CartListActivity.RecyclerViewHolder> {
        ArrayList<CartItem> items = new ArrayList<>();

        public void addAll(List<CartItem> items) {
            int pos = getItemCount();
            this.items.addAll(items);
            notifyItemRangeInserted(pos, this.items.size());
        }

        @NonNull
        @Override
        public CartListActivity.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dishitem, parent, false);
            return new CartListActivity.RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CartListActivity.RecyclerViewHolder holder, final int position) {
            final CartItem item = items.get(position);

            holder.bind(item);

            holder.itemView.setOnClickListener (new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean expanded = item.isExpanded();
                    item.setExpanded(!expanded);
                    notifyItemChanged(position);
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
        private ImageView image;
        private View subItem;
        private Button button;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            subItem = itemView.findViewById(R.id.sub_item);
            title = (TextView) itemView.findViewById(R.id.title);
            price = (TextView) itemView.findViewById(R.id.price);
            button = (Button) itemView.findViewById(R.id.seeAll);
            button.setText("Delete");
            button.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf"));
            title.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf"));
            price.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf"));
            image = (ImageView) itemView.findViewById(R.id.imgD);
            //image.setImageResource(R.drawable.no_img); //
        }

        public void bind(CartItem recyclerItem) {
            boolean expanded = recyclerItem.isExpanded();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO Передавать объекты
                }
            });
            title.setText(recyclerItem.getName());
            price.setVisibility(View.INVISIBLE);
            Picasso.with(context).load(recyclerItem.getURL()).into(image);

            subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);
        }
    }
}
