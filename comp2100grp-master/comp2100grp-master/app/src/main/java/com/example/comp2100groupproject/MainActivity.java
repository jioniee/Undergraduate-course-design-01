package com.example.comp2100groupproject;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText query;
    Button go;
    String queryInput;
    TextView helper;
    private FirebaseDatabase querydata;
    private DatabaseReference query_data;
    ArrayList<Query> queries = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        go = (Button) findViewById(R.id.main_button);
        go.setOnClickListener(go_Listener);

        helper = (TextView) findViewById(R.id.main_helper);
        helper.setOnClickListener(helper_Listener);

//        querydata = FirebaseDatabase.getInstance();
//        query_data = querydata.getReference("query");
//
//        loadquery();
    }


    private View.OnClickListener go_Listener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onClick(View view) {
            query = (EditText) findViewById(R.id.main_query);
            queryInput = query.getText().toString();

            // if invaid --> toast

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
                    Toast.makeText(MainActivity.this, "query cant be empty.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    String mod = exp.getQuery();
//                    writequery(query_real);
                    Boolean isFilled = false;

                    if (test1 == null) {
                        isFilled = true;
                        test1 = filldepature(queries);
                        test1 = "=" + test1;
                        queryInput = "from" + test1 + "; " + queryInput;
                         mod = "from" + test1 + "; " + exp.getQuery();
                    }

                    if (test2 == null) {
                        isFilled = true;
                        test2 = filldestination(queries);
                        test2 = "=" + test2;
                        queryInput = "to" + test2 + "; " + queryInput;
                        mod = "to" + test2 + "; " + exp.getQuery();
                    }

                    String check = "";
                    if (isFilled){
                        check = "true";
                    }
                    else{
                        check = "false";
                    }
                    Intent intent = new Intent(getApplicationContext(), resultTicket.class);
                    intent.putExtra("QUERY", queryInput);
                    intent.putExtra("RevisedQuery", mod);
                    intent.putExtra("Check", check);
                    startActivity(intent);
                }

            } catch (Exception e) {
                Context c = getApplicationContext();
                CharSequence text = e.getMessage();

                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(c, text, duration);
                toast.show();
            }
        }

    };

    public static String filldepature(ArrayList<Query> queries) {
        ArrayList<Integer> countdepa = new ArrayList<>();
        int countsydney = 0;
        int countcanberra = 0;
        int countmelbourne = 0;
        int countperth = 0;
        int countbrisbane = 0;
        for (Query q : queries) {
            if (q.departure != null) {
                if (q.departure.equalsIgnoreCase("sydney")) {
                    countsydney += 1;
                }
                if (q.departure.equalsIgnoreCase("canberra")) {
                    countcanberra += 1;
                }
                if (q.departure.equalsIgnoreCase("melbourne")) {
                    countmelbourne += 1;
                }
                if (q.departure.equalsIgnoreCase("perth")) {
                    countperth += 1;
                }
                if (q.departure.equalsIgnoreCase("brisbane")) {
                    countbrisbane += 1;
                }
            }
        }

        countdepa.add(countsydney);
        countdepa.add(countcanberra);
        countdepa.add(countmelbourne);
        countdepa.add(countperth);
        countdepa.add(countbrisbane);

        int countde = 0;
        for (int i : countdepa) {
            if (i > countde) {
                countde = i;
            }
        }

        if (countde == countsydney) {
            return "sydney";
        }
        if (countde == countcanberra) {
            return "canberra";
        }
        if (countde == countmelbourne) {
            return "melbourne";
        }
        if (countde == countperth) {
            return "perth";
        }
        if (countde == countbrisbane) {
            return "brisbane";
        }
        return null;
    }

        public static String filldestination(ArrayList<Query> queries) {
            ArrayList<Integer> countdest = new ArrayList<>();
            int countbj = 0;
            int countgz = 0;
            int countsh = 0;
            int countsz = 0;
            int countnc = 0;
            for (Query q : queries) {
                if (q.destination != null) {
                    if (q.destination.equalsIgnoreCase("beijing")) {
                        countbj += 1;
                    }
                    if (q.destination.equalsIgnoreCase("guangzhou")) {
                        countgz += 1;
                    }
                    if (q.destination.equalsIgnoreCase("shanghai")) {
                        countsh += 1;
                    }
                    if (q.destination.equalsIgnoreCase("shenzhen")) {
                        countsz += 1;
                    }
                    if (q.destination.equalsIgnoreCase("nanchang")) {
                        countnc += 1;
                    }
                }
            }

            countdest.add(countbj);
            countdest.add(countgz);
            countdest.add(countsh);
            countdest.add(countsz);
            countdest.add(countnc);

            int countdd = 0;
            for (int i : countdest) {
                if (i > countdd) {
                    countdd = i;
                }
            }

        if (countdd == countbj) {
            return "beijing";
        }
        if (countdd == countgz) {
            return "guangzhou";
        }
        if (countdd == countsh) {
            return "shanghai";
        }
        if (countdd == countsz) {
            return "shenzhen";
        }
        if (countdd == countnc) {
            return "nanchang";
        }
        return null;
    }


    private void writequery(Query query) {
        String key = query_data.push().getKey();
        query_data.child(key).setValue(query);
    }

    private void loadquery() {
        query_data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                queries.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keynode : snapshot.getChildren()) {
                    keys.add(keynode.getKey());
                    Query query = keynode.getValue(Query.class);
                    queries.add(query);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    private View.OnClickListener helper_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(getApplicationContext(), helperActivity.class);
            startActivity(intent);

        }


    };

    public void departNearbyButton(View v) {
        Intent intent = new Intent(MainActivity.this, DepartNearby.class);
        startActivity(intent);
    }


}