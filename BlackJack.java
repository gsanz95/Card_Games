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

    /**
     * Makes a clone of the passed dekc and assigns that clone
     * to the cardDeck in this class
     */
    public void setDeck(Deck deckToSet) {
        this.cardDeck = deckToSet.clone();
    }

    /**
     * Starts the game by dealing cards to players.
     */
    void startGame(){
        dealCards(playerHand);

        dealCards(dealerHand);
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

    /**
     * Determines whether the player should draw the next card by
     * taking a look at it and seeing if it doesnt pass the
     * scoring limit.
     */
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

    /**
     * Draws until the dealer busts or score > 16
     */
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
     * Takes the decks that need to be counted for points and finds the winner.
     * If both have the same number dealer wins.
     */
    void finalizeGame() {
        this.playerHand.clearDeck();
        this.dealerHand.clearDeck();
    }
}
