package com.example.codersinlaw.fastorder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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

public class DishListActivity extends AppCompatActivity {
    private int category;

    private RecyclerView recView;
    private LinearLayoutManager manager;
    private RecyclerAdapter adapter;
    private Intent intent;
    private String title;
    private Context context;
    private BottomNavigationView bottomNavigationView;
    private ItemTouchHelper.SimpleCallback simpleCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

          requestWindowFeature(Window.FEATURE_NO_TITLE);
          getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                  WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_dish);
       /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbara);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); */
        intent = this.getIntent();
        setTitle(intent.getStringExtra("category_name"));

        recView = findViewById(R.id.dishRecView);

        /*recView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && bottomNavigationView.isShown()) {
                    bottomNavigationView.setVisibility(View.GONE);
                } else if (dy < 0 ) {
                    bottomNavigationView.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
            }
        }); */
        context = this;
        manager = new LinearLayoutManager(context);
        System.out.println("MANAGER = " + manager);
        System.out.println("RECVIEW = " + recView);

        recView.setLayoutManager(manager);
        adapter = new RecyclerAdapter();
        recView.setAdapter(adapter);
        //adapter.addAll(getItems());
        new AsyncRequest().execute();

        simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition(); //get position which is swipe

                if (direction == ItemTouchHelper.LEFT) {    //if swipe left
                    adapter.notifyItemChanged(position, null);
                    MainActivity.cartItems.add(new CartItem(adapter.items.get(position)));
                    Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recView); //set swipe to recylcerview

        Slidr.attach(this, getResources().getColor(R.color.primaryDark), getResources().getColor(R.color.secondaryDark));
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

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
        ArrayList<DishItem> items = new ArrayList<>();

        public void addAll(List<DishItem> items) {
            int pos = getItemCount();
            this.items.addAll(items);
            notifyItemRangeInserted(pos, this.items.size());
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dishitem, parent, false);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, final int position) {
            final DishItem dishItem = items.get(position);

            holder.bind(dishItem);

            holder.itemView.setOnClickListener (new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean expanded = dishItem.isExpanded();
                    dishItem.setExpanded(!expanded);
                    notifyItemChanged(position);
                    dishItem.setFullName(!expanded);
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView title, price, description;
        private ImageView image;
        private View subItem;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            subItem = itemView.findViewById(R.id.sub_item);

            title = (TextView) itemView.findViewById(R.id.title);
            price = (TextView) itemView.findViewById(R.id.price);
            description = (TextView) itemView.findViewById(R.id.description);
            title.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf"));
            price.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf"));
            description.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf"));

            image = (ImageView) itemView.findViewById(R.id.imgD);

            //image.setImageResource(R.drawable.no_img); //
        }

        public void bind(final DishItem recyclerItem) {
            boolean expanded = recyclerItem.isExpanded();

            title.setText(recyclerItem.getName());
            price.setVisibility(View.INVISIBLE);
            description.setText(recyclerItem.getDescription());
            // TODO добавить текст button.setText();
            Picasso.with(context).load(recyclerItem.getURL()).into(image);

            subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);
        }
    }

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
    }
}
