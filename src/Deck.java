import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> standardCardOnDeck;
    private List<Card> cardOnDeck;
    private List<Player> players;
    private int cardOpenedCount = 0;
    private DeckStatus status = DeckStatus.FLOP;

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Card> getStandardCardOnDeck() {
        return standardCardOnDeck;
    }

    public void setStandardCardOnDeck(List<Card> standardCardOnDeck) {
        this.standardCardOnDeck = standardCardOnDeck;
    }

    public Deck(List<Player> players){
        this();
        this.players = players;
    }

    public Deck(){
        List<Card> result = new ArrayList<>(52);
        for (Suit suit : Suit.values()){
            for (int i = 2; i <= 10; i ++){
                result.add(Card.getNumericCard(suit, i));
            }

            for (char s : new char[]{'J', 'Q', 'K', 'A'}){
                result.add(Card.getFaceCard(suit, s));
            }
        }
        this.standardCardOnDeck = result;
    }

    public void printDeck(String description, int row){
        int cardPerRow = standardCardOnDeck.size() / row;
        for (int i = 0; i < row; i ++){
            int startIndex = i * cardPerRow;
            int endIndex = startIndex + cardPerRow;
            System.out.println(standardCardOnDeck.subList(startIndex, endIndex));
        }
        System.out.println(description);
    }

    public void printDeck(){
        printDeck("Current Deck", 4);
    }

    public void dealingCards(){
        Collections.shuffle(standardCardOnDeck);

        for (int i = 0; i < 2; i++){
            for (Player player : players){
                player.receiveCard(standardCardOnDeck.get(cardOpenedCount++));
            }
        }
    }

    public void openCard(){
        int cardsToOpen = switch (status) {
            case TURN, RIVER -> 1;
            default -> 3;
        };
        status.changeState(status);

        while ()
    }
}
