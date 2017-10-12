package lab3;

import java.util.LinkedList;
import java.util.List;

public class Main {

    private static String expression = "repeat n:=n-1 until (n=0 or b!=a[n]);"; // expression
    private static List<Token> tokens = new LinkedList<>();                     // list of recognized tokens
    private static TokenType[] tokenTypes = new TokenType[] {                   // standard tokens
            new TokenType("repeat", "REPEAT"),
            new TokenType("until", "UNTIL"),
            new TokenType("or", "OR"),
            new TokenType(":=", "ASSIGN"),
            new TokenType("!=", "NOT_EQUALS"),
            new TokenType("=", "EQUALS"),
            new TokenType("-", "SUBTRACTION"),
            new TokenType("(", "LEFT_ROUND_BRACKET"),
            new TokenType(")", "RIGHT_ROUND_BRACKET"),
            new TokenType("[", "LEFT_SQUARE_BRACKET"),
            new TokenType("]", "RIGHT_SQUARE_BRACKET"),
            new TokenType(";", "SEMICOLON"),
            new TokenType(" ", "SEPARATOR")
    };

    public static void main(String[] args) {
        System.out.println(expression);
        parseExpression(expression);
        tokens.forEach(System.out::println);
    }

    public static void parseExpression(String expression) {
        String[] parts = expression.split(" ");
        for (String part : parts)
            parsePart(part);
    }

    public static void parsePart(String part) {
        if (part.length() == 0)
            return;
        // if part contains one of standard token
        for (TokenType type : tokenTypes) {
            String key = type.getKey();
            if (part.contains(key)) {
                int left  = part.indexOf(key);
                int right = left + type.getKey().length();
                parsePart(part.substring(0, left));
                tokens.add(new Token(key, type));
                parsePart(part.substring(right));
                return;
            }
        }

        // if part doesn't contain standard token then it is a variable or number
        try {
            Integer.parseInt(part);
            tokens.add(new Token(part, new TokenType("", "NUMBER")));
        } catch (NumberFormatException e) {
            tokens.add(new Token(part, new TokenType("", "VARIABLE")));
        }
    }

}
