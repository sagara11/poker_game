package poker.services;

import poker.Card;

import java.util.List;
import java.util.function.Function;

public class Base {
    public static <T> List<T> getListOfElements(List<Card> cards, Function<Card, T> getElements){
        return cards.stream()
                .map(getElements)
                .toList();
    }
}
