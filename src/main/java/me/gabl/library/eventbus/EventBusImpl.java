package me.gabl.library.eventbus;

import me.gabl.library.util.PrioritisingMap;
import me.gabl.library.util.SimplePrioritisingMap;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

import static me.gabl.library.validate.Check.*;

@SuppressWarnings("rawtypes")
public class EventBusImpl<I, P extends Comparable<P>> {

    private final PrioritisingMap<I, EventListener, P> listeners;

    EventBusImpl() {
        this.listeners = new SimplePrioritisingMap<>();
    }

    public void register(@NotNull I identifier, @NotNull EventListener listener, @NotNull P priorityLevel) {
        notNull("Identifier, listener and priority level must not be null.", identifier, listener, priorityLevel);
        assertFalse("Identifier must be unique. If you try to override a listener, make sure to unregister the old one first.",listeners.containsKey(identifier));
        this.listeners.put(identifier, listener, priorityLevel);
    }

    public void unregister(@NotNull I identifier) {
        notNull("Identifier must not be null.", identifier);
        this.listeners.remove(identifier);
    }

    public void unregisterIf(@NotNull Predicate<SimplePrioritisingMap.Node<I, EventListener, P>> predicate) {
        for(SimplePrioritisingMap.Node<I, EventListener, P> entry : listeners.entries())
            if(predicate.test(entry))
                this.unregister(entry.key());
    }

    public boolean registered(@NotNull I identifier) {
        notNull("Identifier must not be null.", identifier);
        return this.listeners.containsKey(identifier);
    }

    @SuppressWarnings("unchecked")
    public void fire(@NotNull Object event) {
        for(SimplePrioritisingMap.Node<I, EventListener, P> entry : listeners.entries()) {
            if(entry.value().eventClass().isAssignableFrom(event.getClass()))
                entry.value().onEvent(event);
        }
    }
}
