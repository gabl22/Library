package me.gabl.library.eventbus;

import me.gabl.library.util.PrioritisingMap;
import me.gabl.library.util.SimplePrioritisingMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

import static me.gabl.library.validate.Check.*;

@SuppressWarnings("rawtypes")
public class EventBusImpl<I, P extends Comparable<P>> implements EventBus<I, P> {

    private final PrioritisingMap<I, EventListener, P> listeners;

    EventBusImpl() {
        this.listeners = new SimplePrioritisingMap<>();
    }

    @Override
    public void register(I identifier, EventListener listener, P priorityLevel) {
        notNull("Identifier, listener and priority level must not be null.", identifier, listener, priorityLevel);
        assertFalse("Identifier must be unique. If you try to override a listener, make sure to unregister the old one first.",listeners.containsKey(identifier));
        this.listeners.put(identifier, listener, priorityLevel);
    }

    @Override
    public void unregister(I identifier) {
        notNull("Identifier must not be null.", identifier);
        this.listeners.remove(identifier);
    }

    @Override
    public void unregisterIf(@NotNull Predicate<PrioritisingMap.Node<I, EventListener, P>> predicate) {
        for(PrioritisingMap.Node<I, EventListener, P> entry : List.copyOf(listeners.entries()))
            if(predicate.test(entry))
                this.unregister(entry.key());
    }

    @Override
    public void unregisterAll() {
        this.listeners.clear();
    }

    @Override
    public boolean registered(I identifier) {
        notNull("Identifier must not be null.", identifier);
        return this.listeners.containsKey(identifier);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean fire(@Nullable Object event) {
        for(PrioritisingMap.Node<I, EventListener, P> entry : listeners.entries()) {
            if((event == null && entry.value().eventClass() != Object.class))
                continue;
            if(entry.value().eventClass().isAssignableFrom(event.getClass()))
                entry.value().onEvent(event);
            if(event instanceof CancellableEvent cEvent && cEvent.cancelled())
                return false;
        }
        return true;
    }
}
