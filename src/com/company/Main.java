package com.company;

import java.util.Scanner;

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

        Deck mainDeck = gamePreparer.makeDeck();

        //playUnlimitedWar(mainDeck);
        //playThreePlayerWar(mainDeck);
        //playClassicWar(mainDeck);
        playBlackJack(mainDeck);
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

    static void playBlackJack(Deck mainDeck){
        Deck[] decksInPlay = new Deck[2];

        decksInPlay[PLAYER_POSITION] = new Deck("Player");
        decksInPlay[DEALER_POSITION] = new Deck("Dealer");

        BlackJack blackJackGame = new BlackJack(decksInPlay[PLAYER_POSITION], decksInPlay[DEALER_POSITION], mainDeck);

        blackJackGame.startGame();

        Scanner actionReader = new Scanner(System.in);

        // 0 to Hold, 1 to Draw
        int playerAction = 1;
        while (playerAction == 1) {
            playerAction = actionReader.nextInt();
            blackJackGame.sendAction(playerAction);


            if(decksInPlay[PLAYER_POSITION].getSize() >= 4)
                break;
        }

        blackJackGame.playDealerRound();

    }
}
