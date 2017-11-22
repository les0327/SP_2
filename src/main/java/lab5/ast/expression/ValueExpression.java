package lab5.ast.expression;

import lab5.ast.NumberValue;
import lab5.ast.Value;

public final class ValueExpression implements Expression {

    public final Value value;

    public ValueExpression(int value) {
        this.value = new NumberValue(value);
    }

    @Override
    public Value eval() {
        return value;
    }


    @Override
    public String toString() {
        return value.asString();
    }

    @Override
    public String toAsm() {
        return toString();
    }
}
