package me.designpatterns.statemachine.compiler.lexer;


import de.bechte.junit.runners.context.HierarchicalContextRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(HierarchicalContextRunner.class)
public class LexerTest implements TokenCollector {

    private String tokens = "";
    private Lexer lexer;
    private boolean isFirstToken = true;

    private void addToken(String token) {
        if (!isFirstToken)
            tokens += ",";
        tokens += token;
        isFirstToken = false;
    }

    private void assertLexResult(String input, String expected) {
        lexer.lex(input);
        assertThat(tokens).isEqualTo(expected);
    }

    @Override
    public void openBrace(int line, int pos) {
        addToken("OB");
    }

    @Override
    public void closeBrace(int line, int pos) {
        addToken("CB");
    }

    @Override
    public void openParen(int line, int pos) {
        addToken("OP");
    }

    @Override
    public void closeParen(int line, int pos) {
        addToken("CP");
    }

    @Override
    public void openAngle(int line, int pos) {
        addToken("OA");
    }

    @Override
    public void closeAngle(int line, int pos) {
        addToken("CA");
    }

    @Override
    public void dash(int line, int pos) {
        addToken("D");
    }

    @Override
    public void colon(int line, int pos) {
        addToken("C");
    }

    @Override
    public void name(String name, int line, int pos) {
        addToken("#" + name + "#");
    }

    @Override
    public void error(int line, int pos) {
        addToken("E" + line + "/" + pos);
    }

    @Before
    public void before() {
        this.lexer = new Lexer(this);
    }

    public class SingleTokenTests {
        @Test
        public void shouldFindOpenBrace() {
            assertLexResult("{", "OB");
        }

        @Test
        public void shouldFindCloseBrace() {
            assertLexResult("}", "CB");
        }

        @Test
        public void shouldFindOpenParen() {
            assertLexResult("(", "OP");
        }

        @Test
        public void shouldFindCloseParen() {
            assertLexResult(")", "CP");
        }

        @Test
        public void shouldFindOpenAngle() {
            assertLexResult("<", "OA");
        }

        @Test
        public void shouldFindCloseAngle() {
            assertLexResult(">", "CA");
        }

        @Test
        public void shouldFindDash() {
            assertLexResult("-", "D");
        }

        @Test
        public void shouldFindColon() {
            assertLexResult(":", "C");
        }

        @Test
        public void shouldFindName() {
            assertLexResult("name", "#name#");
        }

        @Test
        public void shouldFindNameWithNumber() {
            assertLexResult("Travis_123", "#Travis_123#");
        }

        @Test
        public void shoudlFindError() {
            assertLexResult(".", "E1/1");
        }

        @Test
        public void shouldDoNothingWithWhiteSpace() {
            assertLexResult("     ", "");
        }

        @Test
        public void shouldEatWhiteSpaceBeforeValidInput() {
            assertLexResult("    \t\n\t\t-", "D");
        }
    }

    public class MultipleTokenTests {
        @Test
        public void shouldFindOpenCloseBraces() {
            assertLexResult("{}", "OB,CB");
        }

        @Test
        public void shouldFindTokensInComplexString() {
            assertLexResult("FSM:fsm{this}", "#FSM#,C,#fsm#,OB,#this#,CB");
        }

        @Test
        public void shouldFindAllTokens() {
            assertLexResult("{}()<>-: name .", "OB,CB,OP,CP,OA,CA,D,C,#name#,E1/15");
        }

        @Test
        public void shouldFindTokensOnMultipleLines() {
            assertLexResult("FSM:fsm.\n{travis - .}", "#FSM#,C,#fsm#,E1/8,OB,#travis#,D," +
                    "E2/11,CB");
        }
    }
}