package com.example.comp2100groupproject;

import java.util.Arrays;

public class Tokenizer {

    private String buffer;
    private Token currentToken;

    public Tokenizer(String text) {
        buffer = text;
        next();
    }

    public void next(){

        buffer = buffer.trim();

        if (buffer.isEmpty()){
            currentToken = null;
            return;
        }

        char firstChar = buffer.charAt(0);
        if (firstChar == ';') {
            currentToken = new Token (";", Token.Type.Semicolon);
        }
        else if (firstChar == '>' || firstChar == '<' || firstChar == '='){

            if (buffer.length() > 1){
                if (buffer.charAt(1) == '>' || buffer.charAt(1) == '<' || buffer.charAt(1) == '=' ){
                    String string = Character.toString(firstChar) + Character.toString(buffer.charAt(1));


                    currentToken = new Token(string, Token.Type.Comparison);
                }
                else {
                    currentToken = new Token(Character.toString(firstChar), Token.Type.Comparison);
                }
            }
            else {
                currentToken = new Token(Character.toString(firstChar), Token.Type.Comparison);
            }
        }
        else if (Character.isLetter(firstChar)){
            int start = 0;
            while (start < buffer.length() && Character.isLetter(buffer.charAt(start))){
                start ++;
            }
            String letterString = buffer.substring(0, start);
            if (Arrays.asList(Token.fixed_Words).contains(letterString.toLowerCase())){
                currentToken = new Token(letterString, Token.Type.Fixed);
            }
            else {
                currentToken = new Token(letterString, Token.Type.Letters);
            }
        }
        else if (Character.isDigit(firstChar)){

            int start = 0;
            boolean isTime = false;

            int slashCount = 0;
            while (start < buffer.length() && (Character.isDigit(buffer.charAt(start)) || buffer.charAt(start) == '/') && (slashCount <= 2) ){

                if (buffer.charAt(start) == '/'){
                    isTime = true;
                    slashCount++;

                }
                    start++;
            }

            String string = buffer.substring(0, start);
            if (isTime){
                if (slashCount == 2){
                currentToken = new Token(string, Token.Type.Time);
                }
                else {
                    currentToken = new Token(string, Token.Type.Unknown);
                }
            }
            else {
                currentToken = new Token(string, Token.Type.Number);
            }
        }
        else{
            int start = 0;
            while (start < buffer.length() && !Character.isDigit(buffer.charAt(start)) && !Character.isLetter(buffer.charAt(start)) && !Character.isSpaceChar(buffer.charAt(start))){
                start++;
            }
            String unknown = buffer.substring(0, start);
            currentToken = new Token(unknown, Token.Type.Unknown);
        }

        int tokenLength = currentToken.get_Token().length();
        buffer = buffer.substring(tokenLength);


    }
    public Token getCurrentToken(){return currentToken;}
    public boolean hasNext(){return currentToken != null;}



}


