package lab6.ast.expression;

import lab6.ast.DataType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class UnaryExpression implements Expression {

    public enum Operator {
        NEGATE("-"),
        NOT("!");

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
    @Getter private Expression expr1;

    @Override
    public DataType returnType() {
        return expr1.returnType();
    }
}
