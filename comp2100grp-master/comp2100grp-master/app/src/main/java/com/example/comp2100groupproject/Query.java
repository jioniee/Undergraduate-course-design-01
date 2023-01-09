package com.example.comp2100groupproject;

import java.util.Arrays;

public class Query {
    String departure;
    String destination;
    String departureTime;
    String price;
    String NoTicket;

    public Query (String departure, String destination, String departureTime, String price, String NoTicket){
        this.departure = departure;
        this.destination = destination;
        this.departureTime = departureTime;
        this.price = price;
        this.NoTicket = NoTicket;
    }
    public Query(){};


    public String getDeparture() {
        if (this.departure != null){
            if (numberOfCompareSymbols(this.departure) != 1 && !OnlyEqual(this.departure)){
                throw new IllegalArgumentException();
            }
            return departure;
        }
        return null;
    }

    public String getDestination() {
        if (this.destination != null){
            if (numberOfCompareSymbols(this.destination) != 1 && !OnlyEqual(this.destination)){
                throw new IllegalArgumentException();
            }
            return destination;
        }
        return null;
    }

    public String getDepartureTime() {
        if (this.departureTime != null){
            if (numberOfCompareSymbols(this.departureTime) != 1 && !OnlyEqual(this.departureTime)){
                throw new IllegalArgumentException();
            }
            return departureTime;
        }
        return null;
    }

    public String getPrice() {
        if (this.price != null){
            if (numberOfCompareSymbols(this.price) > 2){
                throw new IllegalArgumentException();
            }
            return price;
        }
        return null;
    }

    public String getNoTicket() {
        if (this.NoTicket != null){
            if (numberOfCompareSymbols(this.NoTicket) != 1 && !OnlyEqual(this.NoTicket)){
                throw new IllegalArgumentException();
            }
            return NoTicket;
        }
        return null;
    }

    public void evaluate(){

        System.out.println("expected place of departure is " + departure);
        System.out.println("expected destination is " + destination);
        System.out.println("expected departure time is " +departureTime);
        System.out.println("expected price is " +price);
        System.out.println("expected number of ticket is " + NoTicket);
    }


    public int numberOfCompareSymbols(String text){
        int count = 0;
        char[] chars = text.toCharArray();

        for (char c : chars){
            if (c == '=' || c =='<' || c =='>'){
                count++;
            }
        }
        return count;

    }

    public boolean OnlyEqual(String text){
        char[] chars = text.toCharArray();
        for (char c : chars){
            if (c == '<' || c == '>'){
                return false;
            }
        }
        return true;
    }

}
