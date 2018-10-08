package com.company;

import java.util.ArrayList;

/**
 * Plays Game of War with normal rules (with any number of players).
 * Meaning every time a round is won, the cards count are put aside and
 * counted as 1 point for each card.
 */
class ClassicWar extends GameMode{

    private int MAX_TIED_PLAYERS;
    private int numberOfPlayers;
    private GameLogger logger;
    private GameController gameControl;

    /**
     * Constructor
     *
     * @param numberOfPlayers Number of players playing in the game
     */
    public ClassicWar(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;

        this.MAX_TIED_PLAYERS = 2;
        this.logger = new GameLogger();
        this.gameControl = new GameController();
    }

    /**
     * Starts the game mode
     *
     * @param playerDecks Decks containing decks for the players
     */
    void startGame(Deck[] playerDecks){
        while(isAnyDeckEmpty(playerDecks)){
            tryPlayRound(playerDecks);
        }

        finalizeGame(playerDecks);
    }

    /**
     * Tries to play the round and prints a message if a deck is empty
     *
     * @param playerDecks decks of the players
     */
    void tryPlayRound(Deck[] playerDecks){
        try{
            playRound(playerDecks);
        }catch (EmptyDeckException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Plays a round by getting a card from each player and finding the winner.
     * If a war is started, playWarRound is called to determine the winner.
     *
     * @param playerDecks Deck for each player
     * @throws EmptyDeckException Thrown if any deck in the game is empty
     */
    void playRound(Deck[] playerDecks) throws EmptyDeckException{
        ArrayList<Card> cardsPlayed = gameControl.popPlayerCards(playerDecks, numberOfPlayers);
        logger.printCardsPlayed(playerDecks,cardsPlayed);

        Deck winnerDeck = gameControl.determineRoundWinner(playerDecks, cardsPlayed);

        if(winnerDeck == null){
            try{
                winnerDeck = playWarRound(playerDecks, cardsPlayed);
            }catch (EmptyDeckException e){
                throw new EmptyDeckException(e.getMessage());
            }
        }

        winnerDeck.addToScore(cardsPlayed.size());
        logger.printRoundWinner(winnerDeck);
        logger.printScores(playerDecks);
    }

    /**
     * Plays extra rounds of war until a winner has been decided.
     *
     *
     * @param playerDecks Decks for all the players
     * @param cardsPlayed previously played cards in the round the function is called
     * @return The position of the actual winner of the round
     */
    Deck playWarRound(Deck[] playerDecks, ArrayList<Card> cardsPlayed){
        if(isAnyDeckEmpty(playerDecks)) throw new EmptyDeckException("War stopped: Empty Deck");

        Deck winningDeck = null;
        Deck[] tiedDecks = new Deck[MAX_TIED_PLAYERS];

        while(winningDeck == null){
            logger.printWar();

            int[] tiedPlayers = gameControl.determineTiedPlayers(cardsPlayed, MAX_TIED_PLAYERS);

            for (int i = 0; i < MAX_TIED_PLAYERS; i++)
                tiedDecks[i] = playerDecks[tiedPlayers[i]];
            if(isAnyDeckEmpty(tiedDecks)) throw new EmptyDeckException("War stopped: Empty Deck");

            ArrayList<Card> warCards = gameControl.popPlayerCards(tiedDecks, MAX_TIED_PLAYERS);
            logger.printCardsPlayed(tiedDecks, warCards);

            winningDeck = gameControl.determineRoundWinner(tiedDecks, warCards);
            cardsPlayed.addAll(warCards);
        }
        return winningDeck;
    }

    /**
     * Takes the decks that need to be counted for points and finds the winner
     * if two have the same number, printTie is called.
     *
     * @param decksToCount Decks containing cards which are the points.
     */
    void finalizeGame(Deck[] decksToCount){
        int winningPlayerNumber = gameControl.determineGameWinner(decksToCount);

        if(winningPlayerNumber == -1){
            logger.printGameTie();
        }else {
            logger.printGameWinner(decksToCount[winningPlayerNumber]);
        }
    }
}
