package lab5.ast.statement;

import lab5.ast.expression.Expression;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ForStatement implements Statement {

    public final Statement  assign;
    public final Expression toValue;
    public final Statement  block;

    @Override
    public String toAsm() {
        return null;
    }
}
