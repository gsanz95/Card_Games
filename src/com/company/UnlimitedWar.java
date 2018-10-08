package com.company;

import java.util.ArrayList;

/**
 * Unlimited war lets players take all cards won and put them at
 * the bottom of their decks and reuse them.
 */
class UnlimitedWar extends GameMode{

    private int MAX_TIED_PLAYERS;
    private int numberOfPlayers;
    private GameLogger logger;
    private GameController gameControl;
    private int roundLimit;

    /**
     * Constructor
     *
     * @param maxRounds The number of max rounds
     */
    public UnlimitedWar(int maxRounds, int numberOfPlayers){
        this.roundLimit = maxRounds;
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

        for(int i = 0; i < this.roundLimit; i++){
            tryPlayRound(playerDecks);

            if(isAnyDeckEmpty(playerDecks)) break;
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
            System.err.println(e.getMessage());
        }
    }

    /**
     * Plays a round by getting a card from each player and finding the winner.
     * If a war is started, playWarRound is called to determine the winner.
     *
     * @param playerDecks Decks for each player
     * @throws EmptyDeckException Thrown if any deck in the game is empty
     */
    void playRound(Deck[] playerDecks) throws EmptyDeckException {
        ArrayList<Card> cardsPlayed = gameControl.popPlayerCards(playerDecks, numberOfPlayers);

        logger.printCardsPlayed(playerDecks, cardsPlayed);

        Deck roundWinner = gameControl.determineRoundWinner(playerDecks, cardsPlayed);

        // Has a War been started?
        if(roundWinner == null){
            try{
                 roundWinner = playWarRound(playerDecks, cardsPlayed);
            }catch (EmptyDeckException e){
                throw new EmptyDeckException(e.getMessage());
            }
        }

        roundWinner.addToScore(cardsPlayed.size());
        addCardsToWinner(roundWinner, cardsPlayed);
        logger.printRoundWinner(roundWinner);
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
    Deck playWarRound(Deck[] playerDecks, ArrayList<Card> cardsPlayed) throws EmptyDeckException{
        if(isAnyDeckEmpty(playerDecks)) throw new EmptyDeckException("War stopped: Empty Deck");

        Deck[] tiedDecks = new Deck[MAX_TIED_PLAYERS];
        Deck roundWinner = null;

        while (roundWinner == null) {
            int[] tiedPlayers = gameControl.determineTiedPlayers(cardsPlayed, MAX_TIED_PLAYERS);

            for (int i = 0; i < MAX_TIED_PLAYERS; i++)
                tiedDecks[i] = playerDecks[tiedPlayers[i]];
            if(isAnyDeckEmpty(tiedDecks)) throw new EmptyDeckException("War stopped: Empty Deck");

            ArrayList<Card> warCards = gameControl.popPlayerCards(tiedDecks, MAX_TIED_PLAYERS);
            logger.printWar();
            logger.printCardsPlayed(tiedDecks, warCards);

            roundWinner = gameControl.determineRoundWinner(tiedDecks, warCards);
            cardsPlayed.addAll(warCards);
        }
        return roundWinner;
    }

    /**
     * Takes all the cards in the cards played pile and adds the to the winner's deck
     *
     * @param winnerDeck Deck where the cards will be added into
     * @param cardsPlayed All cards to be put into the winning deck
     */
     void addCardsToWinner(Deck winnerDeck, ArrayList<Card> cardsPlayed){
        for(Card singleCard : cardsPlayed){
            winnerDeck.addCard(singleCard);
        }
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
