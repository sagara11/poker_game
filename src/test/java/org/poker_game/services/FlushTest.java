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

import static org.junit.jupiter.api.Assertions.assertEquals;

class FlushTest {
    static String[][] testCases = {
        // Positive Test Cases (Expected: True)

        // All cards are the same suit
        {"♠A", "♠3", "♠5", "♠7", "♠9", "♦2", "♣K", "True"},  // Flush with ♠ (spades), not a sequence
        {"♦2", "♦4", "♦6", "♦8", "♦J", "♠3", "♣9", "True"},  // Flush with ♦ (diamonds), not a sequence
        {"♥2", "♥4", "♥6", "♥8", "♥10", "♠A", "♦3", "True"},  // Flush with ♥ (hearts), not a sequence
        {"♣K", "♣Q", "♣J", "♣9", "♣8", "♦4", "♠7", "True"},  // Flush with ♣ (clubs), not a sequence
        {"♠5", "♠7", "♠9", "♠J", "♠Q", "♦2", "♣4", "True"},  // Flush with ♠ (spades), not a sequence

        // Negative Test Cases (Expected: False)

        // Cards are of mixed suits or not all from the same suit
        {"♠A", "♠3", "♦5", "♣7", "♥9", "♦2", "♣K", "False"},  // Mixed suits, not a flush
        {"♦3", "♦5", "♠7", "♣9", "♠J", "♦8", "♣A", "False"},  // Mixed suits, not a flush
        {"♣A", "♣2", "♦4", "♥6", "♠8", "♦J", "♠K", "False"},  // Mixed suits, not a flush
        {"♠9", "♦3", "♠7", "♣2", "♥J", "♠A", "♦K", "False"},  // Mixed suits, not a flush
        {"♠3", "♠5", "♠7", "♦9", "♠J", "♦K", "♣A", "False"},  // Mixed suits, not a flush

        // Edge Cases (Expected: True or False based on validity)

        // Flush with more than 5 cards, still a flush as long as 5 cards of the same suit exist
        {"♠2", "♠4", "♠6", "♠8", "♠10", "♠K", "♠A", "True"},  // Flush with ♠ (spades), more than 5 cards
        {"♦3", "♦5", "♦7", "♦9", "♦J", "♦Q", "♦K", "True"},      // Flush with ♦ (diamonds), more than 5 cards

        // Edge case with no flush, but five cards of the same suit
        {"♠A", "♠3", "♠5", "♠7", "♠8", "♦6", "♣J", "True"},     // Flush with ♠ (spades), no sequence
        {"♦2", "♦3", "♦6", "♦8", "♦10", "♠Q", "♣9", "True"},    // Flush with ♦ (diamonds), no sequence
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
    void isFlushUsingHashTable(List<Card> cards, boolean expectedValue) {
        boolean result = Flush.isFlushUsingHashTable(cards);
        assertEquals(expectedValue, result);
    }

    @ParameterizedTest
    @MethodSource("provideTestCases")
    void isFlushUsingSortAndTwoPointer(List<Card> cards, boolean expectedValue) {
        boolean result = Flush.isFlushUsingSortAndTwoPointer(cards);
        assertEquals(expectedValue, result);
    }
}