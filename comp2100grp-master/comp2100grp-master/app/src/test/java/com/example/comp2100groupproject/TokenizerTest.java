package com.example.comp2100groupproject;

import org.junit.Test;

import static org.junit.Assert.*;

public class TokenizerTest {

    @Test
    public void testNullInput(){
        Tokenizer tokenizer = new Tokenizer("");
        assertTrue(tokenizer.getCurrentToken() == null); // test for the null input the token should also be null
        assertTrue(tokenizer.hasNext() == false);
    }

    @Test
    public void testSingleToken(){
        Tokenizer tokenizer = new Tokenizer("from");
        assertTrue(tokenizer.getCurrentToken().get_Type().equals(Token.Type.Fixed));
        assertTrue(tokenizer.getCurrentToken().get_Token().equalsIgnoreCase("FROM"));
        // the lowercase or capital case of input string should not matter
        Tokenizer tokenizer1 = new Tokenizer("FROM");
        assertTrue(tokenizer1.getCurrentToken().get_Type().equals(Token.Type.Fixed));
        assertTrue(tokenizer1.getCurrentToken().get_Token().equalsIgnoreCase("from"));
        // check for other token types
        Tokenizer tokenizer2 = new Tokenizer("canberra");
        assertTrue(tokenizer2.getCurrentToken().get_Type().equals(Token.Type.Letters));
        assertTrue(tokenizer2.getCurrentToken().get_Token().equalsIgnoreCase("Canberra"));
        Tokenizer tokenizer3 = new Tokenizer("2019/11/22");
        assertTrue(tokenizer3.getCurrentToken().get_Type().equals(Token.Type.Time));
        assertTrue(tokenizer3.getCurrentToken().get_Token().equalsIgnoreCase("2019/11/22"));
        Tokenizer tokenizer4 = new Tokenizer("2019");
        assertTrue(tokenizer4.getCurrentToken().get_Type().equals(Token.Type.Number));
        assertTrue(tokenizer4.getCurrentToken().get_Token().equalsIgnoreCase("2019"));
    }

    @Test
    public void testSymbol(){
        Tokenizer tokenizer = new Tokenizer(";");
        assertTrue(tokenizer.getCurrentToken().get_Type().equals(Token.Type.Semicolon));
        assertTrue(tokenizer.getCurrentToken().get_Token().equalsIgnoreCase(";"));

        // check if the tokenizer can distinguish the difference between comparison symbol and semicolon
        Tokenizer tokenizer1 = new Tokenizer(">");
        assertTrue(tokenizer1.getCurrentToken().get_Type().equals(Token.Type.Comparison));
        assertTrue(tokenizer1.getCurrentToken().get_Token().equalsIgnoreCase(">"));

        // check if there are two comparison symbols the token should consider it as a single token
        Tokenizer tokenizer2 = new Tokenizer(">=");
        assertTrue(tokenizer2.getCurrentToken().get_Type().equals(Token.Type.Comparison));
        assertTrue(tokenizer2.getCurrentToken().get_Token().equalsIgnoreCase(">="));

        // check if == still be considered as a single token even it has no meaning, but still correspond to the grammar
        Tokenizer tokenizer3 = new Tokenizer("==");
        assertTrue(tokenizer3.getCurrentToken().get_Type().equals(Token.Type.Comparison));
        assertTrue(tokenizer3.getCurrentToken().get_Token().equalsIgnoreCase("=="));

        // check if there are three symbols
        Tokenizer tokenizer4 = new Tokenizer(">=<");
        assertTrue(tokenizer4.getCurrentToken().get_Type().equals(Token.Type.Comparison));
        assertTrue(tokenizer4.getCurrentToken().get_Token().equalsIgnoreCase(">="));
        tokenizer4.next();
        assertTrue(tokenizer4.getCurrentToken().get_Type().equals(Token.Type.Comparison));
        assertTrue(tokenizer4.getCurrentToken().get_Token().equalsIgnoreCase("<"));
        tokenizer4.next();



        // test combination
        Tokenizer tokenizer5 = new Tokenizer(">=12");
        assertTrue(tokenizer5.getCurrentToken().get_Type().equals(Token.Type.Comparison));
        assertTrue(tokenizer5.getCurrentToken().get_Token().equalsIgnoreCase(">="));
        tokenizer5.next();
        assertTrue(tokenizer5.getCurrentToken().get_Type().equals(Token.Type.Number));
        assertTrue(tokenizer5.getCurrentToken().get_Token().equalsIgnoreCase("12"));
        // check if the  next token is null
        tokenizer5.next();
        assertTrue(tokenizer5.getCurrentToken() == null);
    }

    @Test
    // test if the string contains all the token type
    public void testCombination(){
        Tokenizer tokenizer = new Tokenizer("From = shanghai;");
        assertTrue(tokenizer.getCurrentToken().get_Type().equals(Token.Type.Fixed));
        assertTrue(tokenizer.getCurrentToken().get_Token().equalsIgnoreCase("from"));
        tokenizer.next();
        assertTrue(tokenizer.getCurrentToken().get_Type().equals(Token.Type.Comparison));
        assertTrue(tokenizer.getCurrentToken().get_Token().equalsIgnoreCase("="));
        tokenizer.next();
        assertTrue(tokenizer.getCurrentToken().get_Type().equals(Token.Type.Letters));
        assertTrue(tokenizer.getCurrentToken().get_Token().equalsIgnoreCase("shanghai"));
        tokenizer.next();
        assertTrue(tokenizer.getCurrentToken().get_Type().equals(Token.Type.Semicolon));
        assertTrue(tokenizer.getCurrentToken().get_Token().equalsIgnoreCase(";"));
        tokenizer.next();
        assertTrue(tokenizer.getCurrentToken() == null);
    }

    @Test
    // test if the token can tell time and number correctly
    public void testTime(){
        Tokenizer tokenizer = new Tokenizer("2019/11/22");
        assertTrue(tokenizer.getCurrentToken().get_Type().equals(Token.Type.Time));
        assertTrue(tokenizer.getCurrentToken().get_Token().equalsIgnoreCase("2019/11/22"));
        Tokenizer tokenizer1 = new Tokenizer("2019/22");
        assertFalse(tokenizer1.getCurrentToken().get_Type().equals(Token.Type.Time));
        assertTrue(tokenizer1.getCurrentToken().get_Token().equalsIgnoreCase("2019/22"));
        assertTrue(tokenizer1.getCurrentToken().get_Type().equals(Token.Type.Unknown));
        tokenizer1.next();
        assertTrue(tokenizer1.getCurrentToken() == null);
    }

    @Test
    // test the time token together with the number
    public void testTimeNumber(){
        Tokenizer tokenizer = new Tokenizer("2019/11/22 22");
        assertTrue(tokenizer.getCurrentToken().get_Type().equals(Token.Type.Time));
        assertTrue(tokenizer.getCurrentToken().get_Token().equalsIgnoreCase("2019/11/22"));
        tokenizer.next();
        assertTrue(tokenizer.getCurrentToken().get_Type().equals(Token.Type.Number));
        assertTrue(tokenizer.getCurrentToken().get_Token().equalsIgnoreCase("22"));

        Tokenizer tokenizer2 = new Tokenizer("22 2019/11/22");
        assertTrue(tokenizer2.getCurrentToken().get_Type().equals(Token.Type.Number));
        assertTrue(tokenizer2.getCurrentToken().get_Token().equalsIgnoreCase("22"));
        tokenizer2.next();
        assertTrue(tokenizer2.getCurrentToken().get_Type().equals(Token.Type.Time));
        assertTrue(tokenizer2.getCurrentToken().get_Token().equalsIgnoreCase("2019/11/22"));
    }

    @Test
    // test the combination of time and letter
    public void testTimeLetter(){
        Tokenizer tokenizer = new Tokenizer("2019/11/22 shanghai");
        assertTrue(tokenizer.getCurrentToken().get_Type().equals(Token.Type.Time));
        assertTrue(tokenizer.getCurrentToken().get_Token().equalsIgnoreCase("2019/11/22"));
        tokenizer.next();
        assertTrue(tokenizer.getCurrentToken().get_Type().equals(Token.Type.Letters));
        assertTrue(tokenizer.getCurrentToken().get_Token().equalsIgnoreCase("shanghai"));
    }

    @Test
    // test with multiple combination
    public void testMultipleCombination(){
        Tokenizer tokenizer = new Tokenizer("from = shanghai; to = sydney; time = 2019/11/22");
        assertTrue(tokenizer.getCurrentToken().get_Type().equals(Token.Type.Fixed));
        assertTrue(tokenizer.getCurrentToken().get_Token().equalsIgnoreCase("from"));
        tokenizer.next();
        assertTrue(tokenizer.getCurrentToken().get_Type().equals(Token.Type.Comparison));
        assertTrue(tokenizer.getCurrentToken().get_Token().equalsIgnoreCase("="));
        tokenizer.next();
        assertTrue(tokenizer.getCurrentToken().get_Type().equals(Token.Type.Letters));
        assertTrue(tokenizer.getCurrentToken().get_Token().equalsIgnoreCase("shanghai"));
        tokenizer.next();
        tokenizer.next();
        tokenizer.next();
        tokenizer.next();
        assertTrue(tokenizer.getCurrentToken().get_Type().equals(Token.Type.Letters));
        assertTrue(tokenizer.getCurrentToken().get_Token().equalsIgnoreCase("Sydney"));
        tokenizer.next();
        tokenizer.next();
        tokenizer.next();
        tokenizer.next();
        assertTrue(tokenizer.getCurrentToken().get_Type().equals(Token.Type.Time));
        assertTrue(tokenizer.getCurrentToken().get_Token().equalsIgnoreCase("2019/11/22"));
    }

    @Test
    // the whitespace should not matter
    public void testWhiteSpace(){
        Tokenizer tokenizer = new Tokenizer(" 2      3");
        assertTrue(tokenizer.getCurrentToken().get_Type().equals(Token.Type.Number));
        assertTrue(tokenizer.getCurrentToken().get_Token().equalsIgnoreCase("2"));
        tokenizer.next();
        assertTrue(tokenizer.getCurrentToken().get_Type().equals(Token.Type.Number));
        assertTrue(tokenizer.getCurrentToken().get_Token().equalsIgnoreCase("3"));
    }

    @Test
    // test special character or symbol which is not in the grammar
    public void testSpecialCharacter(){
        Tokenizer tokenizer = new Tokenizer("from :  shanghai");
        assertTrue(tokenizer.getCurrentToken().get_Type().equals(Token.Type.Fixed));
        assertTrue(tokenizer.getCurrentToken().get_Token().equalsIgnoreCase("from"));
        tokenizer.next();
        assertTrue(tokenizer.getCurrentToken().get_Type().equals(Token.Type.Unknown));
        assertTrue(tokenizer.getCurrentToken().get_Token().equalsIgnoreCase(":"));
        tokenizer.next();
        assertTrue(tokenizer.getCurrentToken().get_Type().equals(Token.Type.Letters));
        assertTrue(tokenizer.getCurrentToken().get_Token().equalsIgnoreCase("shanghai"));
    }
}
