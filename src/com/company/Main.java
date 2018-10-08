package com.company;

/**
 *  Main class for the Game of War
 *
 * @author Giancarlo Sanz
 * @version 1.0
 */
public class Main {

    private static int MAX_ROUNDS = 50;
    private static PrepareGame gamePreparer = new PrepareGame();
    private static GameLogger logger = new GameLogger();

    /**
     * Main method
     */
    public static void main(String[] args){

        Deck mainDeck = gamePreparer.makeDeck();

        playUnlimitedWar(mainDeck);
        playThreePlayerWar(mainDeck);
        playClassicWar(mainDeck);
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
}
