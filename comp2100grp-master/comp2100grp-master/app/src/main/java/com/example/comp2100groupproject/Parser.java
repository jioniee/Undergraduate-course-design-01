package com.example.comp2100groupproject;

import java.util.ArrayList;
import java.util.List;

public class  Parser {
    /**
     * <exp> -> <term> | <term> ; <exp> ;
     * <term> -> <terminal> <compare exp> <query>
     * <terminal> -> "from" | "to" | "price" | "time" | "NoTicket" // the upper and lower case should not matter
     * <compare exp> -> <compare symbol> | <compare symbol> <compare symbol> // the compare expression should composed of one or two compare symbols
     * <compare symbol> -> ">" | "<" | "="
     * <query> -> <string> | <containsDigit> (can be the "time" and the "price")
     * <containsDigit> -> <unsigned integer> | <unsigned int><|><unsigned int><|><unsigned int> (if have "/" then it should be time otherwise it could be price of ticket or the number of ticket)
     */

    Tokenizer tokenizer;
    String error = "";


    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;

    }




    public Exp parseExp() throws Exception {
        // if the token is an semicolon, then go to the next combination
        while (tokenizer.hasNext() && tokenizer.getCurrentToken().get_Type().equals(Token.Type.Semicolon)){
            tokenizer.next();
        }

        // if the combination is not started with a token of type "Fixed"
        if (tokenizer.getCurrentToken() != null && !tokenizer.getCurrentToken().get_Type().equals(Token.Type.Fixed) ){
            // get the next three tokens
            List<Token> tokens = new ArrayList<>();
            tokens.add(tokenizer.getCurrentToken());
            if (tokenizer.hasNext()){
                tokenizer.next();
                tokens.add(tokenizer.getCurrentToken());
            }
            if (tokenizer.hasNext()){
                tokenizer.next();
                tokens.add(tokenizer.getCurrentToken());
            }
            Exp term = new NullExp();

            // check if three tokens contain elements of "Fixed", "Comparison" and "Time/Number/Letter"
            if (checkThreeElements(tokens)){
                 term = threeEvaluate(tokens);
            }
            tokenizer.next();
            // if there are still tokens after the combination
            if (tokenizer.hasNext()){
                Exp exp = parseExp();
                return new EvaluationExp(term, exp);
            }
            // if no more tokens, then just return this one
            return term;
        }

        // if the token is started with a type of "Fixed"
        if (tokenizer.getCurrentToken() != null && tokenizer.getCurrentToken().get_Type().equals(Token.Type.Fixed)) {

            // if the fixed type has value "from" then it represents departure place, and with tolerance of wrong word spelling
            if (tokenizer.getCurrentToken().get_Token().equalsIgnoreCase("from")
                    || similarity(tokenizer.getCurrentToken().get_Token(), "from") >= 0.75) {
                tokenizer.next();
                if (!tokenizer.getCurrentToken().get_Type().equals(Token.Type.Comparison) &&
                        !tokenizer.getCurrentToken().get_Type().equals(Token.Type.Unknown)) {
                    error = "require a \"=\" after \"from\"";
                    throw  (new Exception(error));
                }

                // the term is from location
                Exp term = parseLocationFromTerm();
                if (tokenizer.hasNext() &&!tokenizer.getCurrentToken().get_Type().equals(Token.Type.Semicolon)){
                    throw new Exception("Missing semicolon after \"from\"");
                }
                if (tokenizer.hasNext()) {
                    tokenizer.next();
                    Exp exp = parseExp();
                    return new EvaluationExp(term, exp);
                }
                return term;
            }


            else if (tokenizer.getCurrentToken().get_Token().equalsIgnoreCase("to")
                    || similarity(tokenizer.getCurrentToken().get_Token(), "to") >= 0.75) {
                tokenizer.next();
                if (!tokenizer.getCurrentToken().get_Type().equals(Token.Type.Comparison) &&
                        !tokenizer.getCurrentToken().get_Type().equals(Token.Type.Unknown)) {
                    error = "require a \"=\" after \"to\"";
                    throw  (new Exception(error));
                }

                Exp term = parseLocationToTerm();
                if (tokenizer.hasNext() &&!tokenizer.getCurrentToken().get_Type().equals(Token.Type.Semicolon)){
                    throw new Exception("Missing semicolon after \"to\"");
                }
                if (tokenizer.hasNext()) {
                    tokenizer.next();
                    Exp exp = parseExp();
                    return new EvaluationExp(term, exp);
                }
                return term;
            }
            else if (tokenizer.getCurrentToken().get_Token().equalsIgnoreCase("price")
                    || similarity(tokenizer.getCurrentToken().get_Token(), "price") >= 0.75) {
                tokenizer.next();

                if (!tokenizer.getCurrentToken().get_Type().equals(Token.Type.Comparison) &&
                        !tokenizer.getCurrentToken().get_Type().equals(Token.Type.Unknown)) {
                    error = "require a \"=\" after \"price\"";
                    throw  (new Exception(error));
                }
                Exp term = parsePriceTerm();
                if (tokenizer.hasNext() && !tokenizer.getCurrentToken().get_Type().equals(Token.Type.Semicolon)){
                    throw new Exception("Missing semicolon after \"Price\"");
                }
                if (tokenizer.hasNext()) {
                    tokenizer.next();
                    Exp exp = parseExp();
                    return new EvaluationExp(term, exp);
                }
                return term;
            }
            else if (tokenizer.getCurrentToken().get_Token().equalsIgnoreCase("time")
                    || similarity(tokenizer.getCurrentToken().get_Token(), "time") >= 0.75) {
                tokenizer.next();
                if (!tokenizer.getCurrentToken().get_Type().equals(Token.Type.Comparison) &&
                        !tokenizer.getCurrentToken().get_Type().equals(Token.Type.Unknown)) {
                    error = "require a \"=\" after \"time\"";
                    throw  (new Exception(error));
                }
                Exp term = parseTimeItem();
                if (tokenizer.hasNext() &&!tokenizer.getCurrentToken().get_Type().equals(Token.Type.Semicolon)){
                    throw new Exception("Missing semicolon after \"Time\"");
                }
                if (tokenizer.hasNext() ) {
                    tokenizer.next();
                    Exp exp = parseExp();
                    return new EvaluationExp(term, exp);
                }
                return term;
            }
            else if (tokenizer.getCurrentToken().get_Token().equalsIgnoreCase("noticket")
                    || similarity(tokenizer.getCurrentToken().get_Token(), "noticket") >= 0.75) {
                tokenizer.next();

                if (!tokenizer.getCurrentToken().get_Type().equals(Token.Type.Comparison) &&
                        !tokenizer.getCurrentToken().get_Type().equals(Token.Type.Unknown)) {
                    error = "require a \"=\" after \"Noticket\"";
                    throw  (new Exception(error));
                }
                Exp term = parseNoTicket();
                if (tokenizer.hasNext() &&!tokenizer.getCurrentToken().get_Type().equals(Token.Type.Semicolon)){
                    throw new Exception("Missing semicolon after \"NoTicker\"");
                }
                if (tokenizer.hasNext()) {
                    tokenizer.next();
                    Exp exp = parseExp();
                    return new EvaluationExp(term, exp);
                }
                return term;
            }
        }
        // otherwise return an null expression
            return new NullExp() ;
    }




    public Exp parseLocationFromTerm(){
        // get the comparison symbol
        String comparisonString = parseComparison();
        if (tokenizer.getCurrentToken() == null){
            throw new NullPointerException("missing element after from " + comparisonString) ;
        }
        if (!tokenizer.getCurrentToken().get_Type().equals(Token.Type.Letters)){
            throw new IllegalArgumentException("Expect number after from " + comparisonString);
        }
        if (!comparisonString.equals("=")){
            comparisonString = "=";
        }
        // if the token following the comparison token is of type "Letters"
        if (tokenizer.getCurrentToken().get_Type().equals(Token.Type.Letters)){
            // then get the string of the departure location
            String fromLocation = comparisonString + tokenizer.getCurrentToken().get_Token();
            if (tokenizer.hasNext()){
                tokenizer.next();
            }
            return new LocationFromExp(fromLocation);
        }
        // if the comparison symbol is not followed by a letter or time or number, then it should be invalid
        return new NullExp();
    }

    public Exp parseLocationToTerm(){
        String comparisonString = parseComparison();
        if (tokenizer.getCurrentToken() == null){
            throw new NullPointerException("missing element after to " + comparisonString) ;
        }
        if (!tokenizer.getCurrentToken().get_Type().equals(Token.Type.Letters)){
            throw new IllegalArgumentException("Expect number after to " + comparisonString);
        }
        if (!comparisonString.equals("=")){
            comparisonString = "=";
        }

        if (tokenizer.getCurrentToken().get_Type().equals(Token.Type.Letters)){
            String toLocation = comparisonString + tokenizer.getCurrentToken().get_Token();
            if (tokenizer.hasNext()){
                tokenizer.next();
            }
            return new LocationToExp(toLocation);
        }
        return new NullExp();
    }


    public Exp parsePriceTerm(){

        String comparisonString = parseComparison();
        if (tokenizer.getCurrentToken() == null){
            throw new NullPointerException("missing element after price " + comparisonString) ;
        }
        if (!tokenizer.getCurrentToken().get_Type().equals(Token.Type.Number)){
            throw new IllegalArgumentException("Expect number after price " + comparisonString);
        }
        if (!comparisonString.equals("=") && !comparisonString.equals(">=") &&!comparisonString.equals("<=")

        && !comparisonString.equals(">") && !comparisonString.equals("<")){



            comparisonString = "=";
        }

        if (tokenizer.getCurrentToken().get_Type().equals(Token.Type.Number)){
            // then get the string of the departure location
            String price = comparisonString + tokenizer.getCurrentToken().get_Token();
            if (tokenizer.hasNext()){
                tokenizer.next();
            }
            return new PriceExp(price);
        }
        // if the comparison symbol is not followed by a letter or time or number, then it should be invalid
        return new NullExp();
    }

    public Exp parseTimeItem() {
        String comparisonString = parseComparison();
        if (tokenizer.getCurrentToken() == null){
            throw new NullPointerException("missing element after time " + comparisonString) ;
        }
        if (!tokenizer.getCurrentToken().get_Type().equals(Token.Type.Time)){
            throw new IllegalArgumentException("Expect number after time " + comparisonString);
        }
        if (!comparisonString.equals("=")){
            comparisonString = "=";
        }

        if (tokenizer.getCurrentToken().get_Type().equals(Token.Type.Time)) {
            // then get the string of the departure location
            String time = comparisonString + tokenizer.getCurrentToken().get_Token();
            if (tokenizer.hasNext()) {
                tokenizer.next();
            }
            return new TimeExp(time);
        }
        // if the comparison symbol is not followed by a letter or time or number, then it should be invalid
        return new NullExp();
    }


    public Exp parseNoTicket(){
        String comparisonString = parseComparison();
        if (!comparisonString.equals("=") ){
            comparisonString = "=";
        }
        if (tokenizer.getCurrentToken().get_Type().equals(Token.Type.Number)) {
            // then get the string of the departure location
            String noTicket = comparisonString + tokenizer.getCurrentToken().get_Token();
            if (tokenizer.hasNext()) {
                tokenizer.next();
            }
            return new NoTicketExp(noTicket);
        }
        // if the comparison symbol is not followed by a letter or time or number, then it should be invalid
        return new NullExp();
    }

    public String parseComparison(){
        String comparisonSymbol = tokenizer.getCurrentToken().get_Token();
        tokenizer.next();
        // if the comparison symbol is composed of two symbols
        // but will ignore the third comparison symbol according to the grammer
        // return the string of comparison symbols
        return comparisonSymbol;
    }

    public static float similarity(String a, String b){


        int longer = 0;
        if (a.length() > b.length()){
            longer = a.length();
        }
        else {
            longer = b.length();
        }
        if (longer == 0){
            return 1.0f;
        }

        return (longer - getLevenshteinDistance(a.toLowerCase(), b.toLowerCase())) / (float) longer;
    }
    public static int getLevenshteinDistance(String s, String t) {
        if (s == null || t == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }

        int n = s.length();
        int m = t.length();

        if (n == 0) {
            return m;
        } else if (m == 0) {
            return n;
        }

        if (n > m) {
            String tmp = s;
            s = t;
            t = tmp;
            n = m;
            m = t.length();
        }

        int p[] = new int[n + 1];
        int d[] = new int[n + 1];
        int _d[];


        int i;
        int j;

        char t_j;

        int cost;

        for (i = 0; i <= n; i++) {
            p[i] = i;
        }

        for (j = 1; j <= m; j++) {
            t_j = t.charAt(j - 1);
            d[0] = j;

            for (i = 1; i <= n; i++) {
                cost = s.charAt(i - 1) == t_j ? 0 : 1;

                d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
            }
            _d = p;
            p = d;
            d = _d;
        }

        return p[n];
    }

    public boolean checkThreeElements(List<Token> tokens){
        int fix = 0;
        int compare = 0;
        int letter = 0;
        Token fixT = new Token("", Token.Type.Unknown);
        Token comparisonT = new Token("", Token.Type.Unknown);
        Token letterT = new Token("", Token.Type.Unknown);


        for (Token token : tokens){
            if (token == null){
                throw new NullPointerException("missing element after " + tokens.get(1).get_Token()) ;
            }
            if (token.get_Type().equals(Token.Type.Fixed)|| similarity(token.get_Token(), "from") >= 0.75
                    || similarity(token.get_Token(), "to") >= 0.75 || similarity(token.get_Token(), "time") >= 0.75
                    || similarity(token.get_Token(), "Noticke") >= 0.75 || similarity(token.get_Token(), "price") >= 0.75){
                fix ++;
                fixT = token;
            }
            else if (token.get_Type().equals(Token.Type.Comparison) || token.get_Type().equals(Token.Type.Unknown)){
                compare ++;
                comparisonT = token;
            }
            else if (token.get_Type().equals(Token.Type.Letters) || token.get_Type().equals(Token.Type.Number)  || token.get_Type().equals(Token.Type.Time)){
                letter ++;
                letterT = token;
            }

        }
        if (!tokens.get(1).get_Type().equals(Token.Type.Comparison) && !tokens.get(1).get_Type().equals(Token.Type.Unknown)){
            error = "must be a \"=\" between " + fixT.get_Token() + " and " + letterT.get_Token();
            throw new IllegalArgumentException(error);
        }

        return (fix == 1 && letter == 1 && compare ==1);
    }

    public Exp threeEvaluate(List<Token> tokens){
        Token fix = new Token("", Token.Type.Unknown);
        Token comparison = new Token("", Token.Type.Unknown);
        Token letter = new Token("", Token.Type.Unknown);
        for (Token t : tokens){
            if(t.get_Type().equals(Token.Type.Fixed) || similarity(t.get_Token(), "from") >= 0.75
                    || similarity(t.get_Token(), "to") >= 0.75 || similarity(t.get_Token(), "time") >= 0.75
                    || similarity(t.get_Token(), "Noticke") >= 0.75 || similarity(t.get_Token(), "price") >= 0.75){
                fix = t;
            }
            else if(t.get_Type().equals(Token.Type.Comparison)|| t.get_Type().equals(Token.Type.Unknown)){
                if (t.get_Type().equals(Token.Type.Unknown)){
                    comparison = new Token("=", Token.Type.Comparison);
                }
                else {
                    comparison = t;
                }

            }
            else if(t.get_Type().equals(Token.Type.Letters)|| t.get_Type().equals(Token.Type.Number)  || t.get_Type().equals(Token.Type.Time)){
                letter = t;
            }
        }

        if (fix.get_Token().equalsIgnoreCase("from")
                || similarity(fix.get_Token(), "from") >= 0.75){
            return new LocationFromExp(comparison.get_Token() + letter.get_Token());
        }
        if (fix.get_Token().equalsIgnoreCase("to")
                || similarity(fix.get_Token(), "to") >= 0.75){
            return new LocationToExp(comparison.get_Token() + letter.get_Token());
        }
        if (fix.get_Token().equalsIgnoreCase("time")
                || similarity(fix.get_Token(), "time") >= 0.75){
            return new TimeExp(comparison.get_Token() + letter.get_Token());
        }
        if (fix.get_Token().equalsIgnoreCase("NoTicket")
                || similarity(fix.get_Token(), "Noticke") >= 0.75){
            return new NoTicketExp(comparison.get_Token() + letter.get_Token());
        }
        if (fix.get_Token().equalsIgnoreCase("price")
                || similarity(fix.get_Token(), "price") >= 0.75){
            return new PriceExp(comparison.get_Token() + letter.get_Token());
        }


        return null;
    }



}
