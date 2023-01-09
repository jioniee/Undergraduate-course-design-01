package com.example.comp2100groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

public class ticketDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        Intent intent = getIntent();
        String Departure = intent.getStringExtra("Departure");
        String Destination = intent.getStringExtra("Destination");
        String Time = intent.getStringExtra("Time");
        String Available = intent.getStringExtra("Available");
        String Price = intent.getStringExtra("Price");
        String AirplaneType = intent.getStringExtra("AirplaneType");

        Time real_time = com.example.comp2100groupproject.Time.fromString(Time);
        int year = real_time.year;
        int month = real_time.month;
        int day = real_time.date;
        int hour = real_time.hour;
        int minute = real_time.minute;


        TextView text = (TextView) findViewById(R.id.price_tv);
        String pricetext = "price:" + Price;
        text.setText(pricetext);

        TextView text2 = (TextView) findViewById(R.id.departure);
        String pricetext2 = "from:" + Departure;
        text2.setText(pricetext2);

        TextView text3 = (TextView) findViewById(R.id.destination);
        String pricetext3 = "To:" + Destination;
        text3.setText(pricetext3);

        TextView text4 = (TextView) findViewById(R.id.time);

        String finalTime =  month + "/" + day + "/" + hour +":" +minute;
        String pricetext4 = "Time:" + finalTime;
        text4.setText(pricetext4);


        TextView text5 = (TextView) findViewById(R.id.available);
        String pricetext5 = "Available:" + Available;
        text5.setText(pricetext5);

        TextView text6 = (TextView) findViewById(R.id.airtype);
        String pricetext6 = AirplaneType;
        text6.setText(pricetext6);


        ImageView imageView = (ImageView) findViewById(R.id.AirplanePictures);
        if (AirplaneType.equalsIgnoreCase("a318")) {
            imageView.setImageResource(R.drawable.a318);
        }
        if (AirplaneType.equalsIgnoreCase("a319")) {
            imageView.setImageResource(R.drawable.a319);
        }
        if (AirplaneType.equalsIgnoreCase("a320")) {
            imageView.setImageResource(R.drawable.a320);
        }
        if (AirplaneType.equalsIgnoreCase("a321")) {
            imageView.setImageResource(R.drawable.a321);
        }

        ImageView hotel = (ImageView)findViewById(R.id.hotel);
        if (Destination.equalsIgnoreCase("beijing")){
            hotel.setImageResource(R.drawable.beijing);
        }
        if (Destination.equalsIgnoreCase("guangzhou")){
            hotel.setImageResource(R.drawable.guangzhou);
        }
        if (Destination.equalsIgnoreCase("nanchang")){
            hotel.setImageResource(R.drawable.nanchang);
        }
        if (Destination.equalsIgnoreCase("shenzhen")){
            hotel.setImageResource(R.drawable.shenzhen);
        }
        if (Destination.equalsIgnoreCase("shanghai")){
            hotel.setImageResource(R.drawable.shanghai);
        }

        TextView ad = (TextView)findViewById(R.id.ads);
        if (Destination.equalsIgnoreCase("beijing")){
            String ad_pk = "<a href='https://www.aman.com/zh-cn/resorts/aman-summer-palace?gclid=Cj0KCQjw8rT8BRCbARIsALWiOvRK8IErX3ujALvn9gVyoFKvm-WvFnG0SVkgyJtxHDKSkpWhp3au6B0aAho6EALw_wcB&gclsrc=aw.ds'>" +
                    "Beijing - AMAN hotel</a>";
            ad_pk += "(advertisement)";
            ad.setMovementMethod(LinkMovementMethod.getInstance());
            ad.setText(Html.fromHtml(ad_pk));
        }

        if (Destination.equalsIgnoreCase("guangzhou")){
            String ad_gz = "<a href='https://www.fourseasons.com/zh/guangzhou/'>" +
                    "Guangzhou - FOUR SEASONS hotel</a>";
            ad_gz += "(advertisement)";
            ad.setMovementMethod(LinkMovementMethod.getInstance());
            ad.setText(Html.fromHtml(ad_gz));
        }

        if (Destination.equalsIgnoreCase("nanchang")){
            String ad_nc = "<a href='https://www.shangri-la.com/en/nanchang/shangrila/'>" +
                    "Nanchang - SHANGRI-LA</a>";
            ad_nc += "(advertisement)";
            ad.setMovementMethod(LinkMovementMethod.getInstance());
            ad.setText(Html.fromHtml(ad_nc));
        }

        if (Destination.equalsIgnoreCase("shenzhen")){
            String ad_sz = "<a href='https://www.hyatt.com/en-US/hotel/china/park-hyatt-shenzhen/szxph'>" +
                    "Shenzhen - HYATT hotel</a>";
            ad_sz += "(advertisement)";
            ad.setMovementMethod(LinkMovementMethod.getInstance());
            ad.setText(Html.fromHtml(ad_sz));
        }

        if (Destination.equalsIgnoreCase("shanghai")){
            String ad_sh = "<a href='https://www.bulgarihotels.com/en_US/shanghai'>" +
                    "Shanghai - BVLGARI hotel</a>";
            ad_sh += "(advertisement)";
            ad.setMovementMethod(LinkMovementMethod.getInstance());
            ad.setText(Html.fromHtml(ad_sh));
        }

    }
}