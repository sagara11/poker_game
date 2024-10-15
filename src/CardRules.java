import java.util.*;

public class CardRules {
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

    public static void evaluate(Player player, List<Card> deckCard) {
        List<Card> userCardCopy = new ArrayList<>(player.getCardOnHand());
        userCardCopy.addAll(deckCard);
        Integer[] listOfRank = getListOfRank(userCardCopy);

        Pattern pair = isPair(listOfRank);
        System.out.printf("Player: %s win the game with %s in hand \n", player.getName(), pair.toString());

        Pattern straight = isStraightFromSevenCards(listOfRank);
        System.out.printf("Player: %s win the game with %s in hand \n", player.getName(), straight.toString());

        Pattern flush = isFlushUsingSortAndTwoPointer(userCardCopy);
        System.out.printf("Player: %s win the game with %s in hand \n", player.getName(), flush.toString());
    }

    private static Pattern isPair(Integer[] cardRanks){
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer rank : cardRanks){
            map.put(rank, map.getOrDefault(rank, 0) + 1);
        }

        int pairCount = 0;
        int numberCount = 0;
        for(var element : map.entrySet()){
            if (element.getValue() > 1){
                pairCount++;
                numberCount += element.getValue();
            }
        }

        return switch (pairCount) {
            case 1 -> switch (numberCount) {
                case 4 -> Pattern.FOUR_OF_THE_KIND;
                case 3 -> Pattern.THREE_OF_THE_KIND;
                case 2 -> Pattern.ONE_PAIR;
                default -> Pattern.HIGH_CARD;
            };
            case 2 -> switch (numberCount) {
                case 5 -> Pattern.FULL_HOUSE;
                case 4 -> Pattern.TWO_PAIR;
                default -> Pattern.HIGH_CARD;
            };
            default -> Pattern.HIGH_CARD;
        };
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

    private static Pattern isStraightFromSevenCards(Integer[] cards) {
        // Sliding window of size 5
        Arrays.sort(cards);

        List<Integer> listOfCardsResizing = new ArrayList<>(Arrays.asList(cards));

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
}
