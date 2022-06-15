package me.gabl.library.eventbus;

import me.gabl.library.util.PrioritisingMap;
import me.gabl.library.util.SimplePrioritisingMap;
import org.jetbrains.annotations.NotNull;

public class EventBusImpl<I, P extends Comparable<P>> {

    private final PrioritisingMap<I, EventListener, P> listeners;

    EventBusImpl() {
        this.listeners = new SimplePrioritisingMap<>();
    }

    public void fire(@NotNull Object event) {
        for(SimplePrioritisingMap.Node<I, EventListener, P> entry : listeners.entries()) {
            if(entry.value().eventClass().isAssignableFrom(event.getClass()))
                entry.value().onEvent(event);
        }
    }
}
