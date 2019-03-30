package me.designpatterns.statemachine.straightforward;

public class TurnstileFsmSwitch extends TurnstileFsm {

    public TurnstileFsmSwitch(Turnstile turnstile) {
        super(turnstile);
    }

    public void handleEvent(TurnstileEvent turnstileEvent) {
        switch (state) {
            case LOCKED:
                switch (turnstileEvent) {
                    case COIN:
                        state = State.UNLOCKED;
                        turnstile.unlock();
                        break;
                    case PASS:
                        turnstile.alarm();
                        break;
                } break;
            case UNLOCKED:
                switch (turnstileEvent) {
                    case COIN:
                        turnstile.thankYou();
                        break;
                    case PASS:
                        state = State.LOCKED;
                        turnstile.lock();
                        break;
                } break;
        }
    }

    void setState(State state) {
        this.state = state;
    }

    State getState() {
        return state;
    }
}
