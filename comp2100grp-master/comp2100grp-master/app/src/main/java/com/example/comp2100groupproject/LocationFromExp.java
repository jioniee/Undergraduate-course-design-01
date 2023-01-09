package com.example.comp2100groupproject;

import java.util.ArrayList;
import java.util.List;

public class LocationFromExp extends Exp{
    List<String> Departure = new ArrayList<String>() {
        {
            add("canberra");add("sydney");add("melbourne"); add("perth"); add("brisbane");
        }
    };

    String fromLocation;
    public  LocationFromExp(String fromLocation){
        this.fromLocation = fromLocation;
    }
    @Override
    public Query evaluate() {
        Query query = new Query();
        if (! Departure.contains(fromLocation)){
            for (String d : Departure){
                if (Parser.similarity(d, fromLocation.substring(1)) >= 0.8){
                    fromLocation = "=" + d;
                }
            }
        }
        query.departure = fromLocation;
        return query;
    }

    @Override
    public String getQuery() {
        return "from " + fromLocation + "; ";
    }

}
