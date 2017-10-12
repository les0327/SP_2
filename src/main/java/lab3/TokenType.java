package lab3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class TokenType {

    @Getter @Setter private String key;
    @Getter @Setter private String value;
}
