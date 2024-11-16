package org.poker_game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.poker_game.enums.Pattern;
import org.poker_game.enums.Suit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.poker_game.Util.convertToCard;
import static org.poker_game.Util.convertToSuit;

class AggregatorTest {
    @Mock
    Player player;

    static String[][] testCases = {
            // 1. High Card (Example: No pairs, no straights, no flushes)
            {"♠A", "♠3", "♣5", "♦9", "♥2", "♠7", "♦J", "High Card"},  // Ace-high

            // Edge Case: Low High Card (Example: No pairs, Ace as the lowest)
            {"♠2", "♠3", "♣5", "♦7", "♥8", "♠10", "♣K", "High Card"},  // 10-high

            // 2. One Pair (Example: Two cards of the same rank)
            {"♠2", "♥2", "♣5", "♦9", "♠K", "♠J", "♦7", "One Pair"},  // One pair of 2s

            // Edge Case: Pair of Aces (Example: High pair)
            {"♠A", "♥A", "♣5", "♦9", "♠K", "♠J", "♦7", "One Pair"},  // Pair of Aces

            // 3. Two Pair (Example: Two separate pairs)
            {"♠3", "♣3", "♥7", "♦7", "♠Q", "♣8", "♦6", "Two Pair"},  // Two pairs: 3s and 7s

            // Edge Case: Two Pair with Aces and a low pair
            {"♠A", "♥A", "♣2", "♦2", "♠K", "♣8", "♦6", "Two Pair"},  // Two pairs: Aces and 2s

            // 4. Three of a Kind (Example: Three cards of the same rank)
            {"♦10", "♠10", "♥10", "♣2", "♠Q", "♦5", "♠K", "Three of a Kind"},  // Three 10s

            // Edge Case: Three of a Kind with Aces
            {"♠A", "♣A", "♦A", "♥2", "♠Q", "♦J", "♠9", "Three of a Kind"},  // Three Aces

            // 5. Straight (Example: Five consecutive cards, not the same suit)
            {"♠5", "♣6", "♦7", "♥8", "♠9", "♠10", "♣J", "Straight"},  // 5, 6, 7, 8, 9 straight

            // Edge Case: Ace-low Straight (Example: Ace is low in a 5-4-3-2-Ace straight)
            {"♠A", "♣2", "♦3", "♥4", "♠5", "♠8", "♦K", "Straight"},  // Ace-low straight (5-4-3-2-A)

            // Edge Case: Highest Straight (Example: 10-J-Q-K-A straight)
            {"♠10", "♣J", "♦Q", "♥K", "♠A", "♠5", "♣8", "Straight"},  // 10-J-Q-K-A straight

            // 6. Flush (Example: Five cards of the same suit, not in sequence)
            {"♠3", "♠5", "♠7", "♠9", "♠J", "♠8", "♠K", "Flush"},  // Flush with ♠

            // Edge Case: Minimum Flush (Example: 5 cards of the same suit, not sequential)
            {"♠2", "♠4", "♠6", "♠8", "♠10", "♠J", "♠K", "Flush"},  // Flush with ♠, no sequence

            // 7. Full House (Example: Three of a kind + a pair)
            {"♦4", "♠4", "♣4", "♥6", "♠6", "♦J", "♠A", "Full House"},  // Three 4s and two 6s

            // Edge Case: Full House with Aces (Example: Three Aces + pair of Kings)
            {"♠A", "♥A", "♦A", "♣K", "♠K", "♣Q", "♦J", "Full House"},  // Three Aces and two Kings

            // 8. Four of a Kind (Example: Four cards of the same rank)
            {"♠8", "♣8", "♦8", "♥8", "♠J", "♣3", "♦7", "Four of a Kind"},  // Four 8s

            // Edge Case: Four of a Kind with Aces
            {"♠A", "♣A", "♦A", "♥A", "♠K", "♣2", "♦5", "Four of a Kind"},  // Four Aces

            // 9. Straight Flush (Example: Five consecutive cards of the same suit)
            {"♠3", "♠4", "♠5", "♠6", "♠7", "♠8", "♠9", "Straight Flush"},  // Straight flush with ♠

            // Edge Case: Lowest Straight Flush (Example: 5-4-3-2-Ace straight flush)
            {"♠A", "♠2", "♠3", "♠4", "♠5", "♠8", "♠K", "Straight Flush"},  // Ace-low straight flush

            // Edge Case: Highest Straight Flush (Example: 10-J-Q-K-A straight flush)
            {"♠10", "♠J", "♠Q", "♠K", "♠A", "♠3", "♠5", "Straight Flush"},  // 10-J-Q-K-A straight flush

            // 10. Royal Flush (Example: 10, J, Q, K, A of the same suit)
            {"♠10", "♠J", "♠Q", "♠K", "♠A", "♠3", "♠5", "Royal Flush"},  // Royal flush with ♠

            // Edge Case: Royal Flush with a different suit (Example: 10, J, Q, K, A of ♥)
            {"♥10", "♥J", "♥Q", "♥K", "♥A", "♠6", "♦2", "Royal Flush"}  // Royal flush with ♥
    };

    public static Pattern mappingPatterns(String pattern){
        return switch (pattern){
            case "High Card" -> Pattern.HIGH_CARD;
            case "One Pair" -> Pattern.ONE_PAIR;
            case "Two Pair" -> Pattern.TWO_PAIR;
            case "Three of a Kind" -> Pattern.THREE_OF_A_KIND;
            case "Full House" -> Pattern.FULL_HOUSE;
            case "Four of a Kind" -> Pattern.FOUR_OF_A_KIND;
            case "Straight" -> Pattern.STRAIGHT;
            case "Flush" -> Pattern.FLUSH;
            case "Straight Flush" -> Pattern.STRAIGHT_FLUSH;
            case "Royal Flush" -> Pattern.ROYAL_FLUSH;
            default -> throw new IllegalStateException("Unexpected value: " + pattern);
        };
    }

    public static List<TestStructure<Pattern>> cleanData(String[][] data){
        List<TestStructure<Pattern>> solutions = new ArrayList<>();

        for (String[] solution : data){
            List<Card> cards = new ArrayList<>(7);
            for (int i = 0; i < 7; i++){
                String card = solution[i];
                Suit suit = convertToSuit(card.charAt(0));
                String value = card.length() > 2 ?
                        String.valueOf(card.charAt(1)) + card.charAt(2) : String.valueOf(card.charAt(1));
                cards.add(convertToCard(value, suit));
            }
            solutions.add(new TestStructure<>(cards, mappingPatterns(solution[7])));
        }

        return solutions;
    }

    //  Method that supplies the parameters
    private static Stream<Arguments> provideTestCases() {
        List<Arguments> arguments = new ArrayList<>();
        cleanData(testCases).forEach(e -> {
            arguments.add(
                    Arguments.of(e.cards(), e.expectedValue())
            );
        });

        return arguments.stream();
    }

    @ParameterizedTest
    @MethodSource("provideTestCases")
    void evaluate(List<Card> cards, Pattern expectedValue) {
        Aggregator aggregator = new Aggregator();
        Pattern result = aggregator.evaluateSubsequently(cards);
        assertEquals(expectedValue, result);
    }

    @Test
    void evaluateDFS() {
    }
}