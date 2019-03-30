package me.designpatterns.statemachine.compiler.lexer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private TokenCollector tokenCollector;
    private int lineNumber;
    private int position;

    public Lexer(TokenCollector tokenCollector) {
        this.tokenCollector = tokenCollector;
    }

    public void lex(String s) {
        lineNumber = 1;
        String[] lines = s.split("\n");
        for (String line : lines) {
            lexLine(line);
            lineNumber++;
        }
    }

    private void lexLine(String line) {
        for (position = 0; position < line.length();) {
            lexToken(line);
        }
    }

    private void lexToken(String line) {
        if (!findToken(line)) {
            tokenCollector.error(lineNumber, position + 1);
            position++;
        }
    }

    private boolean findToken(String line) {
        return findWhiteSpace(line) || findSingleCharacterToken(line) || findName(line);
    }

    private static final Pattern WHITE_SPACE_PATTERN = Pattern.compile("^\\s+");

    private boolean findWhiteSpace(String line) {
        Matcher matcher = WHITE_SPACE_PATTERN.matcher(line.substring(position));
        if (matcher.find()) {
            position += matcher.end();
            return true;
        }

        return false;
    }

    private boolean findSingleCharacterToken(String line) {
        String c = line.substring(position, position + 1);
        switch (c) {
            case "{":
                tokenCollector.openBrace(lineNumber, position);
                break;
            case "}":
                tokenCollector.closeBrace(lineNumber, position);
                break;
            case "(":
                tokenCollector.openParen(lineNumber, position);
                break;
            case ")":
                tokenCollector.closeParen(lineNumber, position);
                break;
            case "<":
                tokenCollector.openAngle(lineNumber, position);
                break;
            case ">":
                tokenCollector.closeAngle(lineNumber, position);
                break;
            case "-":
                tokenCollector.dash(lineNumber, position);
                break;
            case ":":
                tokenCollector.colon(lineNumber, position);
                break;
            default:
                return false;
        }

        position++;
        return true;
    }

    private static final Pattern NAME_PATTERN = Pattern.compile("^\\w+");

    private boolean findName(String line) {
        Matcher matcher = NAME_PATTERN.matcher(line.substring(position));
        if (matcher.find()) {
            tokenCollector.name(matcher.group(0), lineNumber, position);
            position += matcher.end();
            return true;
        }

        return false;
    }
}
