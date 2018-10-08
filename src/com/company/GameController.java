package com.company;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controls the game by finding winners and manipulates decks by using card and deck methods.
 */
class GameController {

    /**
     * Pops one card from each of the player's decks, groups them, and returns them.
     *
     * @param playerDecks decks pertaining to each player.
     * @param numberOfPlayers number of players in the game.
     * @return Grouped cards popped from all player decks.
     */
    ArrayList<Card> popPlayerCards(Deck[] playerDecks, int numberOfPlayers) {
        ArrayList<Card> cardsPlayed = new ArrayList<>();

        // Pop a card for each player
        for(int i = 0; i < numberOfPlayers; i++){
            cardsPlayed.add(playerDecks[i].popCard());
        }

        return cardsPlayed;
    }

    /**
     * Takes the decks playing this round/war round and compares them with
     * each other. Once a winner is found, their deck is returned.
     *
     * @param playerDecks The decks of the players playing in this round.
     * @param cardsPlayed Cards played in this round.
     * @return Deck of the winner of this round.
     */
    Deck determineRoundWinner(Deck[] playerDecks, ArrayList<Card> cardsPlayed) {
        int highestValue = cardsPlayed.get(0).getRank();
        int numberOfPlayers = cardsPlayed.size();
        Deck roundWinner = playerDecks[0];

        for(int i = 1; i < numberOfPlayers; i++){
            int rankToCompare = cardsPlayed.get(i).getRank();

            if(highestValue < rankToCompare){
                highestValue = rankToCompare;
                roundWinner = playerDecks[i];
            }else if(highestValue == rankToCompare){
                return null;
            }
        }
        return roundWinner;
    }

    /**
     * Takes the cards played and chooses the first maxPlayersAllowed players
     * that have tied cards. Groups their positions and returns them.
     *
     * @param cardsPlayed Cards to evaluated for ties.
     * @param maxPlayersAllowed Number of players that can tie at one time.
     * @return Positions of the tied players relative to all decks.
     */
    int[] determineTiedPlayers(ArrayList<Card> cardsPlayed, int maxPlayersAllowed){
        int[] tiedPlayers = new int[maxPlayersAllowed];

        HashMap<Card, Integer> cardLocation = new HashMap<Card, Integer>();

        for(int i = 0; i < cardsPlayed.size(); i++) {
            if(cardLocation.containsKey(cardsPlayed.get(i))){
                tiedPlayers[0] = cardLocation.get(cardsPlayed.get(i));
                tiedPlayers[1] = i;
                break;
            }

            cardLocation.put(cardsPlayed.get(i), i);
        }

        return tiedPlayers;
    }

    /**
     * Takes all decks in the game and finds a winner by comparing their scores.
     *
     * @param decksToCount All decks relevant to the games.
     * @return The position of the winner relative to all decks.
     */
    int determineGameWinner(Deck[] decksToCount){
        int gameWinner = -1;
        int numberOfPlayers = decksToCount.length;
        int highestScore = -1;

        for(int i = 0; i < numberOfPlayers; i++){
            if (highestScore < decksToCount[i].getDeckScore()) {
                highestScore = decksToCount[i].getDeckScore();
                gameWinner = i;
            }
        }

        for(int i = 0; i < numberOfPlayers; i++)
            if(i != gameWinner)
                if (decksToCount[i].getDeckScore() == highestScore)
                    return -1;

        return gameWinner;
    }
}
