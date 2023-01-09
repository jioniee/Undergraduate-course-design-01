package com.example.comp2100groupproject;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParserTest {

    @Test
    public void oneExpressParser() throws Exception {
        String test = "price >= 5000";
        Tokenizer tokenizer = new Tokenizer(test);
        Query query;
        Exp exp = new Parser(tokenizer).parseExp();
        query = exp.evaluate();
        assertEquals(">=5000", query.getPrice());
    }

    @Test
    public void multpleExpreessionParser() throws Exception{
        String test = "from =canberra; to =shanghai; price <=5000; time =2019/11/22; NoTicket =2;";
        Tokenizer tokenizer = new Tokenizer(test);
        Query query;
        Exp exp = new Parser(tokenizer).parseExp();
        query = exp.evaluate();
        assertEquals("<=5000", query.getPrice());
        assertEquals("=canberra", query.getDeparture());
        assertEquals("=shanghai", query.getDestination());
        assertEquals("=2019/11/22", query.getDepartureTime());
        assertEquals("=2", query.getNoTicket());
    }

    @Test
    // test if the string is not corresponding to the grammar
    // the string is not started with a fixed word
    public void testNotStartWithFixedToken() throws Exception{
        String test = "canberra = from";
        Tokenizer tokenizer = new Tokenizer(test);
        Query query;
        Exp exp = new Parser(tokenizer).parseExp();
        query = exp.evaluate();
        assertEquals("=canberra", query.getDeparture());
    }

    @Test (expected = Exception.class)
    public void testNotHaveComparisonSymbolInMiddle() throws Exception {
        String test = "from canberra";
        Tokenizer tokenizer = new Tokenizer(test);
        Query query;
        Exp exp = new Parser(tokenizer).parseExp();
        query = exp.evaluate();

        String test1 = "canberra from";
        Tokenizer tokenizer1 = new Tokenizer(test1);
        Query query1;
        Exp exp1 = new Parser(tokenizer1).parseExp();
        query1 = exp.evaluate();
    }

    @Test
    // if the form is misspelling
    public void testSpellingMistake() throws Exception {
        String test = "frome = canberra";
        Tokenizer tokenizer = new Tokenizer(test);
        Query query;
        Exp exp = new Parser(tokenizer).parseExp();
        query = exp.evaluate();
        assertEquals("=canberra", query.getDeparture());
    }

    @Test
    // if the string contains duplicate semicolon which is not corresponding to grammar
    public void testDuplicateSemicolon() throws Exception{
        String test = "from = canberra;; to = sydney";
        Tokenizer tokenizer = new Tokenizer(test);
        Query query;
        Exp exp = new Parser(tokenizer).parseExp();
        query = exp.evaluate();
        assertEquals("=canberra", query.getDeparture());
        assertEquals("=sydney", query.getDestination());
    }

    @Test (expected = Exception.class)
    // if there is a comparison symbol but does not have three elements
    public void testInadequateThreeComponent() throws Exception{
        String test = "from = ;";
        Tokenizer tokenizer = new Tokenizer(test);
        Query query;
        Exp exp = new Parser(tokenizer).parseExp();
        query = exp.evaluate();

    }
}
