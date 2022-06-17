package me.gabl.library.eventbus;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface EventListener<E> {

    void onEvent(@Nullable E event);

    @NotNull Class<E> eventClass();
}
