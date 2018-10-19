package com.company;

/**
 *  Main class for the Game of War
 *
 * @author Giancarlo Sanz
 * @version 1.0
 */
public class Main {

    private static int PLAYER_POSITION = 0;
    private static int DEALER_POSITION = 1;
    private static int MAX_ROUNDS = 50;
    private static PrepareGame gamePreparer = new PrepareGame();
    private static GameLogger logger = new GameLogger();

    /**
     * Main method
     */
    public static void main(String[] args){

        //playUnlimitedWar(mainDeck);
        //playThreePlayerWar(mainDeck);
        //playClassicWar(mainDeck);
        playBlackJack();
    }

    /**
     * Takes the deck and plays Unlimited War(limited for testing purposes) with
     * two players.
     *
     * @param mainDeck Deck containing all cards relevant to this game
     */
    private static void playUnlimitedWar(Deck mainDeck){
        int numberOfPlayers = 2;
        Deck[] playerDecks;

        // Try to split deck
        try {
            playerDecks = gamePreparer.splitDeck(mainDeck, numberOfPlayers);
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        logger.printGameStart("Unlimited War");
        UnlimitedWar warGame = new UnlimitedWar(MAX_ROUNDS, numberOfPlayers);
        warGame.startGame(playerDecks);
    }

    /**
     * Launches unlimited war but with three players.
     *
     * @param mainDeck Deck containing all relevant cards.
     */
    private static void playThreePlayerWar(Deck mainDeck) {
        int numberOfPlayers = 3;
        Deck[] playerDecks;

        // Try to split deck
        try {
            playerDecks = gamePreparer.splitDeck(mainDeck, numberOfPlayers);
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        logger.printGameStart("Three Player War");
        UnlimitedWar warGame = new UnlimitedWar(MAX_ROUNDS,numberOfPlayers);
        warGame.startGame(playerDecks);
    }

    /**
     * Launches war with classic rules. Two players.
     *
     * @param mainDeck Deck containing all relevant cards.
     */
    static void playClassicWar(Deck mainDeck){
        int numberOfPlayers = 2;
        Deck[] playerDecks;

        // Try to split deck
        try {
            playerDecks = gamePreparer.splitDeck(mainDeck, numberOfPlayers);
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        logger.printGameStart("Classic War");
        ClassicWar warGame = new ClassicWar(numberOfPlayers);
        warGame.startGame(playerDecks);
    }

    static void playBlackJack(){
        Deck[] decksInPlay = new Deck[2];

        decksInPlay[PLAYER_POSITION] = new Deck("Player");
        decksInPlay[DEALER_POSITION] = new Deck("Dealer");

        Deck mainDeck = gamePreparer.makeDeck();

        NetworkController networkControl = new NetworkController();

        BlackJack blackJackGame = new BlackJack(decksInPlay[PLAYER_POSITION], decksInPlay[DEALER_POSITION]);

        // 0 to Hold, 1 to Draw
        for(int i = 0; i < 5000; i++) {
            mainDeck.shuffleDeck();
            blackJackGame.setDeck(mainDeck);
            blackJackGame.startGame();

            int idealAction = blackJackGame.getIdealAction();
            double[] inputs = {decksInPlay[PLAYER_POSITION].getDeckScore(),
                                decksInPlay[DEALER_POSITION].getDeckScore()};
            networkControl.runNetwork(inputs, idealAction, 0);

            idealAction = blackJackGame.getIdealAction();
            inputs[0] = decksInPlay[PLAYER_POSITION].getDeckScore();
            inputs[1] = decksInPlay[DEALER_POSITION].getDeckScore();
            networkControl.runNetwork(inputs, idealAction, 1);

            blackJackGame.playDealerRound();
            blackJackGame.finalizeGame();

            if(i > 9998)
                System.out.println(networkControl.getTotal());
        }


        // TO-DO Print final network
    }
}