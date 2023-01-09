package com.example.comp2100groupproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.TextView;

import android.widget.Switch;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class resultTicket extends AppCompatActivity {
    private FirebaseDatabase querydata;
    private DatabaseReference query_data;
    ListView listview;
    Button result_go;
    EditText result_ed;
    String queryInput;
    ArrayAdapter aa;
    ArrayList<Airplane> airplanes = new ArrayList<>();

    //List<Query> queries;

    Switch low;
    ArrayList<Airplane> air = new ArrayList<>();
    ArrayList<Airplane> price_air = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_ticket);


        querydata = FirebaseDatabase.getInstance();
        query_data = querydata.getReference("query");

        low = (Switch) findViewById(R.id.LowToHigh);
        low.setOnCheckedChangeListener(switch_change);


        result_go = (Button) findViewById(R.id.result_go);
        result_go.setOnClickListener(result_go_listener);

        Intent intent = getIntent();
        String query = intent.getStringExtra("RevisedQuery");
        String inputQuery = intent.getStringExtra("QUERY");
        String Check = "false";
        String Checknull = intent.getStringExtra("Check");
        if (Checknull != null){
            Check = Checknull;
        }
        Tokenizer tokenizer = new Tokenizer(query);
        Query query_real = new Query();


        Exp exp = null;
        try {
            exp = new Parser(tokenizer).parseExp();
        } catch (Exception e) {
            e.printStackTrace();
        }


        query_real = exp.evaluate();

        air = getairlplanes(query);
        for (Airplane a : air){
            airplanes.add(a);
        }


        airplanes = getairlplanes(query);

        price_air = byPrice(air);


        EditText result_query = (EditText) findViewById(R.id.result_query);
        result_query.setText(inputQuery);

        listview = (ListView) findViewById(R.id.result_lw);
        aa = new ArrayAdapter(this,android.R.layout.simple_list_item_1,airplanes);
        listview.setAdapter(aa);

        listview.setOnItemClickListener(listview_click);

        TextView correct = (TextView) findViewById(R.id.correctQuery);

        correct.setMovementMethod(ScrollingMovementMethod.getInstance());
        String corrected = query.replace(";", "");

        String input = inputQuery.replace(";", "");
        corrected = corrected.replace(" ", "");
        input = input.replace(" ", "");
        if (!corrected.equalsIgnoreCase(input) && Check.equalsIgnoreCase("false")){
            corrected = "Do you mean \"" + query + "\"?";
            correct.setText(corrected);
            Spannable span = new SpannableString(correct.getText());
            span.setSpan(new ForegroundColorSpan(Color.RED), 0, 11, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            span.setSpan(new ForegroundColorSpan(Color.RED), corrected.length() - 1, corrected.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            span.setSpan(new AbsoluteSizeSpan(45), 11, corrected.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            correct.setText(span);
        }
        if (!corrected.equalsIgnoreCase(input)&& !Check.equalsIgnoreCase("false") ){
            String text = "use the preference history to search result. ";
            corrected = "Do you mean \"" + query + "\"?";
            corrected = text +corrected;
            correct.setText(corrected);
            Spannable span = new SpannableString(correct.getText());
            span.setSpan(new ForegroundColorSpan(Color.RED), 43, 54, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            span.setSpan(new ForegroundColorSpan(Color.RED), corrected.length() - 1, corrected.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            span.setSpan(new AbsoluteSizeSpan(45), 11, corrected.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            correct.setText(span);
        }
        else if (Check.equalsIgnoreCase("true")){
            String text = "use the preference history to search result";
            correct.setText(text);
        }
    }







    private AdapterView.OnItemClickListener listview_click = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getApplicationContext(),ticketDetail.class);
            intent.putExtra("Departure", airplanes.get(position).departure);
            intent.putExtra("Destination", airplanes.get(position).destination);
            intent.putExtra("Time", airplanes.get(position).time.toString());
            intent.putExtra("Available", Integer.toString(airplanes.get(position).available));
            intent.putExtra("Price", Integer.toString(airplanes.get(position).price));
            intent.putExtra("AirplaneType",airplanes.get(position).type);
            startActivity(intent);
        }
    };

    private CompoundButton.OnCheckedChangeListener switch_change = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if (isChecked){

                airplanes.clear();
                airplanes.addAll(price_air);

            }else {

                airplanes.clear();
                airplanes.addAll(air);

            }

            listview.setAdapter(aa);

            aa.notifyDataSetChanged();

            listview.setOnItemClickListener(listview_click);
        }
    };

    public ArrayList<Airplane> byPrice (ArrayList<Airplane> airs){

        ArrayList<Airplane> airs1 = (ArrayList<Airplane>)airs.clone();

        int len = airs1.size();
        Airplane temp;

        for (int i = 0; i<len; i++){
            for (int j = 1; j < (len - i); j++) {

                if (airs1.get(j - 1).price > airs1.get(j).price) {

                    temp = airs1.get(j - 1);
                    airs1.set(j-1,airs1.get(j));
                    airs1.set(j,temp);

                }
            }
        }

        return airs1;
    }

    private View.OnClickListener result_go_listener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onClick(View v) {
            result_ed = (EditText) findViewById(R.id.result_query);
            queryInput = result_ed.getText().toString();
            try {
                Tokenizer tokenizer = new Tokenizer(queryInput);
                Query query_real = new Query();
                Exp exp = new Parser(tokenizer).parseExp();
                query_real = exp.evaluate();
                String test1 = query_real.getDeparture();
                String test2 = query_real.getDestination();
                String test3 = query_real.getDepartureTime();
                String test4 = query_real.getNoTicket();
                String test5 = query_real.getPrice();
                if (test1 == null && test2 == null && test3 == null && test4 == null && test5 == null) {
                    Toast.makeText(resultTicket.this, "query cant be empty.",
                            Toast.LENGTH_SHORT).show();
                } else {

                    //writequery(query_real);

                    Intent intent = new Intent(getApplicationContext(), resultTicket.class);
                    intent.putExtra("QUERY", queryInput);
                    intent.putExtra("RevisedQuery", exp.getQuery());
                    startActivity(intent);
                }
            } catch (Exception e) {
                Context c = getApplicationContext();
                CharSequence text = "incorrect search message";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(c, text, duration);
                toast.show();

                airplanes.clear();
                aa.notifyDataSetChanged();
                listview.setAdapter(aa);
            }

        }
    };

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ArrayList<Airplane> getairlplanes (String query) {

        Tokenizer tokenizer = new Tokenizer(query);
        Query query_real = new Query();
        Exp exp = null;
        try {
            exp = new Parser(tokenizer).parseExp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        query_real = exp.evaluate();

        File bespokefile = new File("airplanes_data.bin");
        File jsonfile = new File ("airplanes_data.json");
        Airplane_Data data = loadFromBespokeFile(bespokefile);
        List<Airplane> list1 = Airplane_Data.getList(data);
        Airplane_Data data2 = loadFromJSONFile(jsonfile);
        List<Airplane> list2 = Airplane_Data.getList(data2);
        list1.addAll(list2);

        ArrayList<Airplane> airplanes = new ArrayList<>();
        airplanes.addAll(list1);


        // try create tree use departure
        if (query_real.departure != null) {
            BST b1 = new BST();
            for (Airplane a : list1) {
                b1 = BST.insertbydepa(a,b1);
            }
            airplanes = BST.finddepa(query_real.departure.substring(1).toLowerCase(),b1);
        }



        // try create tree use destiantion
        if (query_real.destination != null) {
            BST b2 = new BST();
            for (Airplane a : airplanes) {
                b2 = BST.insertbydest(a,b2);
            }
            airplanes = BST.finddest(query_real.destination.substring(1).toLowerCase(),b2);
        }

        // try create tree use departuretime
        if (query_real.departureTime != null) {
            BST b3 = new BST();
            for (Airplane a : airplanes) {
                b3 = BST.insertbytime(a,b3);
            }
            airplanes = BST.findtime(Time.fromString_short(query_real.departureTime.substring(1)),b3);
        }

        // try create tree use No_ticket
        if (query_real.NoTicket != null) {
            BST b4 = new BST();
            for (Airplane a : airplanes) {
                b4 = BST.insertbyavail(a,b4);
            }
            airplanes = BST.findavail(Integer.parseInt(query_real.NoTicket.substring(1)),b4);
        }


        // try create tree use price
        if (query_real.price != null) {
            BST b5 = new BST();
            for (Airplane a : airplanes) {
                b5 = BST.insertbyprice(a,b5);
            }

            //distinguish different expression
            if (query_real.price.charAt(0) == '=') {
                airplanes = BST.findprice_equal(Integer.parseInt(query_real.price.substring(1)),b5);
            }
            if (query_real.price.charAt(0) == '>' && query_real.price.charAt(1) != '=') {
                airplanes = BST.findprice_more(Integer.parseInt(query_real.price.substring(1)),b5);
            }
            if (query_real.price.charAt(0) == '>' && query_real.price.charAt(1) == '=') {
                airplanes = BST.findprice_more_equal(Integer.parseInt(query_real.price.substring(2)),b5);
            }
            if (query_real.price.charAt(0) == '<' && query_real.price.charAt(1) != '=') {
                airplanes = BST.findprice_less(Integer.parseInt(query_real.price.substring(1)),b5);
            }
            if (query_real.price.charAt(0) == '<' && query_real.price.charAt(1) == '=') {
                airplanes = BST.findprice_less_equal(Integer.parseInt(query_real.price.substring(2)),b5);
            }

        }

        return airplanes;
    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Airplane_Data loadFromBespokeFile(File file) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("airplanes_data.bin"), "UTF-8"))) {
            String record;
            String delimiter = "#";

            List<Airplane> airplanes = new ArrayList<Airplane>();

            while ((record = br.readLine()) != null) {
                String[] tokens = record.split(delimiter);
                String type = tokens[0];
                int price = Integer.parseInt(tokens[1]);
                Time time = Time.fromString(tokens[2]);
                String destination = tokens[3];
                String departure = tokens[4];
                int available = Integer.parseInt(tokens[5]);
                Airplane a = new Airplane(type, price, time, destination, departure, available);
                airplanes.add(a);
            }

            return new Airplane_Data(airplanes);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    public Airplane_Data loadFromJSONFile(File file) {
        Gson gson = new Gson();
        JsonReader jsonReader = null;

        final Type CUS_LIST_TYPE = new TypeToken<List<Airplane>>() {}.getType();

        try{
            jsonReader = new JsonReader(new InputStreamReader(getAssets().open("airplanes_data.json"), "UTF-8"));
        }catch (Exception e) {
            e.printStackTrace();
        }

        return new Airplane_Data((List<Airplane>) gson.fromJson(jsonReader, CUS_LIST_TYPE));
    }

}
