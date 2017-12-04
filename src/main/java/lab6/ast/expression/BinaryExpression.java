package lab6.ast.expression;

import lab6.ast.DataType;
import lab6.exceptions.SemanticException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BinaryExpression implements Expression {

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

    @Getter private Operator operation;
    @Getter private Expression expr1, expr2;

    @Override
    public DataType returnType() {
        DataType type1 = expr1.returnType();
        DataType type2 = expr2.returnType();

        if (type1.equals(DataType.Boolean)) {
            throw new SemanticException("Unsupported operation '" + operation + "' for type " + type1);
        }
        if (type2.equals(DataType.Boolean)) {
            throw new SemanticException("Unsupported operation '" + operation + "' for type " + type2);
        }

        if (type1.equals(DataType.LongInt) && type2.equals(DataType.LongInt))
            return DataType.LongInt;

        return DataType.Real;
    }
}