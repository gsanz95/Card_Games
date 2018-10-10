package com.company;

import java.util.ArrayList;

class GameLogger {

    /**
     * Prints all the cards inside of the deck passed.
     *
     * @param cardDeck Deck class containing the card classes
     */
    void printDeck(Deck cardDeck){
        ArrayList<Card> cards = cardDeck.getCards();
        int cardCount = 0;

        System.out.println(cardDeck.getOwnerName());
        for (Card card : cards) {
            System.out.println(card.toString());
            cardCount += 1;
        }

        //System.out.println("Total Cards: " + cardCount);
    }

    /**
     * Prints message of gameMode starting.
     *
     * @param gameMode Name of the mode started
     */
    void printGameStart(String gameMode){
        System.out.println();
        System.out.println("Starting: " + gameMode);
    }

    /**
     * Prints the names of the owners of the decks and the cards they played.
     *
     * @param decksInPlay Decks of players
     * @param cardsPlayed Cards played
     */
    void printCardsPlayed(Deck[] decksInPlay, ArrayList<Card> cardsPlayed){
        for(int i = 0; i < decksInPlay.length; i++){
            System.out.println(decksInPlay[i].getOwnerName() + " plays " + cardsPlayed.get(i).toString() + " as up card");
        }
    }

    /**
     * Prints the owner name of the deck that won the round
     *
     * @param winnerDeck Deck that won the round.
     */
    void printRoundWinner(Deck winnerDeck){
        System.out.println(winnerDeck.getOwnerName() + " wins the round.");
    }

    /**
     * Prints War!
     */
    void printWar(){
        System.out.println("War!");
    }

    void printPossibleScores(int[][] scoresToPrint) {
        StringBuilder outputText = new StringBuilder();
        for(int i = 0; i < 2; i++) {
            outputText.append("Possible Score: ");

            for(int j = 0; j < 2; j++)
                outputText.append(scoresToPrint[i][j]).append(" ");

            outputText.append("\n");
        }

        System.out.println(outputText.toString());
    }

    /**
     * Prints the scores of all the decks received.
     *
     * @param playerDecks Decks relevant to the game
     */
    void printScores(Deck[] playerDecks) {
        StringBuilder outputText = new StringBuilder();
        outputText.append("Score is ");

        for(int i = 0; i < playerDecks.length; i++){
            outputText.append(playerDecks[i].toString());
            if(i < playerDecks.length - 1)
                outputText.append(", ");
        }

        System.out.println(outputText.toString());
    }

    /**
     * Prints the owner of the winning deck.
     *
     * @param winnerDeck Deck of the winning player
     */
    void printGameWinner(Deck winnerDeck){
        System.out.println(winnerDeck.getOwnerName() + " wins!");
    }

    /**
     * Prints that a game is tied.
     */
    void printGameTie() { System.out.println("Tied Game!");}
}
