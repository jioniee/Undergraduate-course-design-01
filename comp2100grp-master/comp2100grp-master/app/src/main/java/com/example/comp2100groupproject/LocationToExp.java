package com.example.comp2100groupproject;

import java.util.ArrayList;
import java.util.List;

public class LocationToExp extends Exp {
    List<String> Destination = new ArrayList<String>() {
        {
            add("beijing");add("shanghai");add("guangzhou"); add("shenzhen"); add("nanchang");
        }
    };
    String toLocation;
    public  LocationToExp(String toLocation){
        this.toLocation = toLocation;
    }
    @Override
    public Query evaluate() {
        Query query = new Query();
        if (! Destination.contains(toLocation)){
            for (String d : Destination){
                System.out.println(d);
                if (Parser.similarity(d, toLocation.substring(1)) >= 0.8){
                    toLocation = "=" + d;
                }
            }
        }
        query.destination = toLocation;
        return query;
    }

    @Override
    public String getQuery() {
        return "to " + toLocation  + "; ";
    }
}
