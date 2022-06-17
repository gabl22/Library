package me.gabl.library.eventbus;

import me.gabl.library.util.SimplePrioritisingMap;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

public class EventBusTest {
    //GeneralEvent
    //SpecifiedAEvent
    //SpecifiedBEvent extends Cancellable

    @Test
    void registeringTest() {
        EventBus<String, Integer> bus = new EventBusImpl<>();
        EventListener<Object> listener = new EventListenerImpl<>(Object.class, (o) -> {
        });

        assertFalse(bus.registered("Event"));
        bus.register("Event", listener, 0);
        assertTrue(bus.registered("Event"));
        bus.unregister("Event");
        assertFalse(bus.registered("Event"));

        assertDoesNotThrow(() -> bus.register("Event", listener, 0));
        assertThrows(IllegalArgumentException.class, () -> bus.register("Event", listener, 0));
        assertTrue(bus.registered("Event"));
        bus.unregisterAll();
        assertFalse(bus.registered("Event"));

        assertThrows(NullPointerException.class, () -> bus.register(null, listener, 0));
        assertThrows(NullPointerException.class, () -> bus.register("Event", null, 0));
        assertThrows(NullPointerException.class, () -> bus.register("Event", listener, null));
        assertThrows(NullPointerException.class, () -> bus.registered(null));

        bus.register("0", listener, 0);
        bus.register("1", listener, 1);
        bus.register("2", listener, 2);
        assertTrue(bus.registered("0"));
        assertTrue(bus.registered("1"));
        assertTrue(bus.registered("2"));

        bus.unregisterIf(node -> node.priorityLevel() < 1);
        assertFalse(bus.registered("0"));
        assertTrue(bus.registered("1"));
        assertTrue(bus.registered("2"));
    }

    @Test
    void fireTest() {
        AtomicInteger generalIndicator = new AtomicInteger();
        AtomicInteger aIndicator = new AtomicInteger();
        AtomicInteger bIndicator = new AtomicInteger();

        EventBus<String, Integer> bus = new EventBusImpl<>();
        EventListener<GeneralEvent> generalListener = new EventListenerImpl<>(GeneralEvent.class, (event) -> generalIndicator.incrementAndGet());
        EventListener<SpecifiedAEvent> aListener = new EventListenerImpl<>(SpecifiedAEvent.class, (event) -> aIndicator.incrementAndGet());
        EventListener<SpecifiedBEvent> bListener = new EventListenerImpl<>(SpecifiedBEvent.class, (event) -> bIndicator.incrementAndGet());
        EventListener<SpecifiedBEvent> cListener = new EventListenerImpl<>(SpecifiedBEvent.class, CancellableEvent::cancel);

        bus.register("general", generalListener, 0);
        bus.register("a", aListener, 0);
        bus.register("b", bListener, 0);

        assertTrue(() -> bus.fire(new Object()));
        assertTrue(() -> bus.fire("foo"));
        assertTrue(() -> bus.fire(null));
        assertEquals(0, generalIndicator.get());
        assertEquals(0, aIndicator.get());
        assertEquals(0, bIndicator.get());

        assertTrue(() -> bus.fire(new GeneralEvent(){}));
        assertEquals(1, generalIndicator.get());
        assertEquals(0, aIndicator.get());
        assertEquals(0, bIndicator.get());

        assertTrue(() -> bus.fire(new SpecifiedAEvent()));
        assertEquals(2, generalIndicator.get());
        assertEquals(1, aIndicator.get());
        assertEquals(0, bIndicator.get());

        assertTrue(() -> bus.fire(new SpecifiedBEvent()));
        assertEquals(3, generalIndicator.get());
        assertEquals(1, aIndicator.get());
        assertEquals(1, bIndicator.get());

        bus.register("c", cListener, 0);

        assertFalse(() -> bus.fire(new SpecifiedBEvent()));
        assertTrue(() -> bus.fire(new SpecifiedAEvent()));
    }

    @Test
    void prioritisedFireTest() {
        AtomicInteger indicator = new AtomicInteger(1);
        EventBus<String, Integer> bus = new EventBusImpl<>();
        EventListener<Object> addingListener = new EventListenerImpl<>(Object.class, (object) -> indicator.addAndGet(2));
        EventListener<Object> multiplyingListener = new EventListenerImpl<>(Object.class, (object) -> indicator.set(indicator.get()*2));
        bus.register("A", multiplyingListener, 0);
        bus.register("B", addingListener, 100);

        bus.fire(null);
        assertEquals(4, indicator.getAndSet(1));

        bus.unregisterAll();
        bus.register("A", multiplyingListener, 100);
        bus.register("B", addingListener, 0);

        bus.fire(null);
        assertEquals(6, indicator.getAndSet(0));
    }

    interface GeneralEvent {
    }

    static class SpecifiedAEvent implements GeneralEvent {
    }

    static class SpecifiedBEvent extends CancellableEvent implements GeneralEvent {
    }
}
