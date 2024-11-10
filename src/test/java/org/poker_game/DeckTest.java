package org.poker_game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.poker_game.enums.DeckStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DeckTest {
    public static List<Player> players = new ArrayList<>();
    public static Deck deck;
    private static final String[] names = {"Tommy", "Grace", "Sheldon", "Penny"};

    @Mock
    private DeckStatus status;

    @BeforeAll
    static void setup() throws Exception {
        System.out.println("Create 4 players and add to deck...");
        for (var name : names){
            players.add(new Player(name));
        }

        deck = new Deck(players);
        deck.dealingCards();
    }

    @Test
    @DisplayName("Initiate Deck Object successfully")
    void itShouldInitiateDeck() {
        Assertions.assertEquals(DeckStatus.NULL, deck.getStatus());
        Assertions.assertEquals(4, deck.getPlayers().size());
        Assertions.assertEquals(52, deck.getStandardCardOnDeck().size());
        Assertions.assertEquals(0, deck.getCardOnDeck().size());
    }

    @Test
    void openCard() {
        // Given
        Deck deckTest = new Deck(players);

        // Test Flop state
        openCardFlop(deckTest);

        // Test Turn state
        openCardTurn(deckTest);

        // Test River state
        openCardRiver(deckTest);
    }

    private void openCardFlop(Deck deck) {
        // when
        deck.openCard(); // Flop

        // then
        Assertions.assertEquals(4, deck.getCardOpenedCount(), "Flop: Incorrect card opened count");
        Assertions.assertEquals(DeckStatus.FLOP, deck.getStatus(), "Flop: Incorrect deck status");
        Assertions.assertEquals(3, deck.getCardOnDeck().size(), "Flop: Incorrect cards on deck count");
    }

    private void openCardTurn(Deck deck) {
        // when
        deck.openCard(); // Turn

        // then
        Assertions.assertEquals(6, deck.getCardOpenedCount(), "Turn: Incorrect card opened count");
        Assertions.assertEquals(DeckStatus.TURN, deck.getStatus(), "Turn: Incorrect deck status");
        Assertions.assertEquals(4, deck.getCardOnDeck().size(), "Turn: Incorrect cards on deck count");
    }

    private void openCardRiver(Deck deck) {
        // when
        deck.openCard(); // River

        // then
        Assertions.assertEquals(8, deck.getCardOpenedCount(), "River: Incorrect card opened count");
        Assertions.assertEquals(DeckStatus.RIVER, deck.getStatus(), "River: Incorrect deck status");
        Assertions.assertEquals(5, deck.getCardOnDeck().size(), "River: Incorrect cards on deck count");
    }


    @Test
    void givenCards_WhenDealingCards_ShouldPlayersHavingCorrectCards() {
        Assertions.assertEquals(2, players.get(0).getCardOnHand().size());
        Assertions.assertEquals(2, players.get(1).getCardOnHand().size());
        Assertions.assertEquals(2, players.get(2).getCardOnHand().size());
        Assertions.assertEquals(2, players.get(3).getCardOnHand().size());
    }

    @Test
    void givenCards_WhenDealingWrongCards_ShouldRaiseError() {
        // Given
        Player player1 = Mockito.mock(Player.class);
        Player player2 = Mockito.mock(Player.class);
        Player player3 = Mockito.mock(Player.class);
        Player player4 = Mockito.mock(Player.class);

        List<Player> playersTest = Mockito.mock(List.class);
        playersTest.addAll(List.of(
                player1, player2, player3, player4
        ));
        deck = new Deck(playersTest);

        // When
        when(player1.receiveCard(any(Card.class))).thenReturn(true);
        when(player2.receiveCard(any(Card.class))).thenReturn(true);
        when(player3.receiveCard(any(Card.class))).thenReturn(true);
        when(player4.receiveCard(any(Card.class))).thenReturn(false);

        // Then
        Assertions.assertThrows(Exception.class,() -> {
            deck.dealingCards();
        }, "Cannot deal cards to players");
    }



    @Test
    @DisplayName("It should deal cards properly and has matching cards on deck")
    void itShouldDealCardsProperly() {
        // Given
        // Deck in the before all

        // When
        deck.openCard(); // FLOP
        deck.openCard(); // TURN
        deck.openCard(); // RIVER

        // Then
        int cardsOpenedCount = 8; // 8 cards were dealt to each user
        List<Card> cardOnDeckExpected = deck.getCardOnDeck();
        List<Card> cardOnDeckActual = deck.getStandardCardOnDeck();
        int cardToOpen = 0;

        // FLOP
        cardsOpenedCount += 1; // Cut 1 card
        Assertions.assertEquals(cardOnDeckExpected.subList(cardToOpen, cardToOpen + 3), cardOnDeckActual.subList(cardsOpenedCount, cardsOpenedCount + 3));

        // TURN
        cardToOpen += 3;
        cardsOpenedCount += 1; // Cut 1 card
        cardsOpenedCount += 3; // Skip next three 3 cards
        Assertions.assertEquals(cardOnDeckExpected.subList(cardToOpen, cardToOpen + 1), cardOnDeckActual.subList(cardsOpenedCount, cardsOpenedCount + 1));

        // RIVER
        cardToOpen += 1;
        cardsOpenedCount += 1; // Cut 1 card
        cardsOpenedCount += 1; // Skip next three 1 cards
        Assertions.assertEquals(cardOnDeckExpected.subList(cardToOpen, cardToOpen + 1), cardOnDeckActual.subList(cardsOpenedCount, cardsOpenedCount + 1));
    }
}