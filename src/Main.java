import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Dealing cards to each player
        // Each player has two cards

        // Open cards (flop -> turn -> river)
        // Evaluate each player's cards after each opening turn
        // Print the result of all players after each turn

        // Build Deck
        Player player1 = new Player("Tommy");
        Player player2 = new Player("Grace");
        Player player3 = new Player("Bob");
        Player player4 = new Player("Candy");
        List<Player> players = new ArrayList<>(List.of(
                player1,
                player2,
                player3,
                player4
        ));
        Deck deck = new Deck(players);

        // Dealing Cards
        deck.dealingCards();

        // Print each player's cards
        for (Player player : players){
            System.out.println(player.getCardOnHand());
        }

        // Open flop -> turn -> river
        // Flop
//        deck.openCard();
//        Aggregator.evaluate(player1, deck.getCardOnDeck());
//        System.out.println(deck.getCardOnDeck());
//
//        // Turn
//        deck.openCard();
//        Aggregator.evaluate(player1, deck.getCardOnDeck());
//        System.out.println(deck.getCardOnDeck());
//
//        // River
//        deck.openCard();
//        Aggregator.evaluate(player1, deck.getCardOnDeck());
//        System.out.println(deck.getCardOnDeck());

//        testStraight();
        testFlush();
        System.out.println("-".repeat(50));
        testStraight();
        System.out.println("-".repeat(50));
        testFullHouse();
    }

    public static void testStraight(){
        List<Card> cards = List.of(
                Objects.requireNonNull(Card.getFaceCard(Suit.CLUB, 'A')),
                Objects.requireNonNull(Card.getNumericCard(Suit.DIAMOND, 2)),
                Objects.requireNonNull(Card.getNumericCard(Suit.SPADE, 4)),
                Objects.requireNonNull(Card.getFaceCard(Suit.HEART, 'J')),
                Objects.requireNonNull(Card.getFaceCard(Suit.HEART, 'K'))
        );

        Player player = new Player(List.of(
                Objects.requireNonNull(Card.getNumericCard(Suit.DIAMOND, 3)),
                Objects.requireNonNull(Card.getNumericCard(Suit.DIAMOND, 5))
        ), "Tommy");

        Aggregator.evaluate(player, cards);
    }
    public static void testFlush(){
        List<Card> cards = List.of(
                Objects.requireNonNull(Card.getFaceCard(Suit.DIAMOND, 'A')),
                Objects.requireNonNull(Card.getFaceCard(Suit.DIAMOND, 'Q')),
                Objects.requireNonNull(Card.getNumericCard(Suit.DIAMOND, 10)),
                Objects.requireNonNull(Card.getFaceCard(Suit.DIAMOND, 'J')),
                Objects.requireNonNull(Card.getFaceCard(Suit.DIAMOND, 'K'))
        );

        Player player = new Player(List.of(
                Objects.requireNonNull(Card.getNumericCard(Suit.CLUB, 3)),
                Objects.requireNonNull(Card.getNumericCard(Suit.SPADE, 5))
        ), "Tommy");

        Aggregator.evaluate(player, cards);
    }
    public static void testFullHouse(){
        List<Card> cards = List.of(
                Objects.requireNonNull(Card.getFaceCard(Suit.DIAMOND, 'A')),
                Objects.requireNonNull(Card.getFaceCard(Suit.DIAMOND, 'A')),
                Objects.requireNonNull(Card.getNumericCard(Suit.DIAMOND, 10)),
                Objects.requireNonNull(Card.getFaceCard(Suit.DIAMOND, 'J')),
                Objects.requireNonNull(Card.getFaceCard(Suit.DIAMOND, 'K'))
        );

        Player player = new Player(List.of(
                Objects.requireNonNull(Card.getNumericCard(Suit.CLUB, 10)),
                Objects.requireNonNull(Card.getNumericCard(Suit.SPADE, 10))
        ), "Tommy");

        Aggregator.evaluate(player, cards);
    }
}