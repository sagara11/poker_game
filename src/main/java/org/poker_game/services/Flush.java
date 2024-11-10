package org.poker_game.services;


import org.poker_game.Card;
import org.poker_game.enums.Suit;

import java.util.*;

import static java.lang.Integer.sum;

public class Flush extends Base {
    public static boolean isFlush(List<Card> cards, String method){
        return switch (method){
            case "hashTable" -> isFlushUsingHashTable(cards);
            case "sortAndTwoPointer" -> isFlushUsingSortAndTwoPointer(cards);
            default -> false;
        };
    }

    /*
        I unify 7 cards on the deck to a hash map having key, value are suit, frequency respectively
        Which type of suit having the frequency equal or greater than 5 is the answer,
        Otherwise, we will return high card as the answer.

        Time Complexity: O(n) = O(7) - We have to loop through 7 cards in the deck.
        Space Complexity: O(n) = O(4) - We have save 4 kinds of suit: SPACE, DIAMOND, CLUB, HEART
     */
    public static boolean isFlushUsingHashTable(List<Card> cards){
        // Using hash_table: TC: O(n) & SP: O(n)
        Map<Suit, Integer> hashMap = new HashMap<>();

        for(Card card : cards){
            Suit suit = card.getSuit();
            hashMap.put(suit, hashMap.getOrDefault(suit, 0) + 1);
            if (hashMap.get(suit) == 5){
                return true;
            }
        }
        return false;
    }

    /*
        First, we sort the elements based on their rank in an increasing order.
        If the distance between 2 pointers are 5 and the type of suit of 2 pointers are the same,
            return Pattern Flush and end the function
        else,
            it ends up with the High card pattern for not finding the answer.

        Time Complexity: O(nlog(n) + n) - Sort + loop through the cards in the deck
        Space Complexity: O(1)
     */
    public static boolean isFlushUsingSortAndTwoPointer(List<Card> cards){
        // Using sort + two pointer: TC: O(nlog(n)) + O(n) & SP: O(1)
        List<Suit> listOfSuit = getListOfElements(cards, Card::getSuit);
        Suit[] arrayOfSuit = listOfSuit.toArray(new Suit[]{});
        Arrays.sort(arrayOfSuit, Comparator.comparing(Suit::getRank));

        int p1 = 0;
        int p2 = 0;

        while (p2 < arrayOfSuit.length) {
            if (p2 - p1 == 4 && arrayOfSuit[p1] == arrayOfSuit[p2]){
                return true;
            }
            if (arrayOfSuit[p1] != arrayOfSuit[p2]){
                p1 = p2;
            }
            p2++;
        }

        return false;
    }

    public static Integer total(List<Integer> nums){
        return nums.stream().mapToInt(Integer::intValue).sum();
    }
}
