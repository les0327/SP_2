package lab5.ast.statement;

import lab5.ast.expression.Expression;

public final class IfStatement implements Statement {

    public final Expression expression;
    public final Statement ifStatement, elseStatement;

    public IfStatement(Expression expression, Statement ifStatement, Statement elseStatement) {
        this.expression = expression;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public String toAsm() {
        return null;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append("if ").append(expression).append("\n\t").append(ifStatement);
        if (elseStatement != null) {
            result.append("else \n\t").append(elseStatement);
        }
        return result.toString();
    }
}
