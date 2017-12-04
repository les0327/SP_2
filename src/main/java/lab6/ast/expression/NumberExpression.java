package lab6.ast.expression;

import lab6.ast.DataType;
import lab6.ast.NumberValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class NumberExpression implements Expression {

    @Getter private NumberValue value;

    public NumberExpression(Number number, DataType type) {
        this.value = new NumberValue(number, type);
    }

    @Override
    public DataType returnType() {
        return value.getType();
    }
}
