package lab6;

import lab6.ast.Arrays;
import lab6.ast.DataType;
import lab6.ast.NumberValue;
import lab6.ast.Variables;
import lab6.ast.expression.*;
import lab6.ast.statement.AssignmentStatement;
import lab6.ast.statement.BlockStatement;
import lab6.ast.statement.Statement;
import lab6.exceptions.SyntaxException;

import java.util.List;

import static lab6.TokenType.*;

public final class Parser {

    public static Statement parse(List<Token> tokens) {
        return new Parser(tokens).parse();
    }

    private static final Token EOF = new Token(TokenType.EOF, "", -1, -1);

    private final List<Token> tokens;
    private final int size;

    private int pos;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        size = tokens.size();
    }

    public Statement parse() {
        final BlockStatement result = new BlockStatement();
        while (!match(TokenType.EOF)) {
            result.add(statement());
        }
        return result;
    }

    private Statement statement() {

        if (lookMatch(0, VAR) && lookMatch(1, COLON) && lookMatch(2, ARRAY)) {
            return arrayStatement();
        }

        if (lookMatch(0, VAR) && lookMatch(1, COLON)) {
            return defineStatement();
        }
        return assignmentStatement();
    }

    private Statement arrayStatement() {
        String variable = consume(VAR).getText();
        TokenType tokenType = null;
        DataType type = null;
        int start = 0;
        int end   = 0;
        consume(COLON);
        consume(ARRAY);
        consume(LEFT_SQUARE_BRACKET);
        start = Integer.parseInt(get(0).getText());
        consume(LONGINT);
        consume(POINT_POINT);
        end   = Integer.parseInt(get(0).getText());
        consume(LONGINT);
        consume(RIGHT_SQUARE_BRACKET);
        consume(OF);
        switch (get(0).getType()) {
            case REAL:
                tokenType = REAL;
                type = DataType.Real;
                break;
            case LONGINT:
                tokenType = LONGINT;
                type = DataType.LongInt;
                break;
            case BOOLEAN:
                tokenType = BOOLEAN;
                type = DataType.Boolean;
                break;
            default:
                throw new SyntaxException("Expected type, get '" + get(0).getText() + "'");
        }
        consume(tokenType);
        consume(SEMI_COLON);
        return new DefineStatement(variable, new ArrayExpression(Arrays.define(variable, type, start, end), null), true);
    }

    private Statement defineStatement() {
        String variable = consume(VAR).getText();
        TokenType tokenType = null;
        DataType type = null;
        consume(COLON);

        switch (get(0).getType()) {
            case REAL:
                tokenType = REAL;
                type = DataType.Real;
                break;
            case LONGINT:
                tokenType = LONGINT;
                type = DataType.LongInt;
                break;
            case BOOLEAN:
                tokenType = BOOLEAN;
                type = DataType.Boolean;
                break;
            default:
                throw new SyntaxException("Expected type, get '" + get(0).getText() + "'");
        }
        consume(tokenType);
        consume(SEMI_COLON);
        return new DefineStatement(variable, new VariableExpression(Variables.define(variable, type)), false);
    }

    private Statement assignmentStatement() {
        if (lookMatch(0, VAR) && lookMatch(1, LEFT_SQUARE_BRACKET)) {
            String variable = consume(VAR).getText();
            consume(LEFT_SQUARE_BRACKET);
            Expression e = expression();
            consume(RIGHT_SQUARE_BRACKET);
            consume(ASSIGN);
            Statement s = new AssignmentStatement(variable, expression(), true);
            consume(SEMI_COLON);
            return s;
        }
        if (lookMatch(0, VAR) && lookMatch(1, ASSIGN)) {
            String variable = consume(VAR).getText();
            consume(ASSIGN);
            Statement s = new AssignmentStatement(variable, expression(), false);
            consume(SEMI_COLON);
            Variables.assign(variable);
            return s;
        }
        throw new SyntaxException(get(0).getRow(), get(0).getCol(), "Expected next statement, get '" + get(0).getText() + "'");
    }

    private Expression expression() {
        return equality();
    }

    private Expression equality() {
        Expression result = conditional();

        if (match(TokenType.EQUALS)) {
            return new ConditionalExpression(ConditionalExpression.Operator.EQUALS, result, conditional());
        }
        if (match(TokenType.NOT_EQUALS)) {
            return new ConditionalExpression(ConditionalExpression.Operator.NOT_EQUALS, result, conditional());
        }

        return result;
    }

    private Expression conditional() {
        Expression result = additive();

        while (true) {
            if (match(TokenType.LESS)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.LT, result, additive());
                continue;
            }
            if (match(TokenType.LESS_EQUALS)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.LTEQ, result, additive());
                continue;
            }
            if (match(TokenType.MORE)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.GT, result, additive());
                continue;
            }
            if (match(TokenType.MORE_EQUALS)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.GTEQ, result, additive());
                continue;
            }
            break;
        }

        return result;
    }

    private Expression additive() {
        Expression result = unary();

        while (true) {
            if (match(TokenType.PLUS)) {
                result = new BinaryExpression(BinaryExpression.Operator.ADD, result, unary());
                continue;
            }
            if (match(TokenType.MINUS)) {
                result = new BinaryExpression(BinaryExpression.Operator.SUBTRACT, result, unary());
                continue;
            }
            break;
        }

        return result;
    }

    private Expression unary() {
        if (match(TokenType.MINUS)) {
            return new UnaryExpression(UnaryExpression.Operator.NEGATE, primary());
        }
        if (match(TokenType.PLUS)) {
            return primary();
        }
        return primary();
    }

    private Expression primary() {
        final Token current = get(0);
        if (match(TokenType.LONGINT)) {
            return new NumberExpression(new NumberValue(Integer.parseInt(current.getText()), DataType.LongInt));
        }
        if (match(VAR)) {
            if (match(LEFT_SQUARE_BRACKET)) {
                Arrays.get(current.getText());
                Expression e = new ArrayExpression(Arrays.get(current.getText()), expression());
                consume(RIGHT_SQUARE_BRACKET);
                return e;
            }
            Variables.get(current.getText());
            return new VariableExpression(Variables.get(current.getText()));
        }
        if (match(TRUE)) {
            return new NumberExpression(new NumberValue(1, DataType.Boolean));
        }
        if (match(FALSE)) {
            return new NumberExpression(new NumberValue(0, DataType.Boolean));
        }
        if (match(TokenType.LEFT_ROUND_BRACKET)) {
            Expression result = expression();
            match(TokenType.RIGHT_ROUND_BRACKET);
            return result;
        }

        throw new SyntaxException(current.getRow(), current.getCol(), "Unknown expression: " + current);
    }

    private Token consume(TokenType type) {
        final Token current = get(0);
        if (type != current.getType())
            throw new SyntaxException(current.getRow(), current.getCol(), "Expected '" + type + "', get '" + current.getText() + "'");
        pos++;
        return current;
    }

    private boolean match(TokenType type) {
        final Token current = get(0);
        if (type != current.getType()) return false;
        pos++;
        return true;
    }

    private boolean lookMatch(int pos, TokenType type) {
        return get(pos).getType() == type;
    }

    private Token get(int relativePosition) {
        final int position = pos + relativePosition;
        if (position >= size) return EOF;
        return tokens.get(position);
    }
}