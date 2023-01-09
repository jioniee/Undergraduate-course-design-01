package com.example.comp2100groupproject;

public class PriceExp extends Exp {
    String price;
    public  PriceExp(String price){
        this.price = price;
    }

    @Override
    public Query evaluate() {
        Query query = new Query();
        query.price = price;
        return query;
    }

    @Override
    public String getQuery() {
        return  "price " + price + "; ";
    }
}
