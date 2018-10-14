package com.company;

import java.util.Objects;

/**
 * Card class contains attributes pertaining to a single card
 */
public class Card implements Cloneable{

    private Suit cardSuit;
    private int rank;

    /**
     * Constructor
     *
     * @param rankValue (required) Rank of the card class, value MUST be 1 to 13
     * @param suitType (required) Card Suit type of the card class
     */
    public Card(int rankValue, Suit suitType){
        this.cardSuit = suitType;
        this.rank = rankValue;
    }

    // Accessors

    public Suit getCardSuit() {
        return cardSuit;
    }

    public int getRank() {
        return rank;
    }

    /**
     * Compares the card passed to this one.
     *
     * @param itemToCompare A card to compare to this class
     * @return boolean If the cards are equal in rank and suit
     */
    @Override
    public boolean equals(Object itemToCompare){
        if(!(itemToCompare instanceof Card)){
            return false;
        }

        Card cardToCompare = (Card) itemToCompare;
        boolean isEqual = (this.rank == cardToCompare.getRank());

        return isEqual;
    }

    /**
     * Takes Card passed and compares both classes ranks.
     *
     * @param cardToCompare Object to compare rank with this class
     * @return The rank difference between the rank of the two card classes.
     */
    public int compareTo(Card cardToCompare) {
        int comparedValue;

        comparedValue = Integer.compare(this.rank, cardToCompare.getRank());

        return comparedValue;
    }

    /**
     * Makes a clone of this card
     * @return New card object with same rank and suit
     */
    @Override
    protected Card clone() {
        return new Card(this.rank, this.cardSuit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank);
    }

    /**
     * Creates and returns a string containing all the attributes of this class
     *
     * @return Text containing the attributes of this class
     */
    @Override
    public String toString() {
        StringBuilder stringToPrint = new StringBuilder();
        if(this.rank == 11){
            stringToPrint.append("JACK of ");
        }else if(this.rank == 12){
            stringToPrint.append("QUEEN of ");
        }else if(this.rank == 13) {
            stringToPrint.append("KING of ");
        }else{
            stringToPrint.append(this.rank).append(" of ");
        }

        stringToPrint.append(this.cardSuit);
        return stringToPrint.toString();
    }
}
