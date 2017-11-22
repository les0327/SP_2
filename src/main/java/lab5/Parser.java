package lab5;

import lab5.ast.expression.*;
import lab5.ast.statement.*;
import lab5.exceptions.SyntaxException;

import java.util.List;

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

    private Statement statementOrBlock() {
        if (lookMatch(0, TokenType.BEGIN)) return block();
        return statement();
    }

    private Statement statement() {

        if (match(TokenType.IF)) {
            return ifThen();
        }
        if (match(TokenType.FOR)) {
            return forStatement();
        }

        return assignmentStatement();
    }

    private Statement block() {
        final BlockStatement block = new BlockStatement();
        consume(TokenType.BEGIN);
        while (!match(TokenType.END)) {
            block.add(statement());
        }
        match(TokenType.SEMI_COLON);
        return block;
    }

    private Statement assignmentStatement() {
        if (lookMatch(0, TokenType.VAR) && lookMatch(1, TokenType.ASSIGN)) {
            final String variable = consume(TokenType.VAR).getText();
            consume(TokenType.ASSIGN);
            Statement s = new AssignmentStatement(variable, expression());
            consume(TokenType.SEMI_COLON);
            return s;
        }
        throw new SyntaxException(get(0).getRow(), get(0).getCol(), "Expected next statement, get '" + get(0).getText() + "'");
    }


    private Statement ifThen() {
        final Expression condition = expression();
        consume(TokenType.THEN);
        final Statement ifStatement = statementOrBlock();
        final Statement elseStatement;
        if (match(TokenType.ELSE)) {
            elseStatement = statementOrBlock();
        } else {
            elseStatement = null;
        }
        return new IfStatement(condition, ifStatement, elseStatement);
    }

    private Statement forStatement() {
        Statement assign = null;
        Expression toValue = null;
        Statement block  = null;
        if (lookMatch(0, TokenType.VAR) && lookMatch(1, TokenType.ASSIGN)) {
            final String variable = consume(TokenType.VAR).getText();
            consume(TokenType.ASSIGN);
            assign = new AssignmentStatement(variable, expression());
        } else
            throw new SyntaxException(get(0).getRow(), get(0).getCol(), "Expected assign statement, get '" + get(0).getText() + "'");

        consume(TokenType.TO);
        if (match(TokenType.VAR))
            toValue = new VariableExpression(get(0).getText());
        else if (match(TokenType.NUMBER))
            toValue = new ValueExpression(Integer.parseInt(get(0).getText()));
        else
            throw new SyntaxException(get(0).getRow(), get(0).getCol(), "Unknown statement: " + get(0));

        consume(TokenType.DO);
        block = statementOrBlock();
        return new ForStatement(assign, toValue, block);
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
        if (match(TokenType.NUMBER)) {
            return new ValueExpression(Integer.parseInt(current.getText()));
        }
        if (match(TokenType.VAR)) {
            return new VariableExpression(current.getText());
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