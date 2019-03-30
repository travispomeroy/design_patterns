package me.designpatterns.statemachine.straightforward;

public abstract class TurnstileFsm {

    public enum TurnstileEvent {COIN, PASS;}
    enum State {LOCKED, UNLOCKED;}
    protected State state = State.LOCKED;
    protected final Turnstile turnstile;

    protected TurnstileFsm(Turnstile turnstile) {
        this.turnstile = turnstile;
    }

    public abstract void handleEvent(TurnstileEvent event);

    void setState(State state) {
        this.state = state;
    }

    State getState() {
        return state;
    }
}
