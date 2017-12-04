package lab6.ast.statement;

import lab6.ast.Arrays;
import lab6.ast.DataType;
import lab6.ast.Variables;
import lab6.ast.expression.Expression;
import lab6.exceptions.SemanticException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class AssignmentStatement implements Statement {
    @Getter private String variable;
    @Getter private Expression expression;
    @Getter private boolean forArray;

    @Override
    public void semanticAnalyse() {
        DataType expType = expression.returnType();
        DataType varType = forArray ? Arrays.get(variable).getType() : Variables.get(variable).getType();
        if ((varType.equals(DataType.Boolean) && !expType.equals(DataType.Boolean))
                || (!varType.equals(DataType.Boolean) && expType.equals(DataType.Boolean))
                || (varType.equals(DataType.LongInt) && expType.equals(DataType.Real)))
            throw new SemanticException("Variable '" + variable +"(" + Variables.get(variable).getType() + ")' does not match type " + expType);

    }
}