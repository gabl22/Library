package me.gabl.library.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public interface PrioritisingMap<K, V, P extends Comparable<P>> {

    int size();

    boolean isEmpty();

    boolean containsKey(K key);

    boolean containsValue(V value);

    boolean containsPriorityLevel(P priorityLevel);

    V getValue(K key);

    P getPriorityLevel(K key);

    void put(K key, V value, P priorityLevel);

    void put(K key, P priorityLevel);

    void remove(K key);

    void putAll(@NotNull Collection<? extends SimplePrioritisingMap.Node<K, V, P>> nodes);

    void clear();

    @NotNull List<SimplePrioritisingMap.Node<K, V, P>> entries();

    void forEach(Consumer<? super SimplePrioritisingMap.Node<K, V, P>> action);

    @Nullable void putIfAbsent(K key, V value, P priorityLevel);

    void sort();
}
