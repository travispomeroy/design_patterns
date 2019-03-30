package me.designpatterns.statemachine.straightforward;

import java.util.function.Consumer;

public class TurnstileFsmTable extends TurnstileFsm {

    public TurnstileFsmTable(Turnstile turnstile) {
        super(turnstile);
    }

    class Transition {
        public State currentState;
        public TurnstileEvent event;
        public State newState;
        public Consumer<Turnstile> action;

        Transition(State currentState, TurnstileEvent event, State newState,
                   Consumer<Turnstile> action) {
            this.currentState = currentState;
            this.event = event;
            this.newState = newState;
            this.action = action;
        }
    }

    private Transition[] transitions = new Transition[]{
            new Transition(State.LOCKED, TurnstileEvent.COIN, State.UNLOCKED,
                    Turnstile::unlock),
            new Transition(State.LOCKED, TurnstileEvent.PASS, State.LOCKED, Turnstile::alarm),
            new Transition(State.UNLOCKED, TurnstileEvent.COIN, State.UNLOCKED,
                    Turnstile::thankYou),
            new Transition(State.UNLOCKED, TurnstileEvent.PASS, State.LOCKED, Turnstile::lock)
    };

    public void handleEvent(TurnstileEvent event) {
        for (Transition t : transitions) {
            if (t.currentState == state && t.event == event) {
                state = t.newState;
                t.action.accept(turnstile);
                break;
            }
        }
    }
}

