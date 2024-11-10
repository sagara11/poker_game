package org.poker_game;

import org.poker_game.enums.DeckStatus;
import org.poker_game.enums.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck{
    private List<Card> standardCardOnDeck;
    private List<Card> cardOnDeck;
    private List<Player> players;
    private int cardOpenedCount = 0;
    private DeckStatus status;

    public List<Card> getCardOnDeck() {
        return cardOnDeck;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Card> getStandardCardOnDeck() {
        return standardCardOnDeck;
    }

    public void setStandardCardOnDeck(List<Card> standardCardOnDeck) {
        this.standardCardOnDeck = standardCardOnDeck;
    }

    public DeckStatus getStatus() {
        return status;
    }

    public int getCardOpenedCount() {
        return cardOpenedCount;
    }

    public Deck(List<Player> players){
        this();
        this.players = players;
        this.cardOnDeck = new ArrayList<>(5);
        this.status = DeckStatus.NULL;
    }

    public Deck(){
        List<Card> result = new ArrayList<>(52);
        for (Suit suit : Suit.values()){
            for (int i = 2; i <= 10; i ++){
                result.add(Card.getNumericCard(suit, i));
            }

            for (char s : new char[]{'J', 'Q', 'K', 'A'}){
                result.add(Card.getFaceCard(suit, s));
            }
        }
        this.standardCardOnDeck = result;
    }

    public void printDeck(String description, int row){
        int cardPerRow = standardCardOnDeck.size() / row;
        for (int i = 0; i < row; i ++){
            int startIndex = i * cardPerRow;
            int endIndex = startIndex + cardPerRow;
            System.out.println(standardCardOnDeck.subList(startIndex, endIndex));
        }
        System.out.println(description);
    }

    public void printDeck(){
        printDeck("Current Deck", 4);
    }

    public void dealingCards() throws Exception {
        Collections.shuffle(standardCardOnDeck);
        Collections.rotate(standardCardOnDeck, standardCardOnDeck.size() / 2);

        for (int i = 0; i < 2; i++){
            for (Player player : players){
                if (!player.receiveCard(standardCardOnDeck.get(cardOpenedCount++))){
                    throw new Exception("Cannot deal cards to players");
                }
            }
        }
    }

    public void openCard(){
        // NULL -> FLOP -> TURN -> RIVER
        this.status = DeckStatus.changeState(status);
        // Cut one card before opening the card
        cardOpenedCount += 1;

        int cardsToOpen = switch (status) {
            case TURN, RIVER -> 1;
            case FLOP -> 3;
            default -> 0;
        };
        List<Card> cardToOpen = standardCardOnDeck.subList(cardOpenedCount, cardOpenedCount + cardsToOpen);
        cardOpenedCount += cardsToOpen;
        cardOnDeck.addAll(cardToOpen);
    }
}
