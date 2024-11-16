package org.poker_game;



import org.poker_game.enums.Pattern;
import org.poker_game.services.Base;

import java.util.*;

public class Aggregator {
    private Pattern stateOfTheWinner = Pattern.HIGH_CARD;

    /*
        I count the frequency of all cards existing in the cards of deck.
        Afterward, I evaluate them to determine if they are matched to the patterns.
        Otherwise, I return the default pattern - High card.
     */
    public static Pattern checkingPairPattern(List<Card> cards){
        List<Integer> rankOfCards = Base.getListOfElements(cards, Card::getRank);
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer rank : rankOfCards){
            map.put(rank, map.getOrDefault(rank, 0) + 1);
        }

        int four = 0, three = 0, pair = 0;
        for(var count : map.values()){
            switch (count) {
                case 4:
                    four++;
                    break;
                case 3:
                    three++;
                    break;
                case 2:
                    pair++;
                    break;
                default:
                    break;
            }
        }

        if (four > 0) return Pattern.FOUR_OF_A_KIND;
        if (three > 0 && pair > 0) return Pattern.FULL_HOUSE;
        if (three > 0) return Pattern.THREE_OF_A_KIND;
        if (pair > 1) return Pattern.TWO_PAIR;
        if (pair > 0) return Pattern.ONE_PAIR;

        return Pattern.HIGH_CARD;
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
        List<Card> totalCards = new ArrayList<>(player.getCardOnHand());
        totalCards.addAll(deckCard);

        Pattern stateOfPlayer = switch (method) {
            case "evaluateSubsequently" -> evaluateSubsequently(totalCards);
            case "evaluateDFS" -> evaluateDFS(totalCards);
            default -> Pattern.HIGH_CARD;
        };

        System.out.printf("The player %s is the winner with %s in hand %n", player.getName(), stateOfPlayer);
    }

    /*
        I set up each pattern to win the game with a specific condition in Pattern enum.
        Afterward, I loop through all the patterns to evaluate the highest pattern in the hand of player.
        In the end, I will get the highest answer.

        Time complexity: O(n) with n is the numbers of all the possible patterns in main.poker
        Space complexity: O(n)
     */
    public Pattern evaluateSubsequently(List<Card> totalCards){
        Pattern stateOfPlayer = Pattern.HIGH_CARD;
        for (Pattern pattern: Pattern.values()){
            String method = pattern == Pattern.STRAIGHT ? "slidingWindow" : "hashTable";
            if (
                    stateOfPlayer.getRank() < pattern.getRank() &&
                            pattern.examine(totalCards, method)
            ){
                stateOfPlayer = pattern;
            }
        }

        return stateOfPlayer;
    }

    // Public DFS method
    public Pattern evaluateDFS(List<Card> totalCards) {
        Set<Pattern> visited = new HashSet<>(); // Set to track visited nodes
        Graph graph = new Graph();
        dfs(totalCards, Pattern.HIGH_CARD, visited, graph);

        return stateOfTheWinner;
    }

    private void dfs(
            List<Card> totalCards,
            Pattern node,
            Set<Pattern> visited,
            Graph graph
    ){
        if (visited.contains(node)) return;
        visited.add(node);
        if (stateOfTheWinner.getRank() > node.getRank()) return;
        if (!node.examine(totalCards, node == Pattern.STRAIGHT ? "slidingWindow" : "hashTable"))
            return;
        if (graph.getNeighbors(node).isEmpty()) {
            stateOfTheWinner = node.getRank() > stateOfTheWinner.getRank() ? node : stateOfTheWinner;
            return;
        }

        // Visit all adjacent nodes that haven't been visited
        for (var neighbor : graph.getNeighbors(node)) {
            stateOfTheWinner = node.getRank() > stateOfTheWinner.getRank() ? node : stateOfTheWinner;
            dfs(totalCards, neighbor, visited, graph); // Recursive call
        }
    }
}
