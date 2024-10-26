package poker.enums;

public enum Pattern {
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
