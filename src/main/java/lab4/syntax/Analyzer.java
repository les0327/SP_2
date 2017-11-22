package lab4.syntax;

import lab4.lexer.Token;

import java.util.List;
import java.util.ListIterator;

public class Analyzer {

    public void parse(List<Token> tokenList) throws SyntaxException{
        calcBrackets(tokenList);
        ListIterator<Token> itr = tokenList.listIterator();
        Token token = itr.next();
        Token nextToken;
        int assignCount =  0;
        while(itr.hasNext()) {
            nextToken = itr.next();
            if (nextToken.getValue().equals(":=")) {
                assignCount++;
            }
            if (nextToken.getValue().equals(";")) {
                assignCount = 0;
            }

            if (assignCount > 1) {
                throw new SyntaxException("SyntaxException: > 1 ':=' in expression at index" + nextToken.getIndex());
            }

            if (!isExpected(token.getTokenType().getValue(), nextToken.getTokenType().getValue()))
                throw new SyntaxException("SyntaxException at index " + nextToken.getIndex() +
                        ": Expected '" + getExpected(token.getTokenType().getValue()) +
                        "'; Get '" + nextToken.getValue() + "'");

            token = nextToken;
        }
    }

    private boolean isExpected(String first, String second) {
        switch (first) {
            case "ASSIGN":
                return second.equals("VARIABLE") || second.equals("NUMBER") || second.equals("LEFT_ROUND_BRACKET");
            case "VARIABLE":
                return second.equals("ASSIGN") || second.equals("SUBTRACTION") || second.equals("EQUALS")
                        || second.equals("RIGHT_ROUND_BRACKET") || second.equals("SEMICOLON") || second.equals("NOT_EQUALS")
                        || second.equals("LEFT_SQUARE_BRACKET");
            case "NUMBER":
                return second.equals("SUBTRACTION") || second.equals("EQUALS") || second.equals("RIGHT_ROUND_BRACKET")
                        || second.equals("SEMICOLON") || second.equals("NOT_EQUALS");
            case "EQUALS":
                return second.equals("VARIABLE") || second.equals("NUMBER") || second.equals("LEFT_ROUND_BRACKET");
            case "NOT_EQUALS":
                return second.equals("VARIABLE") || second.equals("NUMBER") || second.equals("LEFT_ROUND_BRACKET");
            case "SUBTRACTION":
                return second.equals("VARIABLE") || second.equals("NUMBER") || second.equals("LEFT_ROUND_BRACKET");
            case "LEFT_ROUND_BRACKET":
                return second.equals("VARIABLE") || second.equals("NUMBER") || second.equals("SUBTRACTION");
            case "RIGHT_ROUND_BRACKET":
                return second.equals("SUBTRACTION") || second.equals("EQUALS") || second.equals("SEMICOLON") || second.equals("NOT_EQUALS");
            case "SEMICOLON":
                return second.equals("VARIABLE");
            default:
                return false;
        }
    }

    private String getExpected(String value) {
        switch (value) {
            case "ASSIGN":
                return "VALUE";
            case "VARIABLE":
                return ";";
            case "NUMBER":
                return ";";
            case "EQUALS":
                return "VALUE";
            case "SUBTRACTION":
                return "VALUE";
            case "LEFT_ROUND_BRACKET":
                return "VALUE";
            case "RIGHT_SQUARE_BRACKET":
                return ";";
            default:
                return "Unrecognized symbol - '" + value + "'";
        }
    }

    private void calcBrackets(List<Token> tokenList) throws SyntaxException{
        int countRound  = 0;
        int countSquare = 0;
        for (Token token : tokenList) {
            switch (token.getTokenType().getKey()) {
                case "(":
                    countRound++;
                    break;
                case ")":
                    countRound--;
                    break;
                case "[":
                    countSquare++;
                    break;
                case "]":
                    countSquare--;
                    break;
            }
        }

        if (countRound > 0)
            throw new SyntaxException("SyntaxException: missing ')'");
        if (countRound < 0)
            throw new SyntaxException("SyntaxException: missing '('");
        if (countSquare > 0)
            throw new SyntaxException("SyntaxException: missing ']'");
        if (countSquare < 0)
            throw new SyntaxException("SyntaxException: missing '['");
    }
}
