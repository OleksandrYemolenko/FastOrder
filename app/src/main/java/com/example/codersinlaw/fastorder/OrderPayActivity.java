package com.example.codersinlaw.fastorder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.r0adkll.slidr.Slidr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class OrderPayActivity extends AppCompatActivity {

    private TextView price, time;
    public static Spinner spinner, spinnerO;

    private String data[] = {"Out of restaurant", "In restaurant"};
    private String spots[];
    public static int type_id = 0, spot_index = 0;
    public static HashMap<Integer, Integer> ids = new HashMap<>();
    public static HashMap<String, Integer> indexes = new HashMap<>();

    private Context context;

    private Button map, timeD;
    int pri;
    private static final int Time_id = 1;
    private static final int Date_id = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_order);

        context = this;
        spots = new String[MainActivity.spots.size()];
        MainActivity.spots.toArray(spots);
        ArrayList<JSONObject> aj = MainActivity.spotsObjects;
        for (int i = 0; i < aj.size(); ++i) {
            try {
                ids.put(i, Integer.parseInt(aj.get(i).get("id").toString()));
                indexes.put(aj.get(i).get("name").toString(), i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Slidr.attach(this);

        pri = getIntent().getIntExtra("price", 0);

        bind();
    }

    public void bind() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = (Spinner) findViewById(R.id.spinRes);
        spinner.setAdapter(adapter);

        spinner.setPrompt("Place");

        spinner.setSelection(type_id);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                //Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapterO = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spots);
        adapterO.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerO = (Spinner) findViewById(R.id.spinResO);
        spinnerO.setAdapter(adapterO);

        spinnerO.setPrompt("Place");

        spinnerO.setSelection(spot_index);

        spinnerO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                //Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        price = (TextView) findViewById(R.id.priceO);
        time = (TextView) findViewById(R.id.timeO);
        map = (Button) findViewById(R.id.mapB);
        timeD = (Button) findViewById(R.id.timeD);
        price.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf"));
        time.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf"));
        map.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf"));
        timeD.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf"));
        timeD.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Show time dialog
                showDialog(Time_id);
            }
        });
        map.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(context, MapsActivity.class);
                startActivity(i);
            }
        });
    }
    protected Dialog onCreateDialog(int id) {

        // Get the calander
        Calendar c = Calendar.getInstance();

        // From calander get the year, month, day, hour, minute
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        switch (id) {
            case Date_id:

                // Open the datepicker dialog
                return new DatePickerDialog(OrderPayActivity.this, date_listener, year,
                        month, day);
            case Time_id:

                // Open the timepicker dialog
                return new TimePickerDialog(OrderPayActivity.this, time_listener, hour,
                        minute, false);

        }
        return null;
    }

    // Date picker dialog
    DatePickerDialog.OnDateSetListener date_listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // store the data in one string and set it to text
            String date1 = String.valueOf(month) + "/" + String.valueOf(day)
                    + "/" + String.valueOf(year);
            time.setText(date1);
        }
    };
    TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            // store the data in one string and set it to text
            String time1 = String.valueOf(hour) + ":" + String.valueOf(minute);
            time.setText(time1);
        }
    };


}
