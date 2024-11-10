package org.poker_game.enums;

public enum DeckStatus {
    NULL,
    FLOP,
    TURN,
    RIVER;

    public static DeckStatus changeState(DeckStatus status){
        if (status == RIVER){
            status = FLOP;
        }
        int nextState = status.ordinal() + 1;
        return DeckStatus.values()[nextState];
    }
}
