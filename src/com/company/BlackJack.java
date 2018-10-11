package com.company;

class BlackJack {

    private int STARTING_CARDS_NUMBER;
    private Deck cardDeck;
    private Deck playerHand;
    private int[] possibleScores;
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
        this.possibleScores = new int[2];
    }

    /**
     * Starts the game mode
     */
    void startGame(){
        dealCards(playerHand);
        recordScore(possibleScores, playerHand, 0);
        //logger.printDeck(playerHand);

        dealCards(dealerHand);
        recordScore(possibleScores, dealerHand, 1);

        //logger.printDeck(dealerHand);
        //logger.printPossibleScores(possibleScores);
    }

    void dealCards(Deck destinationDeck) {
        Card[] dealtCards = new Card[STARTING_CARDS_NUMBER];

        for(int i = 0; i < STARTING_CARDS_NUMBER; i++)
            dealtCards[i] = cardDeck.popCard();

        for(Card singleCard : dealtCards)
            destinationDeck.addCard(singleCard);
    }

    void recordScore(int[] scores, Deck cardDeck, int handToRecord) {
        for (Card singleCard : cardDeck.getCards()) {
            if(singleCard.getRank() >= 10)
                scores[handToRecord] += 10;
            else
                scores[handToRecord] += singleCard.getRank();
        }
    }

    void sendAction(int actionToPerform) {
        if (actionToPerform == 1)
            playerHand.addCard(cardDeck.popCard());

        logger.printDeck(playerHand);
    }

    void playDealerRound() {

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
