package lab2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class LexemeNode {

    @Getter @Setter LexemeNode leftNode;
    @Getter @Setter String name;
    @Getter @Setter LexemeNode rightNode;

}
