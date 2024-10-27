package poker.enums;

import poker.Card;
import poker.services.Flush;
import poker.services.Straight;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static poker.Aggregator.checkingPairPattern;
import static poker.Aggregator.isContainingAceUpperBound;

public enum Pattern {
    HIGH_CARD(0) {
        @Override
        public boolean examine(List<Card> cards, String method) {
            System.out.println("-".repeat(30) + this);
            return true;
        }
    },
    ONE_PAIR(1) {
        @Override
        public boolean examine(List<Card> cards, String method) {
            System.out.println("-".repeat(30) + this);
            return checkingPairPattern(cards).getRank() >= this.getRank();
        }
    },
    TWO_PAIR(2) {
        @Override
        public boolean examine(List<Card> cards, String method) {
            System.out.println("-".repeat(30) + this);
            return checkingPairPattern(cards).getRank() >= this.getRank();
        }
    },
    THREE_OF_A_KIND(3) {
        @Override
        public boolean examine(List<Card> cards, String method) {
            System.out.println("-".repeat(30) + this);
            return checkingPairPattern(cards).getRank() >= this.getRank();
        }
    },
    STRAIGHT(4) {
        @Override
        public boolean examine(List<Card> cards, String method) {
            System.out.println("-".repeat(30) + this);
            return Straight.isStraight(cards, method);
        }
    },
    FLUSH(5) {
        @Override
        public boolean examine(List<Card> cards, String method) {
            System.out.println("-".repeat(30) + this);
            return Flush.isFlush(cards, method);
        }
    },
    FULL_HOUSE(6) {
        @Override
        public boolean examine(List<Card> cards, String method) {
            System.out.println("-".repeat(30) + this);
            return checkingPairPattern(cards).getRank() >= this.getRank();
        }
    },
    FOUR_OF_A_KIND(7) {
        @Override
        public boolean examine(List<Card> cards, String method) {
            System.out.println("-".repeat(30) + this);
            return checkingPairPattern(cards).getRank() >= this.getRank();
        }
    },
    STRAIGHT_FLUSH(8) {
        @Override
        public boolean examine(List<Card> cards, String method) {
            System.out.println("-".repeat(30) + this);
            return Pattern.STRAIGHT.examine(cards, method)
                    && Pattern.FLUSH.examine(cards, method);
        }
    },
    ROYAL_FLUSH(9) {
        @Override
        public boolean examine(List<Card> cards, String method) {
            System.out.println("-".repeat(30) + this);
            return Pattern.STRAIGHT_FLUSH.examine(cards, method) && isContainingAceUpperBound(cards);
        }
    };

    private final int rank;

    Pattern(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public abstract boolean examine(List<Card> cards, String method);
}
