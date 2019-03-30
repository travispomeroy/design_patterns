package me.designpatterns.statemachine.withEnum;

public abstract class TurnstileFsm {

    private TurnstileState state;

    public void pass() {
        this.state.pass(this);
    }

    public void coin() {
        this.state.coin(this);
    }

    public void setState(TurnstileState state) {
        this.state = state;
    }

    public abstract void alarm();
    public abstract void lock();
    public abstract void unlock();
    public abstract void thankYou();
}


