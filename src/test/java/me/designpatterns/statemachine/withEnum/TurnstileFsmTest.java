package me.designpatterns.statemachine.withEnum;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TurnstileFsmTest extends TurnstileFsm{

    private TurnstileFsm turnstileFsm;
    private String actions;

    @Before
    public void before() {
        turnstileFsm = this;
        turnstileFsm.setState(OneCoinTurnstileState.LOCKED);
        actions = "";
    }

    @Test
    public void normalOperation() {
        coin();
        assertActions("U");
        pass();
        assertActions("UL");
    }

    @Test
    public void noCoinAndPass() {
        pass();
        assertActions("A");
    }

    @Test
    public void doublePayment() {
        coin();
        coin();
        assertActions("UT");
    }

    @Test
    public void manyCoinsThanPass() {
        for (int i = 0; i < 5; i++) {
            coin();
        }

        pass();
        assertActions("UTTTTL");
    }

    private void assertActions(String expectedAction) {
        assertThat(actions).isEqualTo(expectedAction);
    }

    @Override
    public void alarm() {
        this.actions += "A";
    }

    @Override
    public void lock() {
        this.actions += "L";
    }

    @Override
    public void unlock() {
        this.actions += "U";
    }

    @Override
    public void thankYou() {
        this.actions += "T";
    }
}