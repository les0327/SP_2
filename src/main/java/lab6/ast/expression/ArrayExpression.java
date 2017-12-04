package lab6.ast.expression;

import lab6.ast.Array;
import lab6.ast.DataType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ArrayExpression implements Expression {
    @Getter private Array array;
    @Getter private Expression expression;

    @Override
    public DataType returnType() {
        return array.getType();
    }
}
