package com.example.comp2100groupproject;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Airplane_Data {
    private List<Airplane> airplanes;

    public Airplane_Data() {this.airplanes = new ArrayList<Airplane>();}

    public Airplane_Data(List<Airplane> airplanes) {this.airplanes = airplanes;}

    public static List<Airplane> getList(Airplane_Data ad) {
        return ad.airplanes;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void saveToBespokeFile (File file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            ArrayList<Airplane> airplanes = new ArrayList<>();
            for (int i = 0; i < 5000; i++) {
                Airplane newa = Airplane.generate();
                airplanes.add(newa);
            }
            for (Airplane a : airplanes) {
                String type = a.type;
                int price = a.price;
                Time time = a.time;
                String destination = a.destination;
                String departure = a.departure;
                int available = a.available;

                bw.write(type);
                bw.write("#");
                bw.write(String.valueOf(price));
                bw.write("#");
                bw.write(String.valueOf(time));
                bw.write("#");
                bw.write(destination);
                bw.write("#");
                bw.write((departure));
                bw.write("#");
                bw.write(String.valueOf(available));
                bw.write("#");
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Airplane_Data loadFromJSONFile(File file) {
        Gson gson = new Gson();
        JsonReader jsonReader = null;

        final Type CUS_LIST_TYPE = new TypeToken<List<Airplane>>() {}.getType();
        //or TypeToken.getParameterized(ArrayList.class, PersonJSON.class).getType();

        try{
            jsonReader = new JsonReader(new FileReader(file));
        }catch (Exception e) {
            e.printStackTrace();
        }

        return new Airplane_Data((List<Airplane>) gson.fromJson(jsonReader, CUS_LIST_TYPE));
    }


}
