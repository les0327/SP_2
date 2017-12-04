package lab6.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class NumberValue implements Value {
    @Getter private Number value;
    @Getter private DataType type;
}
