package org.poker_game;

import org.poker_game.enums.Suit;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static Suit convertToSuit(char suit){
        return switch ((int) suit){
            case 9827 -> Suit.CLUB;
            case 9830 -> Suit.DIAMOND;
            case 9829 -> Suit.HEART;
            case 9824 -> Suit.SPADE;
            default -> throw new IllegalStateException("Unexpected value: ");
        };
    }

    public static Card convertToCard(String value, Suit suit){
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

    public static List<TestStructure<Boolean>> cleanData(String[][] data){
        List<TestStructure<Boolean>> solutions = new ArrayList<>(11);

        for (String[] solution : data){
            List<Card> cards = new ArrayList<>(7);
            for (int i = 0; i < 7; i++){
                String card = solution[i];
                Suit suit = convertToSuit(card.charAt(0));
                String value = card.length() > 2 ?
                        String.valueOf(card.charAt(1)) + card.charAt(2) : String.valueOf(card.charAt(1));
                cards.add(convertToCard(value, suit));
            }
            solutions.add(new TestStructure<>(cards, Boolean.parseBoolean(solution[7])));
        }

        return solutions;
    }
}
