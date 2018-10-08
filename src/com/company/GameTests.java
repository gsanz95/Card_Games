package com.company;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.management.InvalidAttributeValueException;
import java.util.ArrayList;

public class GameTests {

    PrepareGame gamePreparer = new PrepareGame();
    GameController gControl = new GameController();
    int numberOfPlayers = 2;

    @Test
    void CardEqualsTest()
    {
        Card card1 = new Card(12, Suit.CLOVERS);
        Card card2 = new Card(12, Suit.HEARTS);
        Card card3 = new Card(11, Suit.DIAMONDS);

        Assertions.assertTrue(card1.equals(card2));
        Assertions.assertFalse(card1.equals(card3));
    }

    @Test
    void InvalidAttributeTest(){

        int numberOfPlayers = 1;
        Deck mainDeck = new Deck("");

        try {
            Deck[] playerCards = gamePreparer.splitDeck(mainDeck, numberOfPlayers);
            Assertions.fail();
        } catch (InvalidAttributeValueException e){
            Assertions.assertEquals("Number of players cannot be less than 1: " + numberOfPlayers, e.getMessage());
        }

    }

    @Test
    void determineTiedPlayersTest(){
        ArrayList<Card> sampleCards = new ArrayList<>();

        sampleCards.add(new Card(1, Suit.HEARTS));
        sampleCards.add(new Card(1, Suit.DIAMONDS));

        int[] realOutput = new int[]{0, 1};
        int[] output = gControl.determineTiedPlayers(sampleCards, numberOfPlayers);
        Assertions.assertArrayEquals(output, realOutput);
    }

    @Test
    void TiedRoundTest(){
        Deck[] decks = createTiedDecks(numberOfPlayers);

        ArrayList<Card> cardsPopped = gControl.popPlayerCards(decks, numberOfPlayers);

        Assertions.assertNull(gControl.determineRoundWinner(decks, cardsPopped));
    }

    Deck[] createTiedDecks(int numberOfPlayers){
        Deck[] tiedDecks = new Deck[numberOfPlayers];

        tiedDecks[0] = new Deck("Hans");
        tiedDecks[1] = new Deck("Gretel");

        tiedDecks[0].addCard(new Card(6, Suit.HEARTS));
        tiedDecks[0].addCard(new Card(11, Suit.DIAMONDS));
        tiedDecks[0].addCard(new Card(8, Suit.SPADES));

        tiedDecks[1].addCard(new Card(6, Suit.CLOVERS));
        tiedDecks[1].addCard(new Card(11, Suit.HEARTS));
        tiedDecks[1].addCard(new Card(8, Suit.HEARTS));

        if(numberOfPlayers > 2) {
            tiedDecks[2] = new Deck("Witch");
            tiedDecks[2].addCard(new Card(6, Suit.SPADES));
            tiedDecks[2].addCard(new Card(11, Suit.DIAMONDS));
            tiedDecks[2].addCard(new Card(8, Suit.HEARTS));
        }
        return tiedDecks;
    }

    @Test
    void WinRoundTest(){
        Deck[] decks = new Deck[numberOfPlayers];

        decks[0] = new Deck("Hans");
        decks[1] = new Deck("Gretel");

        decks[0].addCard(new Card(2, Suit.HEARTS));
        decks[1].addCard(new Card(10, Suit.HEARTS));


        ArrayList<Card> cardsPopped = gControl.popPlayerCards(decks, numberOfPlayers);

        Assertions.assertEquals(decks[1], gControl.determineRoundWinner(decks, cardsPopped));
        Assertions.assertNotEquals(decks[0], gControl.determineRoundWinner(decks, cardsPopped));
    }

    Deck[] createWinDecks(int numberOfPlayers){
        Deck[] winDecks = new Deck[numberOfPlayers];

        winDecks[0] = new Deck("Hans");
        winDecks[1] = new Deck("Gretel");

        winDecks[0].addCard(new Card(1, Suit.HEARTS));
        winDecks[0].addCard(new Card(10, Suit.DIAMONDS));
        winDecks[0].addCard(new Card(6, Suit.SPADES));

        winDecks[1].addCard(new Card(11, Suit.CLOVERS));
        winDecks[1].addCard(new Card(3, Suit.HEARTS));
        winDecks[1].addCard(new Card(5, Suit.HEARTS));

        if(numberOfPlayers > 2) {
            winDecks[2] = new Deck("Witch");
            winDecks[2].addCard(new Card(13, Suit.SPADES));
            winDecks[2].addCard(new Card(11, Suit.DIAMONDS));
            winDecks[2].addCard(new Card(9, Suit.HEARTS));
        }
        return winDecks;
    }

    @Test
    void UnlimitedWarTieTest(){
        Deck[] decks = createTiedDecks(numberOfPlayers);

        UnlimitedWar warGame = new UnlimitedWar(2,numberOfPlayers);


        for(int i = 0; i < decks[0].getCards().size(); i++){
            warGame.tryPlayRound(decks);

            if(warGame.isAnyDeckEmpty(decks)) break;
        }


        Assertions.assertEquals(-1,gControl.determineGameWinner(decks));
    }

    @Test
    void UnlimitedWarWinTest(){
        Deck[] decks = createWinDecks(numberOfPlayers);

        UnlimitedWar warGame = new UnlimitedWar(2,numberOfPlayers);


        for(int i = 0; i < decks[0].getCards().size(); i++){
            warGame.tryPlayRound(decks);

            if(warGame.isAnyDeckEmpty(decks)) break;
        }

        Assertions.assertEquals(0,gControl.determineGameWinner(decks));
    }

    @Test
    void ClassicWarTieTest(){
        Deck[] decks = createTiedDecks(numberOfPlayers);
        ClassicWar warGame = new ClassicWar(numberOfPlayers);

        for(int i = 0; i < 3; i++){
            warGame.tryPlayRound(decks);

            if(warGame.isAnyDeckEmpty(decks)) break;
        }

        Assertions.assertEquals(-1,gControl.determineGameWinner(decks));
    }

    @Test
    void ClassicWarWinTest(){
        Deck[] decks = createWinDecks(numberOfPlayers);

        ClassicWar warGame = new ClassicWar(numberOfPlayers);

        for(int i = 0; i < 3; i++){
            warGame.tryPlayRound(decks);

            if(warGame.isAnyDeckEmpty(decks)) break;
        }

        Assertions.assertEquals(0,gControl.determineGameWinner(decks));
    }

    @Test
    void ThreePlayerWarTieTest(){
        Deck[] decks = createTiedDecks(3);
        UnlimitedWar warGame = new UnlimitedWar(3,numberOfPlayers + 1);

        for(int i = 0; i < decks[0].getCards().size(); i++){
            warGame.tryPlayRound(decks);

            if(warGame.isAnyDeckEmpty(decks)) break;
        }

        Assertions.assertEquals(-1,gControl.determineGameWinner(decks));
    }

    @Test
    void ThreePlayerWarWinTest(){
        Deck[] decks = createWinDecks(3);
        UnlimitedWar warGame = new UnlimitedWar(3,numberOfPlayers + 1);

        for(int i = 0; i < 3; i++){
            warGame.tryPlayRound(decks);

            if(warGame.isAnyDeckEmpty(decks)) break;
        }

        Assertions.assertEquals(2,gControl.determineGameWinner(decks));
    }


}
