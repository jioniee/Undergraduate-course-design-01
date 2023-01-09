package com.example.comp2100groupproject;

public class NullExp extends Exp {
    @Override
    public Query evaluate() {
        System.out.println("evaluate null");
        return new Query();
    }

    @Override
    public String getQuery() {
        return "";
    }

}
