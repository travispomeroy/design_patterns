package me.designpatterns.statemachine.straightforward;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TurnstileFsmSwitchTest {

    private enum SubClass {SWITCH, TABLE}

    @Mock
    private Turnstile turnstile;

    private TurnstileFsm testSubject;

    @Test
    public void shouldUnlockTurnstileWhenGivenACoinAndTurnstileIsLocked() {
        givenTestSubject(SubClass.SWITCH);
        givenTurnstileIs(TurnstileFsmSwitch.State.LOCKED);
        whenHandleEvent(TurnstileFsmSwitch.TurnstileEvent.COIN);
        thenTurnstileFsmShouldBe(TurnstileFsmSwitch.State.UNLOCKED);
        thenTurnstileShouldBeUnlocked();

        givenTestSubject(SubClass.TABLE);
        givenTurnstileIs(TurnstileFsmSwitch.State.LOCKED);
        whenHandleEvent(TurnstileFsmSwitch.TurnstileEvent.COIN);
        thenTurnstileFsmShouldBe(TurnstileFsmSwitch.State.UNLOCKED);
        thenTurnstileShouldBeUnlocked();
    }

    @Test
    public void shouldSoundAlarmWhenTurnstileIsLockedAndSomeonePassesThrough() {
        givenTestSubject(SubClass.SWITCH);
        givenTurnstileIs(TurnstileFsmSwitch.State.LOCKED);
        whenHandleEvent(TurnstileFsmSwitch.TurnstileEvent.PASS);
        thenTurnstileFsmShouldBe(TurnstileFsmSwitch.State.LOCKED);
        thenTurnstileShouldBeShouldAlarm();

        givenTestSubject(SubClass.TABLE);
        givenTurnstileIs(TurnstileFsmSwitch.State.LOCKED);
        whenHandleEvent(TurnstileFsmSwitch.TurnstileEvent.PASS);
        thenTurnstileFsmShouldBe(TurnstileFsmSwitch.State.LOCKED);
        thenTurnstileShouldBeShouldAlarm();
    }

    @Test
    public void shouldSayThankYouWhenTurnstileIsUnlockedAndGivenACoin() {
        givenTestSubject(SubClass.SWITCH);
        givenTurnstileIs(TurnstileFsmSwitch.State.UNLOCKED);
        whenHandleEvent(TurnstileFsmSwitch.TurnstileEvent.COIN);
        thenTurnstileFsmShouldBe(TurnstileFsmSwitch.State.UNLOCKED);
        thenTurnstileShouldThankYou();

        givenTestSubject(SubClass.TABLE);
        givenTurnstileIs(TurnstileFsmSwitch.State.UNLOCKED);
        whenHandleEvent(TurnstileFsmSwitch.TurnstileEvent.COIN);
        thenTurnstileFsmShouldBe(TurnstileFsmSwitch.State.UNLOCKED);
        thenTurnstileShouldThankYou();
    }

    @Test
    public void shouldLockTurnstileWhenPassingThroughAnUnlockedTurnstile() {
        givenTestSubject(SubClass.SWITCH);
        givenTurnstileIs(TurnstileFsmSwitch.State.UNLOCKED);
        whenHandleEvent(TurnstileFsmSwitch.TurnstileEvent.PASS);
        thenTurnstileFsmShouldBe(TurnstileFsmSwitch.State.LOCKED);
        thenTurnstileShouldLock();

        givenTestSubject(SubClass.TABLE);
        givenTurnstileIs(TurnstileFsmSwitch.State.UNLOCKED);
        whenHandleEvent(TurnstileFsmSwitch.TurnstileEvent.PASS);
        thenTurnstileFsmShouldBe(TurnstileFsmSwitch.State.LOCKED);
        thenTurnstileShouldLock();
    }

    private void givenTestSubject(SubClass subClass) {
        switch (subClass) {
            case SWITCH:
                this.testSubject = new TurnstileFsmSwitch(turnstile);
                break;
            case TABLE:
                this.testSubject = new TurnstileFsmTable(turnstile);
                break;
        }

    }

    private void whenHandleEvent(TurnstileFsmSwitch.TurnstileEvent event) {
        this.testSubject.handleEvent(event);
    }

    private void givenTurnstileIs(TurnstileFsmSwitch.State state) {
        this.testSubject.setState(state);
    }

    private void thenTurnstileFsmShouldBe(TurnstileFsmSwitch.State expectedState) {
        assertThat(this.testSubject.getState()).isEqualTo(expectedState);
    }

    private void thenTurnstileShouldBeUnlocked() {
        verify(turnstile, atLeast(1)).unlock();
    }

    private void thenTurnstileShouldBeShouldAlarm() {
        verify(turnstile, atLeast(1)).alarm();
    }

    private void thenTurnstileShouldThankYou() {
        verify(turnstile, atLeast(1)).thankYou();
    }

    private void thenTurnstileShouldLock() {
        verify(turnstile, atLeast(1)).lock();
    }
}