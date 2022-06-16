package me.gabl.library.eventbus;

import org.jetbrains.annotations.NotNull;

public interface EventListener<E> {

    void onEvent(E event);

    @NotNull Class<E> eventClass();
}
