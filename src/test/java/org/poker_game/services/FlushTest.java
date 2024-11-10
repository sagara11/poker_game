package org.poker_game.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.poker_game.Card;
import org.poker_game.enums.Suit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

record TestCase(List<Card> cards, boolean expectedValue){};

class FlushTest {
     static String[][] testCases = {
        // Positive Test Cases (Expected: True)
        {"♥10", "♥4", "♥6", "♥8", "♥J", "♠2", "♦3", "True"},
        {"♠A", "♠3", "♠5", "♠7", "♠9", "♥2", "♦4", "True"},
        {"♣K", "♣Q", "♣J", "♣9", "♣2", "♠6", "♦8", "True"},
        {"♦A", "♦3", "♦4", "♦7", "♦K", "♠Q", "♣9", "True"},
        {"♠9", "♠8", "♠5", "♠3", "♠K", "♥7", "♣4", "True"},

        // Negative Test Cases (Expected: False)
        {"♠A", "♠3", "♠5", "♥7", "♥9", "♦2", "♣4", "False"},
        {"♣K", "♣Q", "♠J", "♠9", "♠2", "♥6", "♦8", "False"},
        {"♦10", "♦4", "♦6", "♠8", "♠J", "♥2", "♥3", "False"},
        {"♠A", "♠2", "♥3", "♥4", "♦5", "♦6", "♣7", "False"},
        {"♥Q", "♥J", "♠10", "♠9", "♣8", "♣7", "♦6", "False"},

        // Edge Case (Expected: True)
        {"♠2", "♠3", "♠5", "♠7", "♠9", "♠K", "♣4", "True"}
     };

     static String[][] positiveCases = new String[5][8];
     static String[][] negativeCases = new String[5][8];
     static String[][] edgeCases = new String[1][8];


    @BeforeAll
    static void setup() {
        positiveCases = Arrays.copyOfRange(testCases, 0, 5);
        negativeCases = Arrays.copyOfRange(testCases, 5, 10);
        edgeCases = Arrays.copyOfRange(testCases, 10, 12);
    }
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

    public static List<TestCase> cleanData(String[][] data){
        List<TestCase> solutions = new ArrayList<>(11);

        for (String[] solution : data){
            List<Card> cards = new ArrayList<>(7);
            for (int i = 0; i < 7; i++){
                String card = solution[i];
                Suit suit = convertToSuit(card.charAt(0));
                String value = card.length() > 2 ?
                        String.valueOf(card.charAt(1)) + card.charAt(2) : String.valueOf(card.charAt(1));
                cards.add(convertToCard(value, suit));
            }
            solutions.add(new TestCase(cards, Boolean.parseBoolean(solution[7])));
        }

        return solutions;
    }

//  Method that supplies the parameters
    private static Stream<Arguments> providePositiveTestCases() {
        List<Arguments> arguments = new ArrayList<>();
        cleanData(positiveCases).forEach(e -> {
            arguments.add(
                    Arguments.of(e.cards(), e.expectedValue())
            );
        });

        return arguments.stream();
    }

    @ParameterizedTest
    @MethodSource("providePositiveTestCases")
    void isFlushUsingHashTable(List<Card> cards, boolean expectedValue) {
        boolean result = Flush.isFlushUsingHashTable(cards);
        assertEquals(expectedValue, result);
    }

    @Test
    void isFlushUsingSortAndTwoPointer() {
    }
}