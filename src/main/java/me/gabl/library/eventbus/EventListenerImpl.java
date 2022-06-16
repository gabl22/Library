package me.gabl.library.eventbus;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

final class EventListenerImpl<E> implements EventListener<E> {

    private final Class<E> eventClass;
    private final Consumer<E> handler;

    EventListenerImpl(Class<E> eventClass, Consumer<E> handler) {
        this.eventClass = eventClass;
        this.handler = handler;
    }


    @Override
    public void onEvent(E event) {
        if(handler == null)
            return;
        handler.accept(event);
    }

    @Override
    public @NotNull Class<E> eventClass() {
        return eventClass;
    }
}
