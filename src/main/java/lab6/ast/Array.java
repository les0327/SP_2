package lab6.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Array implements Value {
    @Getter private String name;
    @Getter private DataType type;
    @Getter private int start;
    @Getter private int end;
}
