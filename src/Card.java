import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Card {
    private Suit suit;
    private String face;
    private Integer rank;

    public Card(Suit suit, String face, int rank) {
        this.suit = suit;
        this.face = face;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public String getFace() {
        return face;
    }

    public Integer getRank() {
        return rank;
    }

    @Override
    public String toString() {
        int index = face.equals("10") ? 2 : 1;
        String faceString = face.substring(0, index);
        return  "%s%c(%d)".formatted(faceString, suit.getImage(), rank);
    }

    public static Card getNumericCard(Suit suit, int cardNumber){
        if (cardNumber > 1 && cardNumber < 11){
            return new Card(suit, String.valueOf(cardNumber), cardNumber);
        }
        return null;
    }

    public static Card getFaceCard(Suit suit, char abbrev){
        int index = "JQKA".indexOf(abbrev);
        if (index > -1){
            return new Card(suit, abbrev + "", index + 11);
        }

        return null;
    }
}
