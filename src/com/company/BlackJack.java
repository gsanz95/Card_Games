package com.company;

class BlackJack extends GameMode {

    private GameLogger logger;
    private GameController gameControl;

    /**
     * Constructor
     */
    public BlackJack() {
        this.logger = new GameLogger();
        this.gameControl = new GameController();
    }

    /**
     * Starts the game mode
     *
     * @param playerDecks Decks containing decks for the players
     */
    void startGame(Deck[] playerDecks){

    }

    /**
     * Takes the decks that need to be counted for points and finds the winner
     * if two have the same number, printTie is called.
     *
     * @param decksToCount Decks containing cards which are the points.
     */
    void finalizeGame(Deck[] decksToCount){

    }

    /**
     * Tries to play the round and prints a message if a deck is empty
     *
     * @param playerDecks decks of the players
     */
    void tryPlayRound(Deck[] playerDecks){

    }

    /**
     * Plays a round by getting a card from each player and finding the winner.
     * If a war is started, playWarRound is called to determine the winner.
     *
     * @param playerDecks Decks for each player
     * @throws EmptyDeckException Thrown if any deck in the game is empty
     */
    void playRound(Deck[] playerDecks) throws EmptyDeckException{

    }
}
