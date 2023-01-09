package com.example.comp2100groupproject;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MyTest {
    @Test
    public void testgreater() {
        String s1 = "hello";
        String s2 = "bye";
        // test genreal case
        assertEquals(Airplane.greater(s1,s2),true);

        String s3 = "Bye";
        String s4 = "aye";
        // test when capitalization
        assertEquals(Airplane.greater(s4,s2) == Airplane.greater(s4,s3), false);
    }

    @Test
    public void testgenerate() {
        for (int i = 0; i < 1000; i++) {
            Airplane a = Airplane.generate();
            assertEquals((a.available<=8 && a.available >= 0 && a.price >= 5000 && a.price <= 15000), true);
        }


    }

    @Test
    public void testBSTdepa() {
        Airplane a1 = new Airplane("A330-200",10296,new Time(2020,3,28,23,21),"Chongqing","LLLLLLLLLLLLLLL",0);
        Airplane a4 = new Airplane("A330-200",10296,new Time(2020,3,28,23,21),"Chongqing","Sydney",0);
        Airplane a2 = new Airplane("A330-200",10296,new Time(2020,3,28,23,21),"Chongqing","Canberra",0);
        Airplane a3 = new Airplane("A330-200",10296,new Time(2020,3,28,23,21),"Chongqing","Perth",0);
        Airplane a5 = new Airplane("A330-200",10296,new Time(2020,3,28,23,21),"Chongqing","LLLLLLLLLLLLLLL",0);

        ArrayList<Airplane> alist = new ArrayList<>();
        BST b = new BST();
        alist.add(a1);
        alist.add(a4);
        alist.add(a2);
        alist.add(a3);
        alist.add(a5);
        for (Airplane a : alist) {
            b = BST.insertbydepa(a,b);
        }
        alist = BST.finddepa("Canberra",b);
        //test find method
        assertEquals(alist.size(), 1);
        //test insert method
        assertEquals(b.left.right.key.departure, "Canberra");

    }

    @Test
    public void testBSTdest() {
        Airplane a1 = new Airplane("A330-200",10296,new Time(2020,3,28,23,21),"Beijing","LLLLLLLLLLLLLLL",0);
        Airplane a4 = new Airplane("A330-200",10296,new Time(2020,3,28,23,21),"qqqqqqqqqqqqqqqqqqqqqqqqqqqq","Sydney",0);
        Airplane a2 = new Airplane("A330-200",10296,new Time(2020,3,28,23,21),"Shanghai","Canberra",0);
        Airplane a3 = new Airplane("A330-200",10296,new Time(2020,3,28,23,21),"qqqqqqqqqqqqqqqqqqqqqqqqqqq","Perth",0);
        Airplane a5 = new Airplane("A330-200",10296,new Time(2020,3,28,23,21),"ppppppppppppppp","LLLLLLLLLLLLLLL",0);

        ArrayList<Airplane> alist = new ArrayList<>();
        BST b = new BST();
        alist.add(a1);
        alist.add(a4);
        alist.add(a2);
        alist.add(a3);
        alist.add(a5);
        for (Airplane a : alist) {
            b = BST.insertbydest(a,b);
        }
        alist = BST.finddest("Beijing",b);
        //test find method
        assertEquals(alist.size(), 1);
        //test insert method
        assertEquals(b.right.left.key.destination, "Shanghai");

    }



    @Test
    public void testBSTprice() {
        Airplane a1 = new Airplane("A330-200",15000,new Time(2020,3,28,23,21),"Chongqing","LLLLLLLLLLLLLLL",0);
        Airplane a4 = new Airplane("A330-200",10000,new Time(2020,3,28,23,21),"Chongqing","Sydney",0);
        Airplane a2 = new Airplane("A330-200",5000,new Time(2020,3,28,23,21),"Chongqing","Canberra",0);
        Airplane a3 = new Airplane("A330-200",7500,new Time(2020,3,28,23,21),"Chongqing","Perth",0);
        Airplane a5 = new Airplane("A330-200",12500,new Time(2020,3,28,23,21),"Chongqing","LLLLLLLLLLLLLLL",0);

        ArrayList<Airplane> alist = new ArrayList<>();
        BST b = new BST();
        alist.add(a1);
        alist.add(a4);
        alist.add(a2);
        alist.add(a3);
        alist.add(a5);
        for (Airplane a : alist) {
            b = BST.insertbyprice(a,b);
        }
        alist = BST.findprice_equal(10000,b);
        assertEquals(alist.size(), 1);

        ArrayList<Airplane> alist2 = new ArrayList<>();
        alist2 = BST.findprice_more(10000,b);
        ArrayList<Airplane> alist3 = new ArrayList<>();
        alist3 = BST.findprice_less_equal(10000,b);

        // test find method
        assertEquals(alist.size(),1);
        assertEquals(alist2.size(), 2);
        assertEquals(alist3.size(), 3);

        //test insert method
        assertEquals(b.left.left.right.key.price, 7500);
        assertEquals(b.left.right.key.price, 12500);

    }

    @Test
    public void testBSTavail() {
        Airplane a1 = new Airplane("A330-200",10296,new Time(2020,3,28,23,21),"Beijing","LLLLLLLLLLLLLLL",7);
        Airplane a4 = new Airplane("A330-200",10296,new Time(2020,3,28,23,21),"qqqqqqqqqqqqqqqqqqqqqqqqqqqq","Sydney",3);
        Airplane a2 = new Airplane("A330-200",10296,new Time(2020,3,28,23,21),"Shanghai","Canberra",0);
        Airplane a3 = new Airplane("A330-200",10296,new Time(2020,3,28,23,21),"qqqqqqqqqqqqqqqqqqqqqqqqqqq","Perth",2);
        Airplane a5 = new Airplane("A330-200",10296,new Time(2020,3,28,23,21),"ppppppppppppppp","LLLLLLLLLLLLLLL",6);

        ArrayList<Airplane> alist = new ArrayList<>();
        BST b = new BST();
        alist.add(a1);
        alist.add(a4);
        alist.add(a2);
        alist.add(a3);
        alist.add(a5);
        for (Airplane a : alist) {
            b = BST.insertbyavail(a,b);
        }
        alist = BST.findavail(3,b);
        //test find method
        assertEquals(alist.size(), 3);
        //test insert method
        assertEquals(b.left.left.key.available, 0);

    }



    @Test
    public void testBSTtime() {
        Airplane a1 = new Airplane("A330-200",10296,new Time(2020,11,28,23,21),"Chongqing","LLLLLLLLLLLLLLL",0);
        Airplane a4 = new Airplane("A330-200",10296,new Time(2020,11,27,23,21),"Chongqing","Sydney",0);
        Airplane a2 = new Airplane("A330-200",10296,new Time(2020,11,26,23,21),"Chongqing","Canberra",0);
        Airplane a3 = new Airplane("A330-200",10296,new Time(2020,11,25,23,21),"Chongqing","Perth",0);
        Airplane a5 = new Airplane("A330-200",5000,new Time(2020,11,24,23,21),"Chongqing","LLLLLLLLLLLLLLL",0);

        ArrayList<Airplane> alist = new ArrayList<>();
        BST b = new BST();
        alist.add(a1);
        alist.add(a4);
        alist.add(a2);
        alist.add(a3);
        alist.add(a5);
        for (Airplane a : alist) {
            b = BST.insertbytime(a,b);
        }
        //test insert method
        assertEquals((b.left.left.left.left.key.price == 5000), true);

    }

    @Test
    public void testNearby() {
        double c1 = 1491.0;
        double c2 = -352.0;
        double s1 = 1512.0;
        double s2 = -338.0;

        //test normal case
        assertEquals(DepartNearby.compare(c1,c2), "Canberra");
        assertEquals(DepartNearby.compare(s1,s2), "Sydney");
    }



    @Test
    public void testfilldepature() {
        ArrayList<Query> queries = new ArrayList<>();
        Query q1 = new Query("Canberra",null,"1","2","3");
        Query q2 = new Query("Sydney",null,"1","2","3");
        Query q3 = new Query("Canberra",null,"1","2","3");

        queries.add(q1);
        queries.add(q2);
        queries.add(q3);

        // test normal case
        String s1 = MainActivity.filldepature(queries);
        assertEquals(s1,"canberra");

        //test for equal occurrence of two object
        Query q4 = new Query("Sydney",null,"1","2","3");
        queries.add(q4);
        String s2 = MainActivity.filldepature(queries);
        assertEquals(s2,"sydney");

    }

    @Test
    public void testgetAirplanes() {
        // Test Capital letter
        Query q1 = new Query("=Canberra","=chongqing",null,">9000","=3");
        String qs1 = q1.toString();
        Query q2 = new Query("=Canberra","=CHONGQING",null,">9000","=3");
        String qs2 = q2.toString();
        Query q3 = new Query(null,null,null,null,null);
        String qs3 = q3.toString();

        // test normal case and uppercase & lowercase does not affect test results
        assertEquals(getairlplanes("Canberra","chongqing",null,">9000","3").size() == getairlplanes("Canberra","CHONGQING",null,">9000","3").size(), true);

        // test when no limit
        assertEquals(getairlplanes(null,null,null,null,null).size(), 6);
    }

    @Test
    public void testTimetostring() {
        Time t1 = new Time(0,0,0,22,51);
        assertEquals(t1.toString(),"22 : 51");

        Time t2 = new Time(2020,11,20,3,1);
        assertEquals(t2.toString(),"2020/11/20/03:01");
    }

    @Test
    public void testTimefromstring() {
        String s1 = "2025/13/29/07:11";
        assertEquals(Time.fromString(s1).month, 13);
    }

    @Test
    public void testfromstringshort() {
        String s1 = "2025/13/29";
        assertEquals(Time.fromString_short(s1).year, 2025);
        assertEquals(Time.fromString_short(s1).month, 13);
        assertEquals(Time.fromString_short(s1).date, 29);
    }

    @Test
    public void testearilershort() {
        Time t1 = new Time(2020,11,20);
        Time t2 = new Time(2020,11,21);

        assertEquals(Time.earlier_short(t1,t2),true);
    }

    @Test
    public void testeariler() {
        Time t1 = new Time(2020,11,20,12,35);
        Time t2 = new Time(2020,11,20,12,36);

        assertEquals(Time.earlier(t1,t2),true);

    }









    public ArrayList<Airplane> getairlplanes (String depa, String dest, String time, String price, String noticket) {

        Airplane a1 = new Airplane("A330-200",10000,new Time(2020,11,25,23,21),"Beijing","Canberra",0);
        Airplane a2 = new Airplane("A330-200",5000,new Time(2020,11,25,23,21),"Shanghai","Perth",5);
        Airplane a3 = new Airplane("A330-200",6000,new Time(2020,11,25,23,21),"Chongqing","Canberra",6);
        Airplane a4 = new Airplane("A330-200",8000,new Time(2020,11,25,23,21),"Nanchang","Perth",2);
        Airplane a5 = new Airplane("A330-200",9000,new Time(2020,11,25,23,21),"Shanghai","Canberra",1);
        Airplane a6 = new Airplane("A330-200",11000,new Time(2020,11,25,23,21),"Chongqing","Perth",4);


        ArrayList<Airplane> airplanes = new ArrayList<>();
        List<Airplane> list1 = new ArrayList<>();
        list1.add(a1);
        list1.add(a2);
        list1.add(a3);
        list1.add(a4);
        list1.add(a5);
        list1.add(a6);

        airplanes.addAll(list1);


        // try create tree use departure
        if (depa != null) {
            BST b1 = new BST();
            for (Airplane a : list1) {
                b1 = BST.insertbydepa(a,b1);
            }
            airplanes = BST.finddepa(depa.substring(0).toLowerCase(),b1);
        }




        // try create tree use destiantion
        if (dest != null) {
            BST b2 = new BST();
            for (Airplane a : airplanes) {
                b2 = BST.insertbydest(a,b2);
            }
            airplanes = BST.finddest(dest.substring(0).toLowerCase(),b2);
        }

        //System.out.println(airplanes.size());

        // try create tree use departuretime
        if (time != null) {
            BST b3 = new BST();
            for (Airplane a : airplanes) {
                b3 = BST.insertbytime(a,b3);
            }
            airplanes = BST.findtime(Time.fromString_short(time.substring(0)),b3);
        }

        // try create tree use No_ticket
        if (noticket != null) {
            BST b4 = new BST();
            for (Airplane a : airplanes) {
                b4 = BST.insertbyavail(a,b4);
            }
            airplanes = BST.findavail(Integer.parseInt(noticket.substring(0)),b4);
        }

        // try create tree use price
        if (price != null) {
            BST b5 = new BST();
            for (Airplane a : airplanes) {
                b5 = BST.insertbyprice(a,b5);
            }

            //distinguish different expression
            if (price.charAt(0) == '=') {
                airplanes = BST.findprice_equal(Integer.parseInt(price.substring(1)),b5);
            }
            if (price.charAt(0) == '>' && price.charAt(1) != '=') {
                airplanes = BST.findprice_more(Integer.parseInt(price.substring(1)),b5);
            }
            if (price.charAt(0) == '>' && price.charAt(1) == '=') {
                airplanes = BST.findprice_more_equal(Integer.parseInt(price.substring(2)),b5);
            }
            if (price.charAt(0) == '<' && price.charAt(1) != '=') {
                airplanes = BST.findprice_less(Integer.parseInt(price.substring(1)),b5);
            }
            if (price.charAt(0) == '<' && price.charAt(1) == '=') {
                airplanes = BST.findprice_less_equal(Integer.parseInt(price.substring(2)),b5);
            }

        }

        return airplanes;
    }




}

