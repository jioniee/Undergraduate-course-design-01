package com.example.comp2100groupproject;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Random;

public class Airplane {

    public String type;
    public int price;
    public Time time;
    public String destination;
    public String departure;
    public int available;

    public Airplane(String type, int price, Time time, String destination, String departure, int available) {
        this.type = type;
        this.price = price;
        this.time = time;
        this.destination = destination;
        this.departure = departure;
        this.available = available;
    }

    public static Boolean greater (String s1, String s2) {
        int sum_s1 = 0;
        for (int i = 0; i < s1.length(); i++) {
            char ch = s1.charAt(i);
            sum_s1 = sum_s1 + ch;
        }
        int sum_s2 = 0;
        for (int i = 0; i < s2.length(); i++) {
            char ch = s2.charAt(i);
            sum_s2 = sum_s2 + ch;
        }
        if (sum_s1 > sum_s2) {return true;}
        return false;
    }



    /*int[] ALL_YEAR = {0, 1};
    int[] ALL_MONTH = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    int[] ALL_DATE_BIG = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
    int[] ALL_DATE_MIDDLE = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
    int[] ALL_DATE_SMALL = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28};
    int[] ALL_HOUR = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
    int[] ALL_MINUTE = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60};
*/
    @Override
    public String toString() {
        return
                "type : " + type  +
                ", price : " + price +
                ", time : " + time +
                ", destination : " + destination  +
                ", departure : " + departure  +
                ", available : " + available;
    }

    public static Airplane generate() {
        String[] ALL_TYPE = {"A318","A319","A320","A321"};
        int STANDARD_PRICE = 10000;
        String[] ALL_DESTINATION = {"beijing", "shanghai", "guangzhou", "shenzhen","nanchang"};
        String[] ALL_DEPARTURE = {"sydney", "canberra", "melbourne","brisbane", "perth"};
        Random random = new Random();
/*        int year = random.nextInt(2);
        year = year + 2021;*/

        int year = 2020;

/*        int month = random.nextInt(12);
        month = month + 1;

        int date = 0;

        if (month == 1 ||  month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            date = random.nextInt(31);
            date = date + 1;
        }
        if (month == 4 ||  month == 6 || month == 9 || month == 12) {
            date = random.nextInt(30);
            date = date + 1;
        }
        if (month == 2) {
            date = random.nextInt(28);
            date = date + 1;
        }*/

        int month = random.nextInt(2);
        month = month + 11;

        int date = 0;

        if (month == 11) {
            date = random.nextInt(30);
            date = date + 1;
        }

        if (month == 12) {
            date = random.nextInt(31);
            date = date + 1;
        }

        int hour = random.nextInt(24);

        int minute = random.nextInt(60);

        int index_type = random.nextInt(ALL_TYPE.length);
        ArrayList<String> ALL_TYPE_ARRAY = new ArrayList<>();
        for (String type : ALL_TYPE) {
            ALL_TYPE_ARRAY.add(type);
        }
        String type = ALL_TYPE_ARRAY.get(index_type);

        int index_destination = random.nextInt(ALL_DESTINATION.length);
        ArrayList<String> ALL_DESTINATION_ARRAY = new ArrayList<>();
        for (String dest : ALL_DESTINATION) {
            ALL_DESTINATION_ARRAY.add(dest);
        }
        String dest = ALL_DESTINATION_ARRAY.get(index_destination);

        int index_departure = random.nextInt(ALL_DEPARTURE.length);
        ArrayList<String> ALL_DEPARTURE_ARRAY = new ArrayList<>();
        for (String depa : ALL_DEPARTURE) {
            ALL_DEPARTURE_ARRAY.add(depa);
        }
        String depa = ALL_DEPARTURE_ARRAY.get(index_departure);

        int available = random.nextInt(10);
        available = available - 2;
        if (available < 0) {
            available = 0;
        }

        int price = random.nextInt(10000);
        price = 15000 - price;

        Time t = new Time(year,month,date,hour,minute);
        Airplane plane = new Airplane(type,price,t,dest,depa,available);

        return plane;
    }

}