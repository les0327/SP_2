package lab5.ast.expression;

import lab5.ast.Value;

public interface Expression {

    Value eval();

    String toAsm();
}
