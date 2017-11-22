package lab5.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class NumberValue implements Value {

    public static final NumberValue ZERO = new NumberValue(0);
    public static final NumberValue ONE  = new NumberValue(1);

    public static NumberValue fromBoolean(boolean b) {
        return b ? ONE : ZERO;
    }

    @Getter private final int value;

    @Override
    public int asNumber() {
        return value;
    }

    @Override
    public String asString() {
        return Integer.toString(value);
    }

    @Override
    public String toString() {
        return asString();
    }
}
