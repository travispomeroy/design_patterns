package me.designpatterns.statemachine.compiler;

import static me.designpatterns.statemachine.compiler.FsmSyntax.*;
import static me.designpatterns.statemachine.compiler.FsmSyntax.SyntaxError.Type.*;

public class SyntaxBuilder implements Builder {

    private FsmSyntax fsm;
    private Header header;
    private String parsedName;
    private FsmSyntax.Transition transition;
    private SubTransition subtransition;

    public SyntaxBuilder() {
        fsm = new FsmSyntax();
    }

    @Override
    public void newHeaderWithName() {
        header = new Header();
        header.name = parsedName;
    }

    @Override
    public void addHeaderWithValue() {
        header.value = parsedName;
        fsm.headers.add(header);
    }

    @Override
    public void setStateName() {
        transition = new Transition();
        fsm.logic.add(transition);
        transition.state = new StateSpec();
        transition.state.name = parsedName;
    }

    @Override
    public void done() {
        fsm.done = true;
    }

    @Override
    public void setSuperStateName() {
        setStateName();
        transition.state.abstractState = true;
    }

    @Override
    public void setEvent() {
        subtransition = new SubTransition(parsedName);
    }

    @Override
    public void setNullEvent() {
        subtransition = new SubTransition(null);
    }

    @Override
    public void setEntryAction() {
        transition.state.entryActions.add(parsedName);
    }

    @Override
    public void setExitAction() {
        transition.state.exitActions.add(parsedName);
    }

    @Override
    public void setStateBase() {
        transition.state.superStates.add(parsedName);
    }

    @Override
    public void setNextState() {
        subtransition.nextState = parsedName;
    }

    @Override
    public void setNullNextState() {
        subtransition.nextState = null;
    }

    @Override
    public void transitionWithAction() {
        subtransition.actions.add(parsedName);
        transition.subTransitions.add(subtransition);
    }

    @Override
    public void transitionNullAction() {
        transition.subTransitions.add(subtransition);
    }

    @Override
    public void addAction() {
        subtransition.actions.add(parsedName);
    }

    @Override
    public void transitionWithActions() {
        transition.subTransitions.add(subtransition);
    }

    @Override
    public void headerError(ParserState state, ParserEvent event, int line, int pos) {
        fsm.errors.add(new SyntaxError(HEADER, state+"|"+event, line, pos));
    }

    @Override
    public void stateSpecError(ParserState state, ParserEvent event, int line, int pos) {
        fsm.errors.add(new SyntaxError(STATE, state+"|"+event, line, pos));
    }

    @Override
    public void transitionError(ParserState state, ParserEvent event, int line, int pos) {
        fsm.errors.add(new SyntaxError(TRANSITION, state+"|"+event, line, pos));
    }

    @Override
    public void transitionGroupError(ParserState state, ParserEvent event, int line, int pos) {
        fsm.errors.add(new SyntaxError(TRANSITION_GROUP, state+"|"+event, line, pos));
    }

    @Override
    public void endError(ParserState state, ParserEvent event, int line, int pos) {
        fsm.errors.add(new SyntaxError(END, state+"|"+event, line, pos));
    }

    @Override
    public void syntaxError(int line, int pos) {
        fsm.errors.add(new SyntaxError(SYNTAX, "", line, pos));
    }

    @Override
    public void setName(String name) {
        parsedName = name;
    }

    public FsmSyntax getFsm() {
        return fsm;
    }
}
