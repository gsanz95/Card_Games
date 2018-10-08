package com.company;

abstract class GameMode {

    /**
     * Starts the game mode
     *
     * @param playerDecks Decks containing decks for the players
     */
    abstract void startGame(Deck[] playerDecks);

    /**
     * Takes the decks that need to be counted for points and finds the winner
     * if two have the same number, printTie is called.
     *
     * @param decksToCount Decks containing cards which are the points.
     */
    abstract void finalizeGame(Deck[] decksToCount);

    /**
     * Tries to play the round and prints a message if a deck is empty
     *
     * @param playerDecks decks of the players
     */
    abstract void tryPlayRound(Deck[] playerDecks);

    /**
     * Plays a round by getting a card from each player and finding the winner.
     * If a war is started, playWarRound is called to determine the winner.
     *
     * @param playerDecks Decks for each player
     * @throws EmptyDeckException Thrown if any deck in the game is empty
     */
    abstract void playRound(Deck[] playerDecks) throws EmptyDeckException;

    /**
     * Checks all decks passed to it and returns true if any of them is empty
     *
     * @param decksToCheck grouped decks to be checked for emptiness
     * @return Whether or not any deck is empty
     */
    final boolean isAnyDeckEmpty(Deck[] decksToCheck){
        for(Deck singleDeck: decksToCheck){
            if(singleDeck.isEmpty()) return true;
        }

        return false;
    }
}
