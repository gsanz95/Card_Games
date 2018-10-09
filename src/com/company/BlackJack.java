package com.company;

class BlackJack {

    private int STARTING_CARDS_NUMBER;
    private Deck cardDeck;
    private Deck playerHand;
    private Deck dealerHand;
    private GameLogger logger;
    private GameController gameControl;

    /**
     * Constructor
     */
    public BlackJack(Deck playerHand, Deck dealerHand, Deck mainDeck) {
        this.logger = new GameLogger();
        this.gameControl = new GameController();
        this.STARTING_CARDS_NUMBER = 2;
        this.cardDeck = mainDeck;
        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
    }

    /**
     * Starts the game mode
     */
    void startGame(){
        dealCards(playerHand);
        logger.printDeck(playerHand);

        dealCards(dealerHand);
        logger.printDeck(dealerHand);
    }

    void dealCards(Deck destinationDeck) {
        Card[] dealtCards = new Card[STARTING_CARDS_NUMBER];
        for(int i = 0; i < STARTING_CARDS_NUMBER; i++)
            dealtCards[i] = cardDeck.popCard();

        for(Card singleCard : dealtCards)
            destinationDeck.addCard(singleCard);
    }

    void sendAction(int actionToPerform) {
        if (actionToPerform == 1)
            playerHand.addCard(cardDeck.popCard());

        logger.printDeck(playerHand);
    }

    /**
     * Takes the decks that need to be counted for points and finds the winner
     * if two have the same number, printTie is called.
     *
     * @param decksToCount Decks containing cards which are the points.
     */
    void finalizeGame(Deck[] decksToCount){

    }
}
