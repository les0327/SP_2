package lab4.lexer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @Getter @Setter private String value;
    @Getter @Setter private Type   tokenType;
    @Getter @Setter private int index;

    public Token(String value, Type tokenType) {
        this.value = value;
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        return "Token-( " + value + " ) --- " + "Type-( " + tokenType.getValue() + " )" + "--- Index-( " + index + ")";
    }

}
