package lab5.ast.expression;

import lab5.ast.NumberValue;
import lab5.ast.Value;

public final class BinaryExpression implements Expression {

    public enum Operator {
        ADD("+"),
        SUBTRACT("-"),
        MULTIPLY("*"),
        DIVIDE("/"),
        REMAINDER("%"),
        // Bitwise
        AND("&"),
        OR("|"),
        XOR("^"),
        LSHIFT("<<"),
        RSHIFT(">>");

        private final String name;

        private Operator(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public final Operator operation;
    public final Expression expr1, expr2;

    public BinaryExpression(Operator operation, Expression expr1, Expression expr2) {
        this.operation = operation;
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public Value eval() {
        final Value value1 = expr1.eval();
        final Value value2 = expr2.eval();

        final int number1 = value1.asNumber();
        final int number2 = value2.asNumber();
        int result;
        switch (operation) {
            case ADD: result = number1 + number2; break;
            case SUBTRACT: result = number1 - number2; break;
            case MULTIPLY: result = number1 * number2; break;
            case DIVIDE: result = number1 / number2; break;
            case REMAINDER: result = number1 % number2; break;

            // Bitwise
            case AND: result = (int)number1 & (int)number2; break;
            case XOR: result = (int)number1 ^ (int)number2; break;
            case OR: result = (int)number1 | (int)number2; break;
            case LSHIFT: result = (int)number1 << (int)number2; break;
            case RSHIFT: result = (int)number1 >> (int)number2; break;

            default:
                throw new RuntimeException("Operation " + operation + " is not supported");
        }
        return new NumberValue(result);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", expr1, operation, expr2);
    }

    @Override
    public String toAsm() {
        return String.format("%s %s %s", expr1.toAsm(), operation, expr2.toAsm());
    }
}