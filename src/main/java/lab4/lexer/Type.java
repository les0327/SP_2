package lab4.lexer;

import lombok.Getter;
import lombok.Setter;

public class Type {

    @Getter @Setter private String key;
    @Getter @Setter private String value;

    public Type(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Type type = (Type) o;

        if (key != null ? !key.equals(type.key) : type.key != null) return false;
        return value != null ? value.equals(type.value) : type.value == null;
    }

}
