package com.example.comp2100groupproject;

public class TimeExp extends Exp {
    String time;
    public  TimeExp(String time){
        this.time = time;
    }

    @Override
    public Query evaluate() {
        Query query = new Query();
        query.departureTime = time;
        return query;
    }

    @Override
    public String getQuery() {
        return "time " + time + "; ";
    }
}
