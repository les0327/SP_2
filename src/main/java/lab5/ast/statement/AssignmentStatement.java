package lab5.ast.statement;

import lab5.ast.expression.Expression;

public final class AssignmentStatement implements Statement {

    private final String variable;
    private final Expression expression;

    public AssignmentStatement(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public String toAsm() {
        return null;
    }

    @Override
    public String toString() {
        return String.format("%s = %s;", variable, expression);
    }
}