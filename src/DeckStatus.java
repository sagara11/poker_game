public enum DeckStatus {
    FLOP,
    TURN,
    RIVER;

    public DeckStatus changeState(DeckStatus status){
        int nextState = status.ordinal() + 1;
        return DeckStatus.values()[nextState];
    }
}
