import java.util.*;

public class CardRules {
    private final List<Card> deckCard;

    public CardRules(List<Card> deck) {
        this.deckCard = deck;
    }

    public void evaluate(List<Card> userCard) {
        List<Card> userCardCopy = new ArrayList<>(userCard);
        userCardCopy.addAll(deckCard);
        userCardCopy.sort(Comparator.comparing(Card::getRank));
    }
}
