package poker;


import poker.enums.Pattern;
import poker.enums.Suit;
import poker.services.Base;
import poker.services.Flush;
import poker.services.Straight;

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

    private static boolean isContainingAceUpperBound(List<Card> cards){
        List<Integer> ranksOfCard = Base.getListOfElements(cards, Card::getRank);
        return  ranksOfCard.contains(10) &&
                ranksOfCard.contains(11) &&
                ranksOfCard.contains(12) &&
                ranksOfCard.contains(13) &&
                ranksOfCard.contains(14);
    }

    public void evaluate(Player player, List<Card> deckCard) {
        List<Card> totalCards = new ArrayList<>(player.getCardOnHand());
        totalCards.addAll(deckCard);

        List<Function<List<Card>, Pattern>> conditions = generatePatternConditions(
                totalCards,
                "slidingWindow",
                "hashTable"
        );
        Pattern stateOfPlayer = Pattern.HIGH_CARD;
        for (var condition: conditions){
            Pattern result = condition.apply(totalCards);
            if (stateOfPlayer.getRank() < result.getRank()){
                stateOfPlayer = result;
            }
        }

        System.out.printf("The player %s is the winner with %s in hand \n", player.getName(), stateOfPlayer);
    }

    private List<Function<List<Card>, Pattern>> generatePatternConditions(List<Card> cards, String straightMethod, String flushMethod) {
        preProcessingCards(cards);
        Function<List<Card>, Pattern> highCard = p -> Pattern.HIGH_CARD;
        Function<List<Card>, Pattern> onePair = p -> pairCount == 1 && numberCount == 2 ? Pattern.ONE_PAIR : Pattern.HIGH_CARD;
        Function<List<Card>, Pattern> twoPair = p -> pairCount == 2 && numberCount == 4 ? Pattern.TWO_PAIR : Pattern.HIGH_CARD;
        Function<List<Card>, Pattern> threeOfAKind = p -> pairCount == 1 && numberCount == 3 ? Pattern.THREE_OF_THE_KIND : Pattern.HIGH_CARD;
        Function<List<Card>, Pattern> straight = p -> Straight.isStraight(p, straightMethod);
        Function<List<Card>, Pattern> flush = p -> Flush.isFlush(cards, flushMethod);
        Function<List<Card>, Pattern> fullHouse = p -> pairCount == 2 && numberCount == 5 ? Pattern.FULL_HOUSE : Pattern.HIGH_CARD;
        Function<List<Card>, Pattern> fourOfAKind = p -> pairCount == 1 && numberCount == 4 ? Pattern.FOUR_OF_THE_KIND : Pattern.HIGH_CARD;
        Function<List<Card>, Pattern> straightFlush =
                p -> straight.apply(p) == Pattern.STRAIGHT && flush.apply(p) == Pattern.FLUSH ? Pattern.STRAIGHT_FLUSH : Pattern.HIGH_CARD;
        Function<List<Card>, Pattern> royalFlush =
                p -> straightFlush.apply(p) == Pattern.STRAIGHT_FLUSH && isContainingAceUpperBound(p) ? Pattern.ROYAL_FLUSH : Pattern.HIGH_CARD;
        return List.of(highCard, onePair, twoPair, threeOfAKind, straight, flush, fullHouse, fourOfAKind, straightFlush, royalFlush);
    }
}
