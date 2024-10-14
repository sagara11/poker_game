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
        userCardCopy.sort(Comparator.comparing(Card::getRank));
        Integer[] listOfRank = getListOfRank(userCardCopy);

//        Pattern pair = isPair(listOfRank);
//        System.out.printf("Player: %s win the game with %s in hand \n", player.getName(), pair.toString());

        Pattern straight = isStraightFromSevenCards(listOfRank);
        System.out.printf("Player: %s win the game with %s in hand \n", player.getName(), straight.toString());
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

    private static Pattern isStraightFromSevenCards(Integer[] cards) {
        List<List<Integer>> combinations = new ArrayList<>(21);
        generateCombinations(Arrays.asList(cards), new ArrayList<>(5), 0, combinations);

        for (var combination : combinations){
            if (isStraight(combination)){
                return Pattern.STRAIGHT;
            }
        }
        return Pattern.HIGH_CARD;
    }

    private static void generateCombinations(
            List<Integer> cards,
            List<Integer> currentSolution,
            int start,
            List<List<Integer>> result
    ){
        if(currentSolution.size() == 5){
            result.add(new ArrayList<>(currentSolution));
            return;
        }

        for (int i = start; i < cards.size(); i++){
            currentSolution.add(cards.get(i));
            generateCombinations(cards, currentSolution, i + 1, result);
            currentSolution.remove(currentSolution.size() - 1);
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
}
