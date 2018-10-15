package com.company;

import java.util.ArrayList;
import java.util.Collections;

/**
 *  Deck class contains a group of card classes
 */
class Deck implements Cloneable{
    private String ownerName;
    private ArrayList<Card> cards;
    private int deckScore;

    /**
     * Constructor
     */
    public Deck(String ownerName) {
        this.cards = new ArrayList<Card>();
        this.ownerName = ownerName;
        this.deckScore = 0;
    }

    // Accessors

    public ArrayList<Card> getCards(){
        return this.cards;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getDeckScore() {
        return deckScore;
    }

    /**
     * This method adds the cardToAdd into the cards ArrayList
     *
     * @param cardToAdd Card class to be added to cards ArrayList
     */
    public void addCard(Card cardToAdd) {
        this.cards.add(cardToAdd);
    }

    /**
     * Checks if the ArrayList cards in this class is empty
     *
     * @return Whether or not the ArrayList cards is empty
     */
    boolean isEmpty(){
        return this.cards.isEmpty();
    }

    /**
     * Removes and returns a card from the ArrayList cards at index 1
     *
     * @return The card removed from cards
     */
    Card popCard(){
        return this.cards.remove(0);
    }

    /**
     * Gets the size of the ArrayList and returns it.
     *
     * @return Size of ArrayList
     */
    int getSize(){
        return cards.size();
    }

    /**
     * Adds pointsToAdd to the deck's score
     *
     * @param pointsToAdd Points to add to this deck's score
     */
    public void addToScore(int pointsToAdd){
        this.deckScore += pointsToAdd;
    }

    /**
     * Builds a string with the deck's owner and the score for this deck
     *
     * @return String containing the deck's owner and the score related to the deck
     */
    public String toString(){
        StringBuilder outputText = new StringBuilder();

        outputText.append(this.ownerName).append(" ").append(this.deckScore);
        return outputText.toString();
    }

    /**
     * Clones the deck and returns a new clone deck
     */
    @Override
    protected Deck clone(){
        Deck clone = new Deck(this.getOwnerName());
            for(Card singleCard : this.cards) clone.addCard(singleCard.clone());

            return clone;
    }

    public void shuffleDeck(){
        Collections.shuffle(this.cards);
    }

    public void clearDeck() {
        this.cards.clear();
        this.deckScore = 0;
    }

}
