package lab6.ast.expression;

import lab6.ast.DataType;
import lab6.ast.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class VariableExpression implements Expression {

    @Getter private Variable variable;

    @Override
    public DataType returnType() {
        return variable.getType();
    }

}
