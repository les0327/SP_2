package lab3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @Getter @Setter private String    token;
    @Getter @Setter private TokenType tokenType;


    @Override
    public String toString() {
        return token + " | " + tokenType.getValue();
    }

}
