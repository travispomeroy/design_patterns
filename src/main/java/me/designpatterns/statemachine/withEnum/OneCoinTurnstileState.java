package me.designpatterns.statemachine.withEnum;

public enum OneCoinTurnstileState implements TurnstileState {

    LOCKED {
        @Override
        public void pass(TurnstileFsm turnstileFsm) {
            turnstileFsm.alarm();
        }

        @Override
        public void coin(TurnstileFsm turnstileFsm) {
            turnstileFsm.setState(UNLOCKED);
            turnstileFsm.unlock();
        }
    },
    UNLOCKED {
        @Override
        public void pass(TurnstileFsm turnstileFsm) {
            turnstileFsm.setState(LOCKED);
            turnstileFsm.lock();
        }

        @Override
        public void coin(TurnstileFsm turnstileFsm) {
            turnstileFsm.thankYou();
        }
    }
}
