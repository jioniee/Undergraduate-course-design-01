package com.example.comp2100groupproject;

import java.util.ArrayList;

public class BST {
    Airplane key;
    BST left, right;

    BST() {
        key = null;
        left = right = null;
    }

    BST(Airplane air) {
        this.key = air;
        left = new BST();
        right = new BST();
    }
    
    public static BST insertbydepa(Airplane air, BST root) {
        return insertbydepa_rec(air, root);
    }

    public static BST insertbydepa_rec(Airplane air, BST n) {
        if (n.key == null) {
            n = new BST(air);
            return n;
        }
        if (!Airplane.greater(air.departure, n.key.departure)) {
            n.left = insertbydepa_rec(air, n.left);
        } else if (Airplane.greater(air.departure, n.key.departure)) {
            n.right = insertbydepa_rec(air, n.right);
        }
        return n;
    }

    public static ArrayList<Airplane> finddepa(String departure, BST b) {
        ArrayList<Airplane> airplanes = new ArrayList<>();
        int count = 0;
        return finddepa_rec(departure, b, airplanes);
    }

    public static ArrayList<Airplane> finddepa_rec(String departure, BST b, ArrayList<Airplane> airplanes) {
        if (b.key == null) return airplanes;
        if (departure.equals(b.key.departure)) {
            airplanes.add(b.key);
            return finddepa_rec(departure, b.left, airplanes);
        }
        if (!Airplane.greater(departure, b.key.departure)) {
            return finddepa_rec(departure, b.left, airplanes);
        } else {
            return finddepa_rec(departure, b.right, airplanes);
        }

    }


    public static BST insertbydest(Airplane air, BST root) {
        return insertbydest_rec(air, root);
    }

    public static BST insertbydest_rec(Airplane air, BST n) {
        if (n.key == null) {
            n = new BST(air);
            return n;
        }
        if (!Airplane.greater(air.destination, n.key.destination)) {
            n.left = insertbydest_rec(air, n.left);
        } else if (Airplane.greater(air.destination, n.key.destination)) {
            n.right = insertbydest_rec(air, n.right);
        }
        return n;
    }

    public static ArrayList<Airplane> finddest(String destination, BST b) {
        ArrayList<Airplane> airplanes = new ArrayList<>();
        return finddest_rec(destination, b, airplanes);
    }

    public static ArrayList<Airplane> finddest_rec(String destination, BST b, ArrayList<Airplane> airplanes) {
        if (b.key == null) return airplanes;
        if (destination.equals(b.key.destination)) {
            airplanes.add(b.key);
            return finddest_rec(destination, b.left, airplanes);
        }
        if (!Airplane.greater(destination, b.key.destination)) {
            return finddest_rec(destination, b.left, airplanes);
        } else {
            return finddest_rec(destination, b.right, airplanes);
        }

    }


    public static BST insertbytime(Airplane air, BST root) {
        return insertbytime_rec(air, root);
    }

    public static BST insertbytime_rec(Airplane air, BST n) {
        if (n.key == null) {
            n = new BST(air);
            return n;
        }
        if (!Time.earlier_short(n.key.time, air.time)) {
            n.left = insertbytime_rec(air, n.left);
        } else if (Time.earlier_short(n.key.time, air.time)) {
            n.right = insertbytime_rec(air, n.right);
        }
        return n;
    }

    public static ArrayList<Airplane> findtime(Time time, BST b) {
        ArrayList<Airplane> airplanes = new ArrayList<>();
        return findtime_rec(time, b, airplanes);
    }

    public static ArrayList<Airplane> findtime_rec(Time time, BST b, ArrayList<Airplane> airplanes) {
        if (b.key == null) return airplanes;
        if (time.year == b.key.time.year && time.month == b.key.time.month && time.date == b.key.time.date) {
            airplanes.add(b.key);
            return findtime_rec(time, b.left, airplanes);
        }
        if (Time.earlier_short(time, b.key.time)) {
            return findtime_rec(time, b.left, airplanes);
        } else {
            return findtime_rec(time, b.right, airplanes);
        }

    }


    public static BST insertbyprice(Airplane air, BST root) {
        return insertbyprice_rec(air, root);
    }

    public static BST insertbyprice_rec(Airplane air, BST n) {
        if (n.key == null) {
            n = new BST(air);
            return n;
        }
        if (air.price < n.key.price) {
            n.left = insertbyprice_rec(air, n.left);
        } else if (air.price >= n.key.price) {
            n.right = insertbyprice_rec(air, n.right);
        }
        return n;
    }


    public static ArrayList<Airplane> findprice_equal(int price, BST b) {
        ArrayList<Airplane> airplanes = new ArrayList<>();
        return findprice_rec_equal(price, b, airplanes);
    }

    public static ArrayList<Airplane> findprice_rec_equal(int price, BST b, ArrayList<Airplane> airplanes) {
        if (b.key == null) return airplanes;
        if (price == b.key.price) {
            airplanes.add(b.key);
            return findprice_rec_equal(price, b.left, airplanes);
        }
        if (price < b.key.price) {
            return findprice_rec_equal(price, b.left, airplanes);
        } else {
            return findprice_rec_equal(price, b.right, airplanes);
        }

    }

    public static ArrayList<Airplane> twoArraylist(ArrayList<Airplane> a1, ArrayList<Airplane> a2) {
        a1.addAll(a2);
        return a1;
    }

    public static ArrayList<Airplane> findprice_less(int price, BST b) {
        ArrayList<Airplane> airplanes = new ArrayList<>();
        return findprice_rec_less(price, b, airplanes);
    }

    public static ArrayList<Airplane> findprice_rec_less(int price, BST b, ArrayList<Airplane> airplanes) {
        if (b.key == null) return airplanes;
        if (price == b.key.price) {
            return findprice_rec_less(price, b.left, airplanes);
        }
        if (price < b.key.price) {
            return findprice_rec_less(price, b.left, airplanes);
        }
        if (price > b.key.price) {
            airplanes.add(b.key);
            ArrayList<Airplane> a1 = new ArrayList<>();
            return twoArraylist(findprice_rec_less(price, b.right, airplanes), findprice_rec_less(price, b.left, a1));
        }
        return null;
    }

    public static ArrayList<Airplane> findprice_less_equal(int price, BST b) {
        ArrayList<Airplane> airplanes = new ArrayList<>();
        return findprice_rec_less_equal(price, b, airplanes);
    }

    public static ArrayList<Airplane> findprice_rec_less_equal(int price, BST b, ArrayList<Airplane> airplanes) {
        if (b.key == null) return airplanes;
        if (price == b.key.price) {
            airplanes.add(b.key);
            return findprice_rec_less_equal(price, b.left, airplanes);
        }
        if (price < b.key.price) {
            return findprice_rec_less_equal(price, b.left, airplanes);
        }
        if (price > b.key.price) {
            airplanes.add(b.key);
            ArrayList<Airplane> a1 = new ArrayList<>();
            return twoArraylist(findprice_rec_less_equal(price, b.right, airplanes), findprice_rec_less_equal(price, b.left, a1));
        }
        return null;
    }

    public static ArrayList<Airplane> findprice_more(int price, BST b) {
        ArrayList<Airplane> airplanes = new ArrayList<>();
        return findprice_rec_more(price, b, airplanes);
    }

    public static ArrayList<Airplane> findprice_rec_more(int price, BST b, ArrayList<Airplane> airplanes) {
        if (b.key == null) return airplanes;
        if (price == b.key.price) {
            return findprice_rec_more(price, b.right, airplanes);
        }
        if (price < b.key.price) {
            airplanes.add(b.key);
            ArrayList<Airplane> a1 = new ArrayList<>();
            return twoArraylist(findprice_rec_more(price, b.right, airplanes), findprice_rec_more(price, b.left, a1));
        }
        if (price > b.key.price) {

            return findprice_rec_more(price, b.right, airplanes);
        }
        return null;
    }

    public static ArrayList<Airplane> findprice_more_equal(int price, BST b) {
        ArrayList<Airplane> airplanes = new ArrayList<>();
        return findprice_rec_more_equal(price, b, airplanes);
    }

    public static ArrayList<Airplane> findprice_rec_more_equal(int price, BST b, ArrayList<Airplane> airplanes) {
        if (b.key == null) return airplanes;
        if (price == b.key.price) {
            airplanes.add(b.key);
            return findprice_rec_more_equal(price, b.right, airplanes);
        }
        if (price < b.key.price) {
            airplanes.add(b.key);
            ArrayList<Airplane> a1 = new ArrayList<>();
            return twoArraylist(findprice_rec_more_equal(price, b.right, airplanes), findprice_rec_more_equal(price, b.left, a1));
        }
        if (price > b.key.price) {

            return findprice_rec_more_equal(price, b.right, airplanes);
        }
        return null;
    }


    public static BST insertbyavail(Airplane air, BST root) {
        return insertbyavail_rec(air, root);
    }

    public static BST insertbyavail_rec(Airplane air, BST n) {
        if (n.key == null) {
            n = new BST(air);
            return n;
        }
        if (air.available < n.key.available) {
            n.left = insertbyavail_rec(air, n.left);
        } else if (air.available >= n.key.available) {
            n.right = insertbyavail_rec(air, n.right);
        }
        return n;
    }

    public static ArrayList<Airplane> findavail(int available, BST b) {
        int now = available;
        ArrayList<Airplane> sum = new ArrayList<>();
        while (now < 8) {
            ArrayList<Airplane> airplanes = new ArrayList<>();
            airplanes = findavail_rec(now, b, airplanes);
            sum.addAll(airplanes);
            now = now + 1;
        }
        return sum;
    }

    public static ArrayList<Airplane> findavail_rec(int available, BST b, ArrayList<Airplane> airplanes) {
        if (b.key == null) return airplanes;
        if (available == b.key.available) {
            airplanes.add(b.key);
            return findavail_rec(available, b.right, airplanes);
        }
        if (available > b.key.available) {
            return findavail_rec(available, b.right, airplanes);
        }
        if (available < b.key.available) {
            return findavail_rec(available, b.left, airplanes);
        }
        return null;
    }


}