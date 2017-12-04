package lab6.ast.expression;

import lab6.ast.DataType;
import lab6.exceptions.SemanticException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ConditionalExpression implements Expression {

    public enum Operator {
        EQUALS("=="),
        NOT_EQUALS("<>"),

        LT("<"),
        LTEQ("<="),
        GT(">"),
        GTEQ(">="),

        AND("&&"),
        OR("||");

        private final String name;

        private Operator(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    @Getter private Operator operation;
    @Getter private Expression expr1, expr2;

    @Override
    public DataType returnType() {

        DataType type1 = expr1.returnType();
        DataType type2 = expr2.returnType();

        if ((type1.equals(DataType.Boolean) && !type2.equals(DataType.Boolean)) || (!type1.equals(DataType.Boolean) && type2.equals(DataType.Boolean)))
            throw new SemanticException("Unsupported operation '" + operation + "' between " + type1 + " and " + type2);

        return DataType.Boolean;
    }
}
