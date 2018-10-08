package com.company;

import javax.management.InvalidAttributeValueException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class prepares all objects for the game.
 */
class PrepareGame{

    /**
     * This method creates a new deck containing 52 shuffled cards (1-13 cards for each suit).
     *
     * @return Deck New deck containing 52 card objects.
     */
    Deck makeDeck() {
        Deck cardDeck = new Deck("");

        for(Suit cardSuit : Suit.values()){
            for(int i = 1; i <= 13; i++){
                cardDeck.addCard(new Card(i, cardSuit));
            }
        }

        Collections.shuffle(cardDeck.getCards());

        return cardDeck;
    }

    /**
     * This method creates an array of decks based on numberOfPlayers and fills them evenly with
     * cards from the deckToSplit. If the deck cannot be split evenly, any excess cards are not added.
     *
     * @param deckToSplit Deck that contains all the cards relevant to the game.
     * @param numberOfPlayers Number of players in the game.
     * @return Array containing all player decks.
     * @throws InvalidAttributeValueException Invalid number of players.
     */
    Deck[] splitDeck(Deck deckToSplit, int numberOfPlayers) throws InvalidAttributeValueException {
        if(numberOfPlayers <= 1){
            throw new InvalidAttributeValueException("Number of players cannot be less than 1: " + numberOfPlayers);
        }

        int playerDeckSize = (int) Math.floor(deckToSplit.getCards().size()/numberOfPlayers);
        ArrayList<Card> mainDeck = deckToSplit.getCards();

        Deck[] playerDecks = new Deck[numberOfPlayers];
        String[] playerNames = new String[]{"Hans", "Gretel", "Witch"};

        for (int i = 0; i < numberOfPlayers; i++){
            playerDecks[i] = new Deck(playerNames[i]);

            // Adding cards to their respective deck
            for(int j = 0; j < playerDeckSize; j++){
                playerDecks[i].addCard(mainDeck.get(j + (playerDeckSize * i)));
            }
        }

        return playerDecks;
    }



}
