package me.gabl.library.validate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CheckTest {

    @Test
    void notNull() {
        assertThrows(NullPointerException.class, () -> Check.notNull("testing", (Object) null));
        assertDoesNotThrow(() -> Check.notNull("testing", "foo"));
        assertThrows(IllegalArgumentException.class, () -> Check.notNull("testing"));
    }

    @Test
    void assertTrue() {
        assertThrows(IllegalArgumentException.class, () -> Check.assertTrue("testing", false));
        assertDoesNotThrow(() -> Check.assertTrue("testing", true));
    }

    @Test
    void assertFalse() {
        assertThrows(IllegalArgumentException.class, () -> Check.assertFalse("testing", true));
        assertDoesNotThrow(() -> Check.assertFalse("testing", false));
    }
}
