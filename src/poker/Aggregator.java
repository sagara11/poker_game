package poker;


import poker.enums.Pattern;
import poker.enums.Suit;
import poker.services.Base;
import poker.services.Flush;
import poker.services.Straight;

import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Function;

public class Aggregator {
    private int pairCount;
    private int numberCount;

    /*
        Unify cards by their ranks and count their frequency in the decks cards.
        Afterward, I save it to a Hashmap with the key, value are rank, frequency respectively.
     */

    private void preProcessingCards(List<Card> cards){
        List<Integer> rankOfCards = Base.getListOfElements(cards, Card::getRank);
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer rank : rankOfCards){
            map.put(rank, map.getOrDefault(rank, 0) + 1);
        }

        for(var element : map.entrySet()){
            if (element.getValue() > 1){
                pairCount++;
                numberCount += element.getValue();
            }
        }
    }

    public static boolean isContainingAceUpperBound(List<Card> cards){
        List<Integer> ranksOfCard = Base.getListOfElements(cards, Card::getRank);
        return  ranksOfCard.contains(10) &&
                ranksOfCard.contains(11) &&
                ranksOfCard.contains(12) &&
                ranksOfCard.contains(13) &&
                ranksOfCard.contains(14);
    }

    public void evaluate(Player player, List<Card> deckCard, String method) {
        Pattern stateOfPlayer = switch (method){
            case "evaluateSubsequently" -> evaluateSubsequently(player, deckCard);
            default -> Pattern.HIGH_CARD;
        };
        System.out.printf("The player %s is the winner with %s in hand \n", player.getName(), stateOfPlayer);
    }

    /*
        I set up each pattern to win the game with a specific condition in Pattern enum.
        Afterward, I loop through all the patterns to evaluate the highest pattern in the hand of player.
        In the end, I will get the highest answer.

        Time complexity: O(n) with n is the numbers of all the possible patterns in poker
        Space complexity: O(n)
     */
    private Pattern evaluateSubsequently(Player player, List<Card> deckCard){
        List<Card> totalCards = new ArrayList<>(player.getCardOnHand());
        totalCards.addAll(deckCard);

        preProcessingCards(totalCards);
        Pattern stateOfPlayer = Pattern.HIGH_CARD;
        for (Pattern pattern: Pattern.values()){
            String method = pattern == Pattern.STRAIGHT ? "slidingWindow" : "hashTable";
            Pattern result = pattern.examine(totalCards, pairCount, numberCount, method);
            if (stateOfPlayer.getRank() < result.getRank()){
                stateOfPlayer = result;
            }
        }

        return stateOfPlayer;
    }

    private Pattern evaluateBasedOnGraph(Player player, List<Card> deckCard){
        List<Card> totalCards = new ArrayList<>(player.getCardOnHand());
        totalCards.addAll(deckCard);

        Pattern stateOfPlayer = Pattern.HIGH_CARD;
        for (Pattern pattern: Pattern.values()){
            String method = pattern == Pattern.STRAIGHT ? "slidingWindow" : "hashTable";
            Pattern result = pattern.examine(totalCards, pairCount, numberCount, method);
            if (stateOfPlayer.getRank() < result.getRank()){
                stateOfPlayer = result;
            }
        }

        return stateOfPlayer;
    }
}
