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
import org.poker_game.services.Mocking;
import org.poker_game.services.User;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeckTest {
    @Mock
    static List<Player> players = new ArrayList<>();

    @BeforeAll
    static void setup() {
        System.out.println("Create 4 players and add to deck...");
        for (int i = 0; i < 3; i++){
            players.add(new Player(anyString()));
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
