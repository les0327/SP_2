package lab4.lexer;

import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
public class Lexer {

    private List<Token> tokenList = new LinkedList<>();
    private Type[] types = new Type[] {
            new Type(":=", "ASSIGN"),
            new Type("<>", "NOT_EQUALS"),
            new Type("=", "EQUALS"),
            new Type("-", "SUBTRACTION"),
            new Type("(", "LEFT_ROUND_BRACKET"),
            new Type(")", "RIGHT_ROUND_BRACKET"),
            new Type("[", "LEFT_SQUARE_BRACKET"),
            new Type("]", "RIGHT_SQUARE_BRACKET"),
            new Type("!", "UNRECOGNIZED"),
            new Type("<", "UNRECOGNIZED"),
            new Type(">", "UNRECOGNIZED"),
            new Type(";", "SEMICOLON"),
            new Type(" ", "SEPARATOR")
    };
    private List<Type> keyWords = new ArrayList<Type>();
    private String exp;

    public List<Token> parseExpression(String expression) throws Exception{
        this.exp = expression;
        tokenList.clear();
        parse(expression);
        concat();
        setIndexes();
        error();
        arrElements();
        removeSeparators();
        return tokenList;
    }

    private void parse(String expression) {
        if (expression.length() == 0)
            return;
        for (Type type : types) {
            String key = type.getKey();
            if (expression.contains(key)) {
                int left  = expression.indexOf(key);
                int right = left + type.getKey().length();
                parse(expression.substring(0, left));
                tokenList.add(new Token(key, type));
                parse(expression.substring(right));
                return;
            }
        }

        //using try-catch box, check value to conformity to NUMBER or VARIABLE
        try {
            Integer.parseInt(expression);
            tokenList.add(new Token(expression, new Type("", "NUMBER")));
        } catch (NumberFormatException e) {
            tokenList.add(new Token(expression, new Type("", "VARIABLE")));
        }
    }

    private void concat() {
        keyWords.add(new Type("", "VARIABLE"));
        keyWords.add(new Type("", "NUMBER"));

        ListIterator<Token> tokenItr = tokenList.listIterator();
        boolean flag = true;
        Token token = null;
        while (tokenItr.hasNext()) {
            if (flag)
                token = tokenItr.next();
            if (keyWords.contains(token.getTokenType())) {
                if (tokenItr.hasNext()) {
                    Token nextToken = tokenItr.next();
                    if (keyWords.contains(nextToken.getTokenType())) {
                        tokenItr.remove();
                        tokenItr.previous();
                        tokenItr.remove();
                        tokenItr.add(token = new Token(token.getValue() + nextToken.getValue(), new Type("", "VARIABLE")));
                        flag = false;
                    } else {
                        flag = true;
                    }
                }
            }
        }
    }

    private void setIndexes() {
        int sum = 0;
        for (Token token : tokenList) {
            String value = token.getValue();
            int index = exp.indexOf(value);
            token.setIndex(index + sum);
            sum += value.length();
            exp = exp.substring(value.length());
        }
    }

    private void error() throws LexicalException{
        for (Token token : tokenList) {
            if (token.getTokenType().getValue().equals("VARIABLE")) {
                String str = token.getValue();
                try {
                    Integer.parseInt(str.substring(0, 1));
                    throw new LexicalException("LexicalException at index " + token.getIndex() +
                            ": Variable '" + token.getValue() + "' starts with number");
                } catch (NumberFormatException e) {

                }
            }
            if (token.getTokenType().getValue().equals("UNRECOGNIZED")) {
                throw new LexicalException("LexicalException at index " + token.getIndex() +
                        ": Unrecognized symbol '" + token.getValue());
            }
        }
    }

    private void arrElements() {
        Token[] arr = new Token[4];
        boolean flag = true;
        a:while (flag) {
            for (int i = 0; i < tokenList.size() - 4; i++) {
                arr[0] = tokenList.get(i);
                if (arr[0].getTokenType().getValue().equals("VARIABLE")) {
                    arr[1] = tokenList.get(i + 1);
                    if (arr[1].getTokenType().getValue().equals("LEFT_SQUARE_BRACKET")) {
                        arr[2] = tokenList.get(i + 2);
                        if (arr[2].getTokenType().getValue().equals("VARIABLE") || arr[2].getTokenType().getValue().equals("NUMBER")) {
                            arr[3] = tokenList.get(i + 3);
                            if (arr[3].getTokenType().getValue().equals("RIGHT_SQUARE_BRACKET")) {
                                tokenList.add(tokenList.indexOf(arr[0]), new Token(arr[0].getValue() + arr[1].getValue() +
                                        arr[2].getValue() + arr[3].getValue(), new Type("", "VARIABLE"), arr[0].getIndex()));
                                tokenList.removeAll(Arrays.asList(arr));
                                continue a;
                            }
                        }
                    }
                }
            }
            flag = false;
        }
    }

    private void removeSeparators() {
        tokenList = tokenList.stream().filter(token -> !token.getTokenType().getValue().equals("SEPARATOR")).collect(Collectors.toList());
    }

    private class LexicalException extends Exception {

        public LexicalException(String s) {
            super(s);
        }
    }
}
