import java.util.ArrayList;
import java.util.List;

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
//        CardRules.evaluate(player1, deck.getCardOnDeck());
//        System.out.println(deck.getCardOnDeck());
//
//        // Turn
//        deck.openCard();
//        CardRules.evaluate(player1, deck.getCardOnDeck());
//        System.out.println(deck.getCardOnDeck());
//
//        // River
//        deck.openCard();
//        CardRules.evaluate(player1, deck.getCardOnDeck());
//        System.out.println(deck.getCardOnDeck());
        testStraight();
    }

    public static void testStraight(){
        List<Card> cards = List.of(
                Card.getFaceCard(Suit.HEART, 'A'),
                Card.getNumericCard(Suit.HEART, 2),
                Card.getNumericCard(Suit.HEART, 4),
                Card.getFaceCard(Suit.HEART, 'J'),
                Card.getFaceCard(Suit.HEART, 'K')
        );

        Player player = new Player(List.of(
                Card.getNumericCard(Suit.DIAMOND, 3),
                Card.getNumericCard(Suit.DIAMOND, 5)
        ), "Tommy");

        CardRules.evaluate(player, cards);
    }
}