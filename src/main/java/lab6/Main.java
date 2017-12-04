package lab6;

import lab6.ast.statement.BlockStatement;
import lab6.ast.statement.Statement;

import java.util.List;

public class Main {


    public static final String input = "float b, a[3];  long n;  n:=n-1; b:=0; b:=n!=a[n]);";
    public static final String correctInput = "b : longInt; a : array [1..3] of real; n : longInt; b:=; n:=3; b:=n<>a[n];";

    public static void main(String[] args) {
        List<Token> tokenList = Lexer.tokenize(correctInput);
        tokenList.forEach(System.out::println);
        BlockStatement s = (BlockStatement) Parser.parse(tokenList);
        s.getStatements().forEach(Statement::semanticAnalyse);
        System.out.println(correctInput + "-> ok");
    }
}
