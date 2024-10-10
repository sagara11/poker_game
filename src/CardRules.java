import java.util.*;

public class CardRules {
    enum Pattern {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_THE_KIND,
        STRAIGHT,
        FLUSH,
        FULL_HOUSE,
        FOUR_OF_THE_KIND,
        STRAIGHT_FLUSH,
        ROYAL_FLUSH
    }

    public static void evaluate(Player player, List<Card> deckCard) {
        List<Card> userCardCopy = new ArrayList<>(player.getCardOnHand());
        userCardCopy.addAll(deckCard);
        userCardCopy.sort(Comparator.comparing(Card::getRank));
    }

    private boolean pair(List<Card> cards){
        Map<String, Integer> map = new HashMap<>();
        for (Card card : cards){
            map.put(card.getFace(), map.getOrDefault(card.getFace(), 0) + 1);
        }

        int pairCount = 0;
        int numberCount = 0;
        for(var pair: map.entrySet()){
            if (pair.getValue() > 1){
                pairCount++;
                numberCount += pair.getValue();
            }
        }

        switch (pairCount){
            case (1 && numberCount == 1):
        }
    }
}
