package com.example.comp2100groupproject;

public class EvaluationExp extends Exp {

    private Exp term;
    private Exp exp;
    public String query;

    public EvaluationExp(Exp term, Exp exp){
        this.term = term;
        this.exp = exp;
    }

    @Override
    public Query evaluate() {
        Query termQuery = term.evaluate();
        String termString = term.getQuery();

        Query expQuery = exp.evaluate();
        String expString = exp.getQuery();
        query = termString + expString;
            if (term instanceof LocationFromExp) {
                if (expQuery.departure != null){
                    throw new IllegalArgumentException("multiple departure place entered");
                }
                expQuery.departure = termQuery.departure;
            }
            if (term instanceof LocationToExp) {
                if (expQuery.destination != null){
                    throw new IllegalArgumentException("multiple destination entered");
                }
                expQuery.destination = termQuery.destination;
            }
            if (term instanceof PriceExp) {
                if (expQuery.price != null){
                    throw new IllegalArgumentException("multiple price entered");
                }
                expQuery.price = termQuery.price;
            }
            if (term instanceof TimeExp) {
                if (expQuery.departureTime != null){
                    throw new IllegalArgumentException("multiple departureTime entered");
                }
                expQuery.departureTime = termQuery.departureTime;
            }
            if (term instanceof NoTicketExp) {
                if (expQuery.NoTicket != null){
                    throw new IllegalArgumentException("multiple NoTicket entered");
                }
                expQuery.NoTicket = termQuery.NoTicket;
            }
            return expQuery;

    }
    public String getQuery(){
        return query;
    }
}
