package de.jakes_co.cellsim.util;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class ObserverManager<O extends Consumer<S>, S> {
    private int oldHash = 0;
    private final List<O> observers = new LinkedList<>();

    public void observe(O observer) {
        observers.add(observer);
    }

    public void change(S s) {
        int hash = s.hashCode();
        if (oldHash != hash) {
            oldHash = hash;
            observers.forEach(o -> o.accept(s));
        }
    }
}
