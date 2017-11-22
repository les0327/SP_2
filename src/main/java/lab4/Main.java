package lab4;

import lab4.lexer.Lexer;
import lab4.lexer.Token;
import lab4.syntax.Analyzer;

import java.util.List;

public class Main {

    private static String expression = "n:=n-1; b:=0; b:=n<>(f[a[n]]);";
//    private static String sourceExpression = "n:=n-1; b:=0; b:=n!=a[n];";

    public static void main(String[] args) {
        Lexer lexer = new Lexer();
        Analyzer analyzer = new Analyzer();
        try {
            System.out.println(expression);
            System.out.println("---------------------------------");
            List<Token> tokenList = lexer.parseExpression(expression);
//            tokenList.forEach(System.out::println);
            analyzer.parse(tokenList);
            System.out.println(expression + "-> OK");
            System.out.println();
//            System.out.println(sourceExpression);
//            System.out.println("---------------------------------");
//            tokenList = lexer.parseExpression(sourceExpression);
//            analyzer.parse(tokenList);
//            System.out.println(sourceExpression + "-> OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
