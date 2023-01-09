package com.example.comp2100groupproject;

public class Token {


    static final String[] fixed_Words = {"from", "to", "price", "time", "noticket"};
    static final char[] compare_Symbols = {'>', '<', '='};
    public enum  Type {Unknown, Fixed, Comparison, Time, Number, Letters, Semicolon};
    private String token = "";
    private Type type = Type.Unknown;

    public Token (String token, Type type){
        this.token = token;
        this.type = type;
    }

    public String get_Token(){return this.token;}
    public Type get_Type(){return this.type;}

}
