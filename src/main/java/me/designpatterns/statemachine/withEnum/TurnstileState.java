package me.designpatterns.statemachine.withEnum;

public interface TurnstileState {

    void pass(TurnstileFsm turnstileFsm);

    void coin(TurnstileFsm turnstileFsm);
}
