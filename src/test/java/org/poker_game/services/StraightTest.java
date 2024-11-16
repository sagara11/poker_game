package org.poker_game.services;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.poker_game.Card;
import org.poker_game.TestStructure;
import org.poker_game.enums.Suit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StraightTest {
    static String[][] testCases = {
        // Positive Test Cases (Expected: True)
        {"♠10", "♣J", "♦Q", "♠K", "♥A", "♠3", "♣2", "True"}, // High Ace straight
        {"♦4", "♠5", "♣6", "♠7", "♥8", "♠3", "♣2", "True"}, // Middle straight
        {"♥A", "♣2", "♠3", "♣4", "♦5", "♥6", "♣7", "True"}, // Low Ace straight
        {"♣9", "♦10", "♠J", "♥Q", "♣K", "♣A", "♥3", "True"}, // Straight with mixed suits
        {"♦6", "♠7", "♣8", "♦9", "♣10", "♠J", "♠Q", "True"}, // Another middle straight
        {"♠A", "♠3", "♣5", "♠7", "♠9", "♦2", "♣4", "True"}, // Disjoint cards form Low Ace straight (A, 2, 3, 4, 5)
        {"♠3", "♣4", "♦5", "♠6", "♠7", "♦8", "♠9", "True"}, // Sequential cards in ascending order
        {"♠A", "♠2", "♦3", "♣4", "♥5", "♠7", "♣9", "True"}, // Low Ace straight (A, 2, 3, 4, 5)
        {"♠8", "♦9", "♠10", "♣J", "♠Q", "♠K", "♥A", "True"}, // High Ace straight (10, J, Q, K, A)
        {"♠A", "♠3", "♣5", "♠7", "♠9", "♦2", "♣4", "True"}, // Disjoint cards with no valid sequence
        {"♦A", "♠2", "♠4", "♣5", "♠6", "♦7", "♠8", "True"}, // Multiple sequences but not a valid 5-card straight

        // Negative Test Cases (Expected: False)
        {"♠2", "♠4", "♠6", "♠8", "♠10", "♣Q", "♣K", "False"}, // No consecutive sequence
        {"♣4", "♦5", "♠7", "♥8", "♠10", "♠J", "♦Q", "False"}, // Missing cards for a sequence
        {"♠K", "♠Q", "♣J", "♠10", "♠8", "♠7", "♣6", "False"}, // Out-of-order cards
        {"♠A", "♣K", "♦Q", "♠J", "♥9", "♣7", "♠5", "False"}, // Cards don’t form a sequence
        {"♦3", "♦5", "♠7", "♣9", "♥J", "♠Q", "♣K", "False"}, // Disjoint cards, no valid straight

        // Edge Cases (Expected: True or False based on validity)
        {"♠A", "♣2", "♦3", "♣4", "♠5", "♠7", "♣9", "True"}, // Low Ace straight spanning Ace to 5
        {"♠10", "♣J", "♦Q", "♠K", "♣A", "♥2", "♥3", "True"},  // High Ace straight spanning 10 to Ace
        {"♠A", "♠2", "♠3", "♠4", "♠5", "♠6", "♠7", "True"},  // Low Ace straight with extra cards (A-5)
        {"♠A", "♠3", "♠5", "♠7", "♠9", "♣A", "♣Q", "False"}, // Extra cards that don't form a valid straight
        {"♠A", "♠2", "♠2", "♠4", "♠5", "♠7", "♠8", "False"}, // Duplicate cards, invalid straight
        {"♠A", "♠2", "♠3", "♠5", "♠5", "♠6", "♠7", "False"}  // Duplicate cards breaking the sequence
    };

    private static Suit convertToSuit(char suit){
        return switch ((int) suit){
            case 9827 -> Suit.CLUB;
            case 9830 -> Suit.DIAMOND;
            case 9829 -> Suit.HEART;
            case 9824 -> Suit.SPADE;
            default -> throw new IllegalStateException("Unexpected value: ");
        };
    }

    private static Card convertToCard(String value, Suit suit){
        if (value.length() > 1){
            return Card.getNumericCard(suit, 10);
        } else {
            if (Character.isDigit(value.charAt(0))) {
                return Card.getNumericCard(suit, Character.getNumericValue(value.charAt(0)));
            } else {
                return Card.getFaceCard(suit, value.charAt(0));
            }
        }
    }

    public static List<TestStructure> cleanData(String[][] data){
        List<TestStructure> solutions = new ArrayList<>(11);

        for (String[] solution : data){
            List<Card> cards = new ArrayList<>(7);
            for (int i = 0; i < 7; i++){
                String card = solution[i];
                Suit suit = convertToSuit(card.charAt(0));
                String value = card.length() > 2 ?
                        String.valueOf(card.charAt(1)) + card.charAt(2) : String.valueOf(card.charAt(1));
                cards.add(convertToCard(value, suit));
            }
            solutions.add(new TestStructure(cards, Boolean.parseBoolean(solution[7])));
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
    void isStraightUsingBackTracking(List<Card> cards, boolean expectedValue) {
        boolean result = Straight.isStraightUsingBackTracking(cards);
        assertEquals(expectedValue, result);
    }

    @ParameterizedTest
    @MethodSource("provideTestCases")
    void isStraightUsingSlidingWindow(List<Card> cards, boolean expectedValue) {
        boolean result = Straight.isStraightUsingSlidingWindow(cards);
        assertEquals(expectedValue, result);
    }
}