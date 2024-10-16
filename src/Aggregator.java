import java.util.*;
import java.util.function.Function;

public class Aggregator {
    enum Pattern {
        HIGH_CARD(0),
        ONE_PAIR(1),
        TWO_PAIR(2),
        THREE_OF_THE_KIND(3),
        STRAIGHT(4),
        FLUSH(5),
        FULL_HOUSE(6),
        FOUR_OF_THE_KIND(7),
        STRAIGHT_FLUSH(8),
        ROYAL_FLUSH(9);

        private final int rank;

        Pattern(int rank) {
            this.rank = rank;
        }

        public int getRank() {
            return rank;
        }
    }
    private static int pairCount;
    private static int numberCount;

    private static void evaluateCards(List<Card> cards){
        Integer[] rankOfCards = getListOfRank(cards);
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

    private static boolean isStraight(List<Integer> cards){
        // Using sliding window with a fixed Size of 5 to validate the cards
        boolean isStandardStraight = isStandardStraight(cards);

        // Check for a special Ace-low straight only if cards 2, 3, 4, 5 are present
        boolean isAceLowStraight =  cards.contains(2) &&
                cards.contains(3) &&
                cards.contains(4) &&
                cards.contains(5) &&
                cards.contains(14);
        return isStandardStraight || isAceLowStraight;
    }

    private static boolean isStandardStraight(List<Integer> cards){
        for (int i = 1; i < cards.size(); i++){
            if (cards.get(i) != cards.get(i-1) + 1){
                return false;
            }
        }
        return true;
    }
    private static Integer[] getListOfRank(List<Card> cards) {
        return cards.stream()
                .map(Card::getRank)
                .toArray(Integer[]::new);
    }
    private static Suit[] getListOfSuit(List<Card> cards) {
        return cards.stream()
                .map(Card::getSuit)
                .toArray(Suit[]::new);
    }

    public static void evaluate(Player player, List<Card> deckCard) {
        List<Card> totalCards = new ArrayList<>(player.getCardOnHand());
        totalCards.addAll(deckCard);

        List<Function<List<Card>, Pattern>> conditions = generatePatternConditions(totalCards);
        Pattern stateOfPlayer = Pattern.HIGH_CARD;
        for (var condition: conditions){
            Pattern result = condition.apply(totalCards);
            if (stateOfPlayer.getRank() < result.getRank()){
                stateOfPlayer = result;
            }
        }

        System.out.printf("The player %s is the winner with %s in hand \n", player.getName(), stateOfPlayer);
    }


    private static Pattern isStraightFromSevenCards(List<Card> cards) {
        // Sliding window of size 5
        Integer[] listOfRanks = getListOfRank(cards);
        Arrays.sort(listOfRanks);

        List<Integer> listOfCardsResizing = new ArrayList<>(Arrays.asList(listOfRanks));

        if(listOfCardsResizing.contains(14)){
            listOfCardsResizing.add(0, 1);
        }
        for (int i = 0; i <= listOfCardsResizing.size() - 5; i++) {
            List<Integer> window = listOfCardsResizing.subList(i, i + 5); // Get the current 5-card window
            if (isStraight(window)) {
                return Pattern.STRAIGHT; // Found a straight
            }
        }

        return Pattern.HIGH_CARD; // No straight found
    }

    private static Pattern isFlushUsingHashTable(List<Card> cards){
        // Using hash_table: TC: O(n) & SP: O(n)
        Map<Suit, Integer> hashMap = new HashMap<>();

        for(Card card : cards){
            Suit suit = card.getSuit();
            hashMap.put(suit, hashMap.getOrDefault(suit, 0) + 1);
            if (hashMap.get(suit) == 5){
                return Pattern.FLUSH;
            }
        }
        return Pattern.HIGH_CARD;
    }

    private static Pattern isFlushUsingSortAndTwoPointer(List<Card> cards){
        // Using sort + two pointer: TC: O(nlog(n)) + O(n) & SP: O(1)
        Suit[] listOfSuit = getListOfSuit(cards);
        Arrays.sort(listOfSuit, Comparator.comparing(Suit::getRank));

        int p1 = 0;
        int p2 = 0;

        while (p2 < listOfSuit.length) {
            if (p2 - p1 == 4 && listOfSuit[p1] == listOfSuit[p2]){
                return Pattern.FLUSH;
            }
            if (listOfSuit[p1] != listOfSuit[p2]){
                p1 = p2;
            }
            p2++;
        }

        return Pattern.HIGH_CARD;
    }

    private static boolean isContainingAceUpperBound(List<Card> cards){
        List<Integer> ranksOfCard = Arrays.asList(getListOfRank(cards));
        return  ranksOfCard.contains(10) &&
                ranksOfCard.contains(11) &&
                ranksOfCard.contains(12) &&
                ranksOfCard.contains(13) &&
                ranksOfCard.contains(14);
    }

    private static List<Function<List<Card>, Pattern>> generatePatternConditions(List<Card> cards) {
        evaluateCards(cards);
        Function<List<Card>, Pattern> highCard = p -> Pattern.HIGH_CARD;
        Function<List<Card>, Pattern> onePair = p -> pairCount == 1 && numberCount == 2 ? Pattern.ONE_PAIR : Pattern.HIGH_CARD;
        Function<List<Card>, Pattern> twoPair = p -> pairCount == 2 && numberCount == 4 ? Pattern.TWO_PAIR : Pattern.HIGH_CARD;
        Function<List<Card>, Pattern> threeOfAKind = p -> pairCount == 1 && numberCount == 3 ? Pattern.THREE_OF_THE_KIND : Pattern.HIGH_CARD;
        Function<List<Card>, Pattern> straight = Aggregator::isStraightFromSevenCards;
        Function<List<Card>, Pattern> flush = Aggregator::isFlushUsingHashTable;
        Function<List<Card>, Pattern> fullHouse = p -> pairCount == 2 && numberCount == 5 ? Pattern.FULL_HOUSE : Pattern.HIGH_CARD;
        Function<List<Card>, Pattern> fourOfAKind = p -> pairCount == 1 && numberCount == 4 ? Pattern.FOUR_OF_THE_KIND : Pattern.HIGH_CARD;
        Function<List<Card>, Pattern> straightFlush =
                p -> straight.apply(p) == Pattern.STRAIGHT && flush.apply(p) == Pattern.FLUSH ? Pattern.STRAIGHT_FLUSH : Pattern.HIGH_CARD;
        Function<List<Card>, Pattern> royalFlush =
                p -> straightFlush.apply(p) == Pattern.STRAIGHT_FLUSH && isContainingAceUpperBound(p) ? Pattern.ROYAL_FLUSH : Pattern.HIGH_CARD;
        return List.of(highCard, onePair, twoPair, threeOfAKind, straight, flush, fullHouse, fourOfAKind, straightFlush, royalFlush);
    }
}
