package poker;

import poker.enums.Pattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private final Map<Pattern, List<Pattern>> adjList;

    // Initialize the graph with an empty adjacency list
    public Graph() {
        adjList = new HashMap<>();
        init();
    }

    private void init(){
        for (Pattern pattern : Pattern.values()){
            addNode(pattern);
        }

        // High Card node
        addEdge(Pattern.HIGH_CARD, Pattern.ONE_PAIR);
        addEdge(Pattern.HIGH_CARD, Pattern.FLUSH);
        addEdge(Pattern.HIGH_CARD, Pattern.STRAIGHT);

        // One Pair node
        addEdge(Pattern.ONE_PAIR, Pattern.TWO_PAIR);
        addEdge(Pattern.ONE_PAIR, Pattern.THREE_OF_A_KIND);

        // Two Pair node
        addEdge(Pattern.TWO_PAIR, Pattern.FULL_HOUSE);
        addEdge(Pattern.TWO_PAIR, Pattern.FOUR_OF_A_KIND);

        // Flush node
        addEdge(Pattern.FLUSH, Pattern.STRAIGHT_FLUSH);
        addEdge(Pattern.FLUSH, Pattern.ROYAL_FLUSH);
    }

    // Add a node to the graph
    public void addNode(Pattern node) {
        adjList.putIfAbsent(node, new ArrayList<>());
    }

    // Add an edge (undirected)
    public void addEdge(Pattern node1, Pattern node2) {
        adjList.get(node1).add(node2);
    }

    // Get the neighbors of a node
    public List<Pattern> getNeighbors(Pattern node) {
        return adjList.getOrDefault(node, new ArrayList<>());
    }

    // Display the graph
    public void printGraph() {
        for (var entry : adjList.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            for (Pattern neighbor : entry.getValue()) {
                System.out.print(neighbor == null ? "NULL" : neighbor+ " ");
            }
            System.out.println();
        }
    }
}

