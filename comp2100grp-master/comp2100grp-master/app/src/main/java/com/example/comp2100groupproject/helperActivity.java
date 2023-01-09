package com.example.comp2100groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class helperActivity extends AppCompatActivity {
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);

        Intent intent = getIntent();

        back = findViewById(R.id.helper_back);
        back.setOnClickListener(back_Listener);
        ListView listView = (ListView)findViewById(R.id.helper_tv);
        String help1 = "1. You query can contain following information: ";
        String help2 = "   From: where is your departure country?";
        String help3 = "   To: where is your destination country?";
        String help4 = "   Time: when do you expect to travel?";
        String help5 = "   Price: what the price you expect?";
        String help6 = "   NoTicket: how many tickets you would like to buy?";
        String help7 = "2. You can contain any one of the information in your query.";
        String help8 = "3. The format is \"From = Sydney; To = Canberra; Time = 2020/11/20; Price = 2000; NoTicket = 2;\"";
        String help9 = "4. The order of the information is not important";
        String help10 = "5. Use \";\" to separate requirements in the query";

        List<String> help = new ArrayList<>();
        help.add(help1);help.add(help2);help.add(help3);help.add(help4);help.add(help5);help.add(help6);
        help.add(help7);help.add(help8);help.add(help9);help.add(help10);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1, help);
        listView.setAdapter(aa);


    }
    private View.OnClickListener back_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    };
}