package ex5.entities;

/**
 * Represents a value with its associated type.
 */
public class Value {

    private final String value;
    private final VarType type;

    /**
     * Constructs a Value with the specified value and type.
     *
     * @param value The string representation of the value
     * @param type  The type of the value
     */
    public Value(String value, VarType type) {
        this.value = value;
        this.type = type;
    }

    /**
     * Gets the string representation of the value.
     *
     * @return The value as a string
     */
    public String getRawValue() {
        return value;
    }

    /**
     * Gets the type of the value.
     *
     * @return The type of the value
     */
    public VarType getType() {
        return type;
    }
}
