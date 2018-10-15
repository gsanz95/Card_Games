package com.company;

class BlackJack {

    private int NUMBER_OF_STARTING_CARDS;
    private Deck cardDeck;
    private Deck playerHand;
    private Deck dealerHand;
    private GameLogger logger;

    /**
     * Constructor
     */
    public BlackJack(Deck playerHand, Deck dealerHand) {
        this.logger = new GameLogger();
        this.NUMBER_OF_STARTING_CARDS = 2;
        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
    }

    public void setDeck(Deck deckToSet) {
        this.cardDeck = deckToSet.clone();
    }

    /**
     * Starts the game mode
     */
    void startGame(){
        dealCards(playerHand);
        //logger.printDeck(playerHand);

        dealCards(dealerHand);

        //logger.printDeck(dealerHand);
        //logger.printPossibleScores(possibleScores);
    }

    void dealCards(Deck destinationDeck) {
        Card[] dealtCards = new Card[NUMBER_OF_STARTING_CARDS];

        for(int i = 0; i < NUMBER_OF_STARTING_CARDS; i++)
            dealtCards[i] = cardDeck.popCard();

        for(Card singleCard : dealtCards){
            destinationDeck.addCard(singleCard);
            if(singleCard.getRank() >= 10)
                destinationDeck.addToScore(10);
            else
                destinationDeck.addToScore(singleCard.getRank());
        }

    }

    int getIdealAction() {
        Card drawnCard;
        int currentScore = playerHand.getDeckScore();
        if(currentScore < 21) {
            drawnCard = cardDeck.popCard();
            playerHand.addCard(drawnCard);
            if(drawnCard.getRank() >= 10)
                playerHand.addToScore(10);
            else
                playerHand.addToScore(drawnCard.getRank());

            if(playerHand.getDeckScore() < 21)
                return 1;
        }

        return 0;
    }


    void playDealerRound() {
        while(dealerHand.getDeckScore() < 17) {
            Card drawnCard = cardDeck.popCard();

            dealerHand.addCard(drawnCard);
            if(drawnCard.getRank() >= 10)
                dealerHand.addToScore(10);
            else
                dealerHand.addToScore(drawnCard.getRank());
        }
    }

    /**
     * Takes the decks that need to be counted for points and finds the winner
     * if two have the same number, printTie is called.
     *
     */
    void finalizeGame() {
        this.playerHand.clearDeck();
        this.dealerHand.clearDeck();
    }
}
