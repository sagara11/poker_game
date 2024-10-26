package poker.enums;

public enum Suit {
    CLUB(0),
    DIAMOND(1),
    HEART(2),
    SPADE(3);

    private final int rank;

    Suit(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public char getImage(){
        return (new char[] {9827, 9830, 9829, 9824})[this.ordinal()];
    }
}
