package poker.enums;

import poker.Card;
import poker.services.Flush;
import poker.services.Straight;

import java.util.List;

import static poker.Aggregator.isContainingAceUpperBound;

public enum Pattern {
    HIGH_CARD(0) {
        @Override
        public Pattern examine(List<Card> cards, int pairCount, int numberCount, String method) {
            return Pattern.HIGH_CARD;
        }
    },
    ONE_PAIR(1) {
        @Override
        public Pattern examine(List<Card> cards, int pairCount, int numberCount, String method) {
            return pairCount == 1 && numberCount == 2 ? Pattern.ONE_PAIR : Pattern.HIGH_CARD;
        }
    },
    TWO_PAIR(2) {
        @Override
        public Pattern examine(List<Card> cards, int pairCount, int numberCount, String method) {
            return pairCount == 2 && numberCount == 4 ? Pattern.TWO_PAIR : Pattern.HIGH_CARD;
        }
    },
    THREE_OF_THE_KIND(3) {
        @Override
        public Pattern examine(List<Card> cards, int pairCount, int numberCount, String method) {
            return pairCount == 1 && numberCount == 3 ? Pattern.THREE_OF_THE_KIND : Pattern.HIGH_CARD;
        }
    },
    STRAIGHT(4) {
        @Override
        public Pattern examine(List<Card> cards, int pairCount, int numberCount, String method) {
            return Straight.isStraight(cards, method);
        }
    },
    FLUSH(5) {
        @Override
        public Pattern examine(List<Card> cards, int pairCount, int numberCount, String method) {
            return Flush.isFlush(cards, method);
        }
    },
    FULL_HOUSE(6) {
        @Override
        public Pattern examine(List<Card> cards, int pairCount, int numberCount, String method) {
            return pairCount == 2 && numberCount == 5 ? Pattern.FULL_HOUSE : Pattern.HIGH_CARD;
        }
    },
    FOUR_OF_THE_KIND(7) {
        @Override
        public Pattern examine(List<Card> cards, int pairCount, int numberCount, String method) {
            return pairCount == 1 && numberCount == 4 ? Pattern.FOUR_OF_THE_KIND : Pattern.HIGH_CARD;
        }
    },
    STRAIGHT_FLUSH(8) {
        @Override
        public Pattern examine(List<Card> cards, int pairCount, int numberCount, String method) {
            return Pattern.STRAIGHT.examine(cards, pairCount, numberCount, method) == Pattern.STRAIGHT
                    && Pattern.FLUSH.examine(cards, pairCount, numberCount, method) == Pattern.FLUSH ? Pattern.STRAIGHT_FLUSH : Pattern.HIGH_CARD;
        }
    },
    ROYAL_FLUSH(9) {
        @Override
        public Pattern examine(List<Card> cards, int pairCount, int numberCount, String method) {
            return Pattern.STRAIGHT_FLUSH.examine(cards, pairCount, numberCount, method) == Pattern.STRAIGHT_FLUSH
                    && isContainingAceUpperBound(cards) ? Pattern.ROYAL_FLUSH : Pattern.HIGH_CARD;
        }
    };

    private final int rank;

    Pattern(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public abstract Pattern examine(List<Card> cards, int pairCount, int numberCount, String method);
}
