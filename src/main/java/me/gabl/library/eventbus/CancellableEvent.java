package me.gabl.library.eventbus;

public class CancellableEvent {

    private boolean cancelled = false;

    public boolean cancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void cancel() {
        this.cancelled = true;
    }
}
