package lab3;

import java.util.LinkedList;
import java.util.List;

public class Main {

    private static String expression = "repekat n:=ADA-10 until (a:=10 or b!=a[n]);"; // expression
    private static List<Token> tokens = new LinkedList<>();                     // list of recognized tokens
    private static TokenType[] tokenTypes = new TokenType[] {                   // standard tokens

    };

    public static void main(String[] args) {
        System.out.println(expression);
        parseExpression(expression);
        tokens.forEach(System.out::println);
    }

    public static void parseExpression(String expression) {
        String[] parts = expression.split(" ");
        for (int i = 0; i < parts.length - 1; i++) {
            parsePart(parts[i]);
            tokens.add(new Token(" ", tokenTypes[12]));
        }
        parsePart(parts[parts.length - 1]);
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
