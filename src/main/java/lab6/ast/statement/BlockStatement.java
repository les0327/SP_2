package lab6.ast.statement;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class BlockStatement implements Statement {

    @Getter private List<Statement> statements;

    public BlockStatement() {
        statements = new ArrayList<>();
    }

    public void add(Statement statement) {
        statements.add(statement);
    }

    @Override
    public void semanticAnalyse() {
        statements.forEach(Statement::semanticAnalyse);
    }
}
