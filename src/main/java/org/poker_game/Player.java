package org.poker_game;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Card> cardOnHand;
    private String name;

    public Player(String name) {
        this.name = name;
        this.cardOnHand = new ArrayList<>();
    }

    public Player(List<Card> cardOnHand, String name) {
        this.cardOnHand = cardOnHand;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getCardOnHand() {
        return cardOnHand;
    }

    public void setCardOnHand(List<Card> cardOnHand) {
        this.cardOnHand = cardOnHand;
    }

    public boolean receiveCard(Card card){
        if (cardOnHand.size() > 2) {
            return false;
        }
        cardOnHand.add(card);
        return true;
    }
}
