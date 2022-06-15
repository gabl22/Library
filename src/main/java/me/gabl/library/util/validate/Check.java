package me.gabl.library.util.validate;

import me.gabl.library.util.text.TextFormat;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Check {

    private Check() {
        throw new UnsupportedOperationException();
    }

    @Contract("_, null -> fail")
    public static void notNull(@NotNull String reason, @Nullable Object... objects) {
        for(Object object : objects)
            if(Objects.isNull(object))
                throw new NullPointerException(TextFormat.format(reason));
    }

    @Contract("_, false -> fail")
    public static void assertTrue(@NotNull String reason, boolean b) {
        assertFalse(reason, !b);
    }

    @Contract("_, true -> fail")
    public static void assertFalse(@NotNull String reason, boolean b) {
        if(b)
            throw new IllegalArgumentException(TextFormat.format(reason));
    }
}
