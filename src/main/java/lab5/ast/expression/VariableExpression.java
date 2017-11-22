package lab5.ast.expression;

import lab5.ast.Value;
import lab5.ast.Variables;

public final class VariableExpression implements Expression {

    public final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public Value eval() {
        return Variables.get(name);
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public String toAsm() {
        return toString();
    }
}
