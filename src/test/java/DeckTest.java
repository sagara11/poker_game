import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.poker_game.Deck;
import org.poker_game.Player;
import org.poker_game.enums.DeckStatus;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class DeckTest {
    public static List<Player> players = new ArrayList<>();

    private static final String[] names = {"Tommy", "Grace", "Sheldon", "Penny"};

    @BeforeAll
    static void setup() {
        System.out.println("Create 4 players and add to deck...");
        for (var name : names){
            players.add(new Player(name));
        }
    }

    @Test
    @DisplayName("Initiate Deck Object successfully")
    void initiateDeck() {
        Deck deck = new Deck(players);

        Assertions.assertEquals(4, deck.getPlayers().size());
        Assertions.assertEquals(DeckStatus.FLOP, deck.getStatus());
        Assertions.assertEquals(52, deck.getStandardCardOnDeck().size());
    }
}
