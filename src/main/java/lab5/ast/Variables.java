package lab5.ast;

import java.util.HashMap;
import java.util.Map;

public final class Variables {

    private static Map<String, Value> variables;

    static {
        variables = new HashMap<>();
        variables.put("true", NumberValue.ONE);
        variables.put("false", NumberValue.ZERO);
    }


    public static boolean isExists(String name) {
        return variables.containsKey(name);
    }

    public static Value get(String name) {
        if (!isExists(name)) throw new RuntimeException("Variable \"" + name + "\" is not defined");
        return variables.get(name);
    }

    public static void set(String name, Value value) {
        variables.put(name, value);
    }
}
