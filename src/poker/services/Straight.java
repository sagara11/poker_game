package poker.services;

import poker.Card;
import poker.enums.Pattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Straight extends Base {
    /*
        Using sliding window with a fixed Size of 5 to validate the cards
        Check for a special Ace-low straight only if cards 2, 3, 4, 5 are present
     */
    private static boolean isStraight(List<Integer> cards){
        boolean isStandardStraight = isStandardStraight(cards);
        boolean isAceLowStraight =  cards.contains(2) &&
                cards.contains(3) &&
                cards.contains(4) &&
                cards.contains(5) &&
                cards.contains(14);
        return isStandardStraight || isAceLowStraight;
    }

    /*
        Check each element whether it continuously follow the next one or the previous one with the unit equal to 1
     */
    private static boolean isStandardStraight(List<Integer> cards){
        for (int i = 1; i < cards.size(); i++){
            if (cards.get(i) != cards.get(i-1) + 1){
                return false;
            }
        }
        return true;
    }

    /*
        Generate all possible combinations of 5 cards among 7 cards
     */
    private static void generateCombinations(
            List<Integer> cards,
            List<Integer> currentSolution,
            int start,
            List<List<Integer>> result
    ){
        if(currentSolution.size() == 5){
            result.add(new ArrayList<>(currentSolution));
            return;
        }

        for (int i = start; i < cards.size(); i++){
            currentSolution.add(cards.get(i));
            generateCombinations(cards, currentSolution, i + 1, result);
            currentSolution.removeLast();
        }
    }

    /*
        First, we sort the list by its rank so basically elements are sorted in an increasing order.
        Afterward,
        If the considering cards (a subset of 5 cards among 7 cards) containing Ace,
            we will add an addition element having value 1 to the list so that we can cover the case: 1,2,3,4,5
        else,
            we will continuously get a subset of 5 cards among 7 cards to examine it whether it is straight.

        Time Complexity: O(n - 5) (n in this case is 7) = O(2) - Constant Time
        Space Complexity: O(1)
     */
    private static boolean isStraightUsingSlidingWindow(List<Card> cards) {
        // Sliding window of size 5
        List<Integer> listOfRanks = getListOfElements(cards, Card::getRank);
        Arrays.sort(listOfRanks.toArray());

        List<Integer> listOfCardsResizing = new ArrayList<>(listOfRanks);

        if(listOfCardsResizing.contains(14)){
            listOfCardsResizing.addFirst(1);
        }
        for (int i = 0; i <= listOfCardsResizing.size() - 5; i++) {
            List<Integer> window = listOfCardsResizing.subList(i, i + 5); // Get the current 5-card window
            if (isStraight(window)) {
                return true; // Found a straight
            }
        }

        return false; // No straight found
    }

    /*
        We just simply test all possible combinations and return the straight one
        right after matching the isStraight condition in the for loop.
     */
    private static boolean isStraightUsingBackTracking(List<Card> cards) {
        List<List<Integer>> combinations = new ArrayList<>(21);
        generateCombinations(getListOfElements(cards, Card::getRank), new ArrayList<>(5), 0, combinations);

        for (var combination : combinations){
            if (isStraight(combination)){
                return true;
            }
        }
        return false;
    }

    public static boolean isStraight(List<Card> cards, String method){
        return switch (method){
            case "slidingWindow" -> isStraightUsingSlidingWindow(cards);
            case "backTracking" -> isStraightUsingBackTracking(cards);
            default -> false;
        };
    }
}
