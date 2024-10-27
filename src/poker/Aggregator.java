package poker;


import poker.enums.Pattern;
import poker.services.Base;

import java.lang.reflect.Parameter;
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
                case 4: four++;
                case 3: three++;
                case 2: pair++;
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
        Pattern stateOfPlayer = switch (method){
            case "evaluateSubsequently" -> evaluateSubsequently(player, deckCard);
            case "evaluateDFS" -> evaluateDFS(player, deckCard);
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
    public Pattern evaluateDFS(Player player, List<Card> deckCard) {
        List<Card> totalCards = new ArrayList<>(player.getCardOnHand());
        totalCards.addAll(deckCard);

        Set<Pattern> visited = new HashSet<>(); // Set to track visited nodes
        Graph graph = new Graph();
        dfs(player, totalCards, Pattern.HIGH_CARD, visited, "", graph);

        return stateOfTheWinner;
    }

    private void dfs(
            Player player,
            List<Card> totalCards,
            Pattern node,
            Set<Pattern> visited,
            String method,
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
            dfs(player, totalCards, neighbor, visited, method, graph); // Recursive call
        }
    }
}
