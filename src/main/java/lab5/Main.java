package lab5;

import lab5.ast.statement.Statement;

import java.util.List;

public class Main {

    /*
    If (a<b) then
        begin
            For t:= 1 to p do
                begin
                    a:=b;
                end;
        end;
     */

    public static final String input = "If a < b thn begin For t:= 1 to (p+4) do a:=b end";

    public static void main(String[] args) {
        List<Token> tokenList = Lexer.tokenize(input);
        tokenList.forEach(System.out::println);
        Statement s = Parser.parse(tokenList);
        System.out.println();
        System.out.println(input + "-> ok");
    }
}
