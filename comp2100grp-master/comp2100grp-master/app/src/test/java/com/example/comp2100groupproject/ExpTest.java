package com.example.comp2100groupproject;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExpTest {

    @Test
    public void priceTest(){
        PriceExp price = new PriceExp("=5000");
        assertEquals("price =5000; ", price.getQuery());
        assertEquals(price.evaluate().price, "=5000");
        assertTrue(price.evaluate().NoTicket == null
                &&  price.evaluate().departureTime == null
                && price.evaluate().destination == null
                && price.evaluate().departure == null );
    }

    @Test
    public void fromTest(){
        // test if the departure is well formed
        LocationFromExp from = new LocationFromExp("=shanghai");
        assertEquals("from =shanghai; ", from.getQuery());
        assertEquals(from.evaluate().departure, "=shanghai");
        assertTrue(from.evaluate().NoTicket == null
                &&  from.evaluate().departureTime == null
                && from.evaluate().destination == null
                && from.evaluate().price == null );
        // test if we have the spelling mistake
        LocationFromExp from1 = new LocationFromExp("=sydny");

        assertEquals(from1.evaluate().departure, "=sydney");
        assertEquals("from =sydney; ", from1.getQuery());
        assertTrue(from1.evaluate().NoTicket == null
                &&  from1.evaluate().departureTime == null
                && from1.evaluate().destination == null
                && from1.evaluate().price == null );
    }

    @Test
    public void toTest(){
        // test if the destination  is well formed
        LocationToExp to = new LocationToExp("=shanghai");
        assertEquals("to =shanghai; ", to.getQuery());
        assertEquals(to.evaluate().destination, "=shanghai");
        assertTrue(to.evaluate().NoTicket == null
                &&  to.evaluate().departureTime == null
                && to.evaluate().departure == null
                && to.evaluate().price == null );
        // test if we have the spelling mistake
        LocationToExp to1 = new LocationToExp("=shanhai");

        assertEquals(to1.evaluate().destination, "=shanghai");
        assertEquals("to =shanghai; ", to1.getQuery());
        assertTrue(to1.evaluate().NoTicket == null
                &&  to1.evaluate().departureTime == null
                && to1.evaluate().departure == null
                && to1.evaluate().price == null );
    }

    @Test
    public void TimeTest(){
        TimeExp time = new TimeExp("=2019/11/22");
        assertEquals("time =2019/11/22; ", time.getQuery());
        assertEquals(time.evaluate().departureTime, "=2019/11/22");
        assertTrue(time.evaluate().NoTicket == null
                &&  time.evaluate().price == null
                && time.evaluate().destination == null
                && time.evaluate().departure == null );
    }

    @Test
    public void NoTicketTest(){
        NoTicketExp no = new NoTicketExp("=2");
        assertEquals("NoTicket =2; ", no.getQuery());
        assertEquals(no.evaluate().NoTicket, "=2");
        assertTrue(no.evaluate().departureTime == null
                &&  no.evaluate().price == null
                && no.evaluate().destination == null
                && no.evaluate().departure == null );
    }

    @Test
    public void evaluateNullExpTest(){
        Exp exp = new NullExp();
        assertEquals("", exp.getQuery());
        assertNull(exp.evaluate().getDeparture());
        assertNull(exp.evaluate().getDepartureTime());
        assertNull(exp.evaluate().getDestination());
        assertNull(exp.evaluate().getNoTicket());
        assertNull(exp.evaluate().getPrice());
    }

    @Test
    public void twoExpTest(){
        Exp exp = new EvaluationExp( new LocationToExp("=shanghai"), new LocationFromExp("=canberra"));
        Query query = exp.evaluate();
        assertNull(query.getNoTicket());
        assertNull(query.getDepartureTime());
        assertNull(query.getPrice());
        assertEquals("=shanghai", query.getDestination());
        assertEquals("=canberra", query.getDeparture());
    }

    @Test
    public void testAllExp(){
        Exp from = new LocationFromExp("=canberra");
        Exp to = new LocationToExp("=shanghai");
        Exp price = new PriceExp("<=5000");
        Exp time = new TimeExp("=2019/11/22");
        Exp NoTicker = new NoTicketExp("=2");
        Exp exp = new EvaluationExp(from, new EvaluationExp(to, new EvaluationExp(price, new EvaluationExp(time, new EvaluationExp(NoTicker, new NullExp())))));
        Query query = exp.evaluate();
        assertEquals("=shanghai", query.getDestination());
        assertEquals("=canberra", query.getDeparture());
        assertEquals("<=5000", query.getPrice());
        assertEquals("=2019/11/22", query.getDepartureTime());
        assertEquals("=2", query.getNoTicket());
        String rawQuery = exp.getQuery();
        assertEquals("from =canberra; to =shanghai; price <=5000; time =2019/11/22; NoTicket =2; ", rawQuery);
    }

    //test what if we have two expression that represent the same information
    @Test (expected = IllegalArgumentException.class)
    public void testDuplicate(){
        Exp from = new LocationFromExp("=canberra");
        Exp from2 = new LocationFromExp("=shanghai");
        Exp exp = new EvaluationExp(from, from2);
        exp.evaluate();
    }

}
