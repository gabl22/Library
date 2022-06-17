package me.gabl.library.util;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

public class SimplePrioritisingMap<K, V, P extends Comparable<P>> implements PrioritisingMap<K, V, P> {

    private final List<Node<K, V, P>> nodes;

    public SimplePrioritisingMap(List<Node<K, V, P>> nodes) {
        this.nodes = List.copyOf(nodes);
    }

    public SimplePrioritisingMap() {
        this.nodes = new LinkedList<>();
    }

    public int size() {
        return this.nodes.size();
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public boolean containsKey(K key) {
        return node(key) != null;
    }

    public boolean containsValue(V value) {
        for(SimplePrioritisingMap.Node<K, V, P> node : this.nodes)
            if(node.value().equals(value))
                return true;
        return false;
    }

    public boolean containsPriorityLevel(P priorityLevel) {
        for(SimplePrioritisingMap.Node<K, V, P> node : this.nodes)
            if(node.priorityLevel().equals(priorityLevel))
                return true;
        return false;
    }

    public V getValue(K key) {
        SimplePrioritisingMap.Node<K, V, P> node = this.node(key);
        if(Objects.isNull(node))
            return null;
        return node.value();
    }

    public P getPriorityLevel(K key) {
        SimplePrioritisingMap.Node<K, V, P> node = this.node(key);
        if(Objects.isNull(node))
            return null;
        return node.priorityLevel();
    }

    public void put(K key, V value, P priorityLevel) {
        if(containsKey(key))
            this.node(key).value(value).priorityLevel(priorityLevel);
        else
            this.nodes.add(SimplePrioritisingMap.Node.of(key, value, priorityLevel));
        this.sort();
    }

    public void put(K key, P priorityLevel) {
        put(key, null, priorityLevel);
    }

    private Node<K, V, P> node(K key) {
        for(Node<K, V, P> node : this.nodes)
            if(node.key().equals(key))
                return node;
        return null;
    }

    @Override
    public void remove(K key) {
        this.nodes.remove(node(key));
    }

    @Override
    public void putAll(@NotNull Collection<? extends Node<K, V, P>> nodes) {
        this.nodes.addAll(nodes);
    }

    @Override
    public void clear() {
        this.nodes.clear();
    }

    @NotNull
    @Override
    public List<PrioritisingMap.Node<K, V, P>> entries() {
        return Collections.unmodifiableList(nodes);
    }

    @Override
    public void forEach(Consumer<? super Node<K, V, P>> action) {
        this.nodes.forEach(action);
    }

    @Override
    public void putIfAbsent(K key, V value, P priorityLevel) {
        if(containsKey(key))
            return;
        this.nodes.add(Node.of(key, value, priorityLevel));
        this.sort();
    }

    @Override
    public void sort() {
        this.nodes.sort(Node::compareTo);
    }

    public static class Node<K, V, P extends Comparable<P>> implements PrioritisingMap.Node<K, V, P> {

        private final K key;
        private V value;
        private P priorityLevel;

        Node(K key, V value, P priorityLevel) {
            this.key = key;
            this.value = value;
            this.priorityLevel = priorityLevel;
        }

        public static <K, V, P extends Comparable<P>> Node<K, V, P> of(K key, V value, P priorityLevel) {
            return new Node<>(key, value, priorityLevel);
        }

        @Override
        public K key() {
            return key;
        }

        @Override
        public V value() {
            return value;
        }

        @Override
        public Node<K, V, P> value(V value) {
            this.value = value;
            return this;
        }

        @Override
        public P priorityLevel() {
            return priorityLevel;
        }

        @Override
        public Node<K, V, P> priorityLevel(P priorityLevel) {
            this.priorityLevel = priorityLevel;
            return this;
        }

        @Override
        public int compareTo(@NotNull PrioritisingMap.Node<K, V, P> that) {
            return this.priorityLevel.compareTo(that.priorityLevel());
        }
    }
}
