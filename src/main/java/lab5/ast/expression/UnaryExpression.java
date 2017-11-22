package lab5.ast.expression;

import lab5.ast.NumberValue;
import lab5.ast.Value;

public final class UnaryExpression implements Expression {

    public enum Operator {
        NEGATE("-"),
        // Boolean
        NOT("!"),
        // Bitwise
        COMPLEMENT("~");

        private final String name;

        private Operator(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public final Expression expr1;
    public final Operator operation;

    public UnaryExpression(Operator operation, Expression expr1) {
        this.operation = operation;
        this.expr1 = expr1;
    }

    @Override
    public Value eval() {
        final Value value = expr1.eval();
        switch (operation) {
            case NEGATE: return new NumberValue(-value.asNumber());
            case COMPLEMENT: return new NumberValue(~value.asNumber());
            case NOT: return new NumberValue(value.asNumber() != 0 ? 0 : 1);
            default:
                throw new RuntimeException("Operation " + operation + " is not supported");
        }
    }


    @Override
    public String toString() {
        return String.format("%s %s", operation, expr1);
    }

    @Override
    public String toAsm() {
        final Value value = expr1.eval();
        switch (operation) {
            case NEGATE: return "MOV eax, " + value.asNumber() + "\nXOR eax, FFFFFFFFh\nADD eax, 1\n";
            case NOT: return "MOV eax, " + value.asNumber() + "\nXOR eax, FFFFFFFFh\n";
            default:
                throw new RuntimeException("Operation " + operation + " is not supported");
        }
    }
}
