package lab6.ast;

import lab6.exceptions.SemanticException;

import java.util.HashMap;
import java.util.Map;

public class Arrays {

    private static Map<String, Array> arrays = new HashMap<>();

    public static Array define(String name, DataType type, int start, int end) {
        if (isDefined(name))
            throw new SemanticException("Variable '" + name +"' has been already defined");
        arrays.put(name, new Array(name, type, start, end));
        return arrays.get(name);
    }

    public static boolean isDefined(String name) {
        return arrays.containsKey(name);
    }

    public static Array get(String name) {
        if (!isDefined(name)) throw new SemanticException("Array \"" + name + "\" is not defined");
        return arrays.get(name);
    }
}
