package me.gabl.library.eventbus;

import me.gabl.library.util.SimplePrioritisingMap;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

@SuppressWarnings("rawtypes")
public interface EventBus<I, P extends Comparable<P>> {
    void register(I identifier, @NotNull EventListener listener, P priorityLevel);

    void unregister(I identifier);

    void unregisterIf(@NotNull Predicate<SimplePrioritisingMap.Node<I, EventListener, P>> predicate);

    boolean registered(I identifier);

    boolean fire(@NotNull Object event);
}