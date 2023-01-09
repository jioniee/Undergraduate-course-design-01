package com.example.comp2100groupproject;

public class NoTicketExp extends Exp {
    String noTicket;
    public  NoTicketExp(String noTicket){
        this.noTicket = noTicket;
    }
    @Override
    public Query evaluate() {
        Query query = new Query();
        query.NoTicket = noTicket;
        return query;
    }

    @Override
    public String getQuery() {
        return "NoTicket " + noTicket+ "; ";
    }
}
