package org.poker_game;

import java.util.List;

public record TestStructure<T>(List<Card> cards, T expectedValue) {
}
